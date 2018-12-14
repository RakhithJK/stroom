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

package stroom.entity.util;


import org.junit.jupiter.api.Test;
import stroom.util.test.StroomUnitTest;

import static org.assertj.core.api.Assertions.assertThat;

class TestXMLUtil extends StroomUnitTest {
    @Test
    void testWords() {
        assertThat(XMLUtil.toXMLName(XMLUtil.class.getSimpleName())).isEqualTo("xmlUtil");
        assertThat(XMLUtil.toXMLName(String.class.getSimpleName())).isEqualTo("string");
        assertThat(XMLUtil.toXMLName(StringBuilder.class.getSimpleName())).isEqualTo("stringBuilder");
        assertThat(XMLUtil.toXMLName(TestXMLUtil.class.getSimpleName())).isEqualTo("testXMLUtil");
    }
}
