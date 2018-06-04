/*
 * Copyright 2018 Crown Copyright
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
 *
 */

package stroom.refdata.offheapstore.serdes;

import stroom.refdata.lmdb.serde.Deserializer;
import stroom.refdata.lmdb.serde.Serde;
import stroom.refdata.lmdb.serde.Serializer;
import stroom.refdata.offheapstore.ValueStoreKey;

import java.nio.ByteBuffer;

public class ValueStoreKeySerde implements Serde<ValueStoreKey>, Serializer<ValueStoreKey>, Deserializer<ValueStoreKey> {

    @Override
    public ValueStoreKey deserialize(final ByteBuffer byteBuffer) {
        int hashCode = byteBuffer.getInt();
        short uniqueId = byteBuffer.getShort();
        byteBuffer.flip();
        return new ValueStoreKey(hashCode, uniqueId);
    }

    @Override
    public void serialize(final ByteBuffer byteBuffer, final ValueStoreKey valueStoreKey) {
        byteBuffer
                .putInt(valueStoreKey.getValueHashCode())
                .putShort(valueStoreKey.getUniqueId());
        byteBuffer.flip();
    }
}
