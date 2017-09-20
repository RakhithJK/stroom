/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.refdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import stroom.pipeline.server.errorhandler.ErrorReceiver;
import stroom.pipeline.shared.data.PipelineReference;
import stroom.pipeline.state.FeedHolder;
import stroom.pipeline.state.StreamHolder;
import stroom.query.api.v2.DocRef;
import stroom.security.SecurityContext;
import stroom.streamstore.server.fs.serializable.StreamSourceInputStream;
import stroom.streamstore.server.fs.serializable.StreamSourceInputStreamProvider;
import stroom.streamstore.shared.Stream;
import stroom.streamstore.shared.StreamType;
import stroom.util.shared.Severity;
import stroom.util.spring.StroomScope;
import stroom.xml.event.EventList;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

@Component
@Scope(StroomScope.PROTOTYPE)
public class ReferenceData {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceData.class);

    // Maps can be nested during the look up process e.g. "MAP1/MAP2"
    private static final String NEST_SEPERATOR = "/";
    private static final int MINIMUM_BYTE_COUNT = 10;
    private final Map<String, CachedMapStore> nestedStreamCache = new HashMap<>();
    private final Map<MapStoreCacheKey, MapStore> localMapStoreCache = new HashMap<>();

    @Resource
    private EffectiveStreamCache effectiveStreamCache;
    @Resource
    private MapStoreCache mapStoreCache;
    @Resource
    private FeedHolder feedHolder;
    @Resource
    private StreamHolder streamHolder;
    @Resource
    private ContextDataLoader contextDataLoader;
    @Resource
    private SecurityContext securityContext;

    /**
     * <p>
     * Given a date and a map name get some reference data value.
     * </p>
     *
     * @param time    The event time (or the time the reference data is valid for)
     * @param mapName The map name
     * @param key     The key of the reference data
     * @return the value of ref data
     */
    public EventList getValue(final List<PipelineReference> pipelineReferences, final ErrorReceiver errorReceiver,
                              final long time, final String mapName, final String key) {
        // Do we have a nested token?
        final int splitPos = mapName.indexOf(NEST_SEPERATOR);
        if (splitPos != -1) {
            // Yes ... pull out the first map
            final String startMap = mapName.substring(0, splitPos);
            final String nextMap = mapName.substring(splitPos + NEST_SEPERATOR.length());

            // Look up the KV then use that to recurse
            final EventList nextKey = doGetValue(pipelineReferences, errorReceiver, time, startMap, key);
            if (nextKey == null) {
                // map broken ... no link found
                return null;
            }

            return getValue(pipelineReferences, errorReceiver, time, nextMap, nextKey.toString());
        }

        return doGetValue(pipelineReferences, errorReceiver, time, mapName, key);
    }

    private EventList doGetValue(final List<PipelineReference> pipelineReferences, final ErrorReceiver errorReceiver,
                                 final long time, final String mapName, final String keyName) {
        for (final PipelineReference pipelineReference : pipelineReferences) {
            EventList eventList = null;

            // Handle context data differently loading it from the
            // current stream context.
            if (pipelineReference.getStreamType() != null
                    && StreamType.CONTEXT.getName().equals(pipelineReference.getStreamType())) {
                eventList = getNestedStreamEventList(pipelineReference, errorReceiver, mapName, keyName);
            } else {
                eventList = getExternalEventList(pipelineReference, errorReceiver, time, mapName, keyName);
            }

            if (eventList != null) {
                return eventList;
            }
        }

        return null;
    }

    /**
     * Get an event list from a stream that is a nested child of the current
     * stream context and is therefore not effective time sensitive.
     */
    private EventList getNestedStreamEventList(final PipelineReference pipelineReference,
                                               final ErrorReceiver errorReceiver, final String mapName, final String keyName) {
        EventList events = null;

        try {
            // Get nested stream.
            final String streamTypeString = pipelineReference.getStreamType();
            final long streamNo = streamHolder.getStreamNo();
            CachedMapStore cachedMapStore = nestedStreamCache.get(streamTypeString);
            MapStore mapStore = null;

            if (cachedMapStore != null && cachedMapStore.getStreamNo() == streamNo) {
                mapStore = cachedMapStore.getMapStore();
            } else {
                StreamType streamType = null;
                for (final StreamType st : StreamType.initialValues()) {
                    if (st.getName().equals(streamTypeString)) {
                        streamType = st;
                        break;
                    }
                }
                final StreamSourceInputStreamProvider provider = streamHolder.getProvider(streamType);
                // There may not be a provider for this stream type if we do not
                // have any context data stream.
                if (provider != null) {
                    final StreamSourceInputStream inputStream = provider.getStream(streamNo);
                    mapStore = getContextData(streamHolder.getStream(), inputStream, pipelineReference.getPipeline(),
                            errorReceiver);
                }

                cachedMapStore = new CachedMapStore(streamNo, mapStore);
                nestedStreamCache.put(streamTypeString, cachedMapStore);
            }

            if (mapStore != null) {
                if (mapStore.getErrorReceiver() != null) {
                    mapStore.getErrorReceiver().replay(errorReceiver);
                }
                events = mapStore.getEvents(mapName, keyName);
            }
        } catch (final IOException e) {
            LOGGER.debug("Unable to get nested stream event list!", e);
            errorReceiver.log(Severity.ERROR, null, getClass().getSimpleName(), e.getMessage(), e);
        }

        return events;
    }

    private MapStore getContextData(final Stream stream, final StreamSourceInputStream contextStream,
                                    final DocRef contextPipeline, final ErrorReceiver errorReceiver) {
        if (contextStream != null) {
            // Check the size of the input stream.
            final long byteCount = contextStream.size();
            // Only use context data if we actually have some.
            if (byteCount > MINIMUM_BYTE_COUNT) {
                return contextDataLoader.load(contextStream, stream, feedHolder.getFeed(), contextPipeline);
            }
        }

        return new MapStoreImpl();
    }

    /**
     * Get an event list from a store level/non nested stream that is sensitive
     * to effective time.
     */
    private EventList getExternalEventList(final PipelineReference pipelineReference, final ErrorReceiver errorReceiver,
                                           final long time, final String mapName, final String keyName) {
        // First round down the time to the nearest 10 days approx (actually
        // more like 11.5, one billion milliseconds).
        final long baseTime = effectiveStreamCache.getBaseTime(time);

        // Make sure the reference feed is persistent otherwise lookups will
        // fail as the equals method will only test for feeds that are the
        // same object instance rather than id.
        assert pipelineReference.getFeed() != null && pipelineReference.getFeed().getUuid() != null && pipelineReference.getFeed().getUuid().length() > 0
                && pipelineReference.getStreamType() != null && pipelineReference.getStreamType().length() > 0;

        // Create a key to find a set of effective times in the pool.
        final EffectiveStreamKey effectiveStreamKey = new EffectiveStreamKey(pipelineReference.getFeed(),
                pipelineReference.getStreamType(), baseTime, getUser());
        // Try and fetch a tree set of effective streams for this key.
        final TreeSet<EffectiveStream> streamSet = effectiveStreamCache.getOrCreate(effectiveStreamKey);

        if (streamSet != null && streamSet.size() > 0) {
            // Try and find the stream before the requested time that is less
            // than or equal to it.
            final EffectiveStream effectiveStream = streamSet.floor(new EffectiveStream(0, time));
            // If we have an effective time then use it.
            if (effectiveStream != null) {
                // Now try and get reference data for the feed at this time.
                final MapStoreCacheKey mapStorePoolKey = new MapStoreCacheKey(pipelineReference.getPipeline(),
                        effectiveStream.getStreamId(), getUser());
                // Get the map store associated with this effective feed.
                final MapStore mapStore = getMapStore(mapStorePoolKey);
                if (mapStore != null) {
                    if (mapStore.getErrorReceiver() != null) {
                        mapStore.getErrorReceiver().replay(errorReceiver);
                    }
                    return mapStore.getEvents(mapName, keyName);
                }
            }
        }

        return null;
    }

    private MapStore getMapStore(final MapStoreCacheKey mapStoreCacheKey) {
        // Try the local cache first as we may have already got this map.
        MapStore mapStore = localMapStoreCache.get(mapStoreCacheKey);

        // If we didn't get a local cache then look in the pool.
        if (mapStore == null) {
            // Get the map store cache associated with this effective feed.
            mapStore = mapStoreCache.getOrCreate(mapStoreCacheKey);
            // Cache this item locally for use later on.
            localMapStoreCache.put(mapStoreCacheKey, mapStore);
        }

        return mapStore;
    }

    /**
     * This method puts a key value pair into the appropriate map based on a map
     * name, feed name and the date that the reference data is effective from.
     * It remains here only for test purposes.
     */
    void put(final MapStoreCacheKey mapStoreCacheKey, final MapStore mapStore) {
        localMapStoreCache.put(mapStoreCacheKey, mapStore);
    }

    void setEffectiveStreamCache(final EffectiveStreamCache effectiveStreamcache) {
        this.effectiveStreamCache = effectiveStreamcache;
    }

    void setMapStorePool(final MapStoreCache mapStorePool) {
        this.mapStoreCache = mapStorePool;
    }

    private String getUser() {
        if (securityContext == null) {
            return null;
        }
        return securityContext.getUserId();
    }

    private static class CachedMapStore {
        private final long streamNo;
        private final MapStore mapStore;

        public CachedMapStore(final long streamNo, final MapStore mapStore) {
            this.streamNo = streamNo;
            this.mapStore = mapStore;
        }

        public long getStreamNo() {
            return streamNo;
        }

        public MapStore getMapStore() {
            return mapStore;
        }

        @Override
        public int hashCode() {
            return (int) streamNo;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null || !(obj instanceof CachedMapStore)) {
                return false;
            }

            final CachedMapStore cachedMapStore = (CachedMapStore) obj;

            return cachedMapStore.streamNo == streamNo;
        }
    }
}
