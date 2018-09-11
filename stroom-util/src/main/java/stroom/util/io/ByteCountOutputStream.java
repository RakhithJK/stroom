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
 */

package stroom.util.io;

import java.io.IOException;
import java.io.OutputStream;

public class ByteCountOutputStream extends WrappedOutputStream {
    private long byteCount;

    public ByteCountOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    public long getByteCount() {
        return byteCount;
    }

    @Override
    public void write(final int b) throws IOException {
        byteCount++;
        super.write(b);
    }

    @Override
    public void write(final byte[] b) throws IOException {
        byteCount += b.length;
        super.write(b);
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        byteCount += len;
        super.write(b, off, len);
    }
}
