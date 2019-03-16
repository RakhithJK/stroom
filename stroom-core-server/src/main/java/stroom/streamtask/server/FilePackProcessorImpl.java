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

package stroom.streamtask.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import stroom.feed.MetaMap;
import stroom.feed.StroomHeaderArguments;
import stroom.feed.server.FeedService;
import stroom.feed.shared.Feed;
import stroom.internalstatistics.MetaDataStatistic;
import stroom.proxy.repo.ProxyFileHandler;
import stroom.proxy.repo.StroomZipRepository;
import stroom.streamstore.server.StreamStore;
import stroom.task.server.TaskContext;
import stroom.util.io.StreamProgressMonitor;
import stroom.util.logging.LogExecutionTime;
import stroom.util.spring.StroomScope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that reads a nested directory tree of stroom zip files.
 * <p>
 * <p>
 * TODO - This class is extended in ProxyAggregationExecutor in Stroom
 * so changes to the way files are stored in the zip repository
 * may have an impact on Stroom while it is using stroom.util.zip as opposed
 * to stroom-proxy-zip.  Need to pull all the zip repository stuff out
 * into its own repo with its own lifecycle and a clearly defined API,
 * then both stroom-proxy and stroom can use it.
 */
@Component
@Scope(StroomScope.PROTOTYPE)
public final class FilePackProcessorImpl implements FilePackProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilePackProcessorImpl.class);

    private final ProxyFileHandler feedFileProcessorHelper = new ProxyFileHandler();

    private final StreamStore streamStore;
    private final FeedService feedService;
    private final MetaDataStatistic metaDataStatistic;
    private final boolean aggregate = true;
    private final ProxyFileHandler proxyFileHandler = new ProxyFileHandler();
    private final TaskContext taskContext;

    @Inject
    public FilePackProcessorImpl(final StreamStore streamStore,
                                 @Named("cachedFeedService") final FeedService feedService,
                                 final MetaDataStatistic metaDataStatistic,
                                 final TaskContext taskContext) {
        this.streamStore = streamStore;
        this.feedService = feedService;
        this.metaDataStatistic = metaDataStatistic;
        this.taskContext = taskContext;
    }

    @Override
    public void process(final StroomZipRepository stroomZipRepository, final FilePack filePack) {
        final String feedName = filePack.getFeed();
        final Feed feed = feedService.loadByName(feedName);

        taskContext.setName("Processing pack - " + filePack.getFeed());

        final LogExecutionTime logExecutionTime = new LogExecutionTime();
        LOGGER.info("processFeedFiles() - Started {} ({} Files)", feedName, filePack.getFiles().size());

        if (feed == null) {
            LOGGER.error("processFeedFiles() - " + feedName + " Failed to find feed");
            return;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("processFeedFiles() - " + feedName + " " + filePack.getFiles());
        }

        // We don't want to aggregate reference feeds.
        final boolean oneByOne = feed.isReference() || !aggregate;

        List<StreamTargetStroomStreamHandler> handlers = openStreamHandlers(feed);
        List<Path> deleteFileList = new ArrayList<>();

        long sequence = 1;
        long count = 0;

        final StreamProgressMonitor streamProgressMonitor = new StreamProgressMonitor("ProxyAggregationTask");

        for (final Path file : filePack.getFiles()) {
            count++;
            taskContext.info("File " + count + " of " + filePack.getFiles().size());

            if (taskContext.isTerminated()) {
                break;
            }
            try {
                if (sequence > 1 && oneByOne) {
                    // Close off this unit
                    handlers = closeStreamHandlers(handlers);

                    // Delete the done files
                    proxyFileHandler.deleteFiles(stroomZipRepository, deleteFileList);

                    // Start new batch
                    deleteFileList = new ArrayList<>();
                    handlers = openStreamHandlers(feed);
                    sequence = 1;
                }

                sequence = feedFileProcessorHelper.processFeedFile(handlers, stroomZipRepository, file, streamProgressMonitor, sequence);
                deleteFileList.add(file);

            } catch (final Throwable t) {
                handlers = closeDeleteStreamHandlers(handlers);
            }
        }
        closeStreamHandlers(handlers);
        proxyFileHandler.deleteFiles(stroomZipRepository, deleteFileList);
        LOGGER.info("processFeedFiles() - Completed {} in {}", feedName, logExecutionTime);
    }

    private List<StreamTargetStroomStreamHandler> openStreamHandlers(final Feed feed) {
        // We don't want to aggregate reference feeds.
        final boolean oneByOne = feed.isReference() || !aggregate;

        final StreamTargetStroomStreamHandler streamTargetStroomStreamHandler = new StreamTargetStroomStreamHandler(streamStore,
                feedService, metaDataStatistic, feed, feed.getStreamType());

        streamTargetStroomStreamHandler.setOneByOne(oneByOne);

        final MetaMap globalMetaMap = new MetaMap();
        globalMetaMap.put(StroomHeaderArguments.FEED, feed.getName());

        try {
            streamTargetStroomStreamHandler.handleHeader(globalMetaMap);
        } catch (final IOException ioEx) {
            streamTargetStroomStreamHandler.close();
            throw new RuntimeException(ioEx);
        }

        final List<StreamTargetStroomStreamHandler> list = new ArrayList<>();
        list.add(streamTargetStroomStreamHandler);

        return list;
    }

    private List<StreamTargetStroomStreamHandler> closeStreamHandlers(final List<StreamTargetStroomStreamHandler> handlers) {
        if (handlers != null) {
            handlers.forEach(StreamTargetStroomStreamHandler::close);
        }
        return null;
    }

    private List<StreamTargetStroomStreamHandler> closeDeleteStreamHandlers(
            final List<StreamTargetStroomStreamHandler> handlers) {
        if (handlers != null) {
            handlers.forEach(StreamTargetStroomStreamHandler::closeDelete);
        }
        return null;
    }
}