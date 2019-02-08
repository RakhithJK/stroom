/*
 * Copyright 2016 Crown Copyright
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

package stroom.db.migration._V07_00_00.entity.shared;

import java.util.HashMap;
import java.util.Map;

public class _V07_00_00_PrimitiveValueConverter<E extends _V07_00_00_HasPrimitiveValue> {
    private Map<Byte, E> map;

    public _V07_00_00_PrimitiveValueConverter(E[] values) {
        map = new HashMap<>(values.length);
        for (E value : values) {
            map.put(value.getPrimitiveValue(), value);
        }
    }

    public E fromPrimitiveValue(byte i) {
        return map.get(i);
    }

    public void put(final Byte key, final E value) {
        map.put(key, value);
    }
}
