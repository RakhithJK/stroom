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

package stroom.refdata.lmdb.serde;

import java.nio.ByteBuffer;

public interface Serializer<T> {

    static final int DEFAULT_CAPACITY = 1_000;

    /**
     * Serialize the passed objects to bytes returning a new {@link ByteBuffer} containing
     * those bytes.
     */
    default ByteBuffer serialize(final T object) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(DEFAULT_CAPACITY);
        serialize(byteBuffer, object);
        return byteBuffer;
    }


    /**
     * Serialize object into the passed {@link ByteBuffer}. Assumes there is sufficient capacity.
     * This method will flip the buffer after writing to it.
     */
    void serialize(final ByteBuffer byteBuffer, final T object);
}
