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

package stroom.dashboard.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import stroom.docref.HasDisplayValue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"type", "settings", "wrap"})
@JsonInclude(Include.NON_NULL)
public class Format {
    public static List<Type> TYPES = Arrays.asList(Type.GENERAL, Type.NUMBER, Type.DATE_TIME, Type.TEXT);

    @JsonProperty
    private Type type;
    @JsonProperty
    private FormatSettings settings;
    @JsonProperty
    private Boolean wrap;

    public Format() {
    }

    public Format(final Type type) {
        this.type = type;
        this.settings = null;
        this.wrap = null;
    }

    public Format(final Type type, final FormatSettings settings) {
        this.type = type;
        this.settings = settings;
        this.wrap = null;
    }

    @JsonCreator
    public Format(@JsonProperty("type") final Type type,
                  @JsonProperty("settings") final FormatSettings settings,
                  @JsonProperty("wrap") final Boolean wrap) {
        this.type = type;
        this.settings = settings;
        this.wrap = wrap;
    }

    public Type getType() {
        return type;
    }

    public FormatSettings getSettings() {
        return settings;
    }

    public Boolean getWrap() {
        return wrap;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Format format = (Format) o;
        return type == format.type &&
                Objects.equals(settings, format.settings) &&
                Objects.equals(wrap, format.wrap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, settings, wrap);
    }

    @Override
    public String toString() {
        return "Format{" +
                "type=" + type +
                ", settings=" + settings +
                ", wrap=" + wrap +
                '}';
    }

    public Format copy() {
        if (settings != null) {
            return new Format(type, settings.copy(), wrap);
        }
        return new Format(type, null, wrap);
    }

    public enum Type implements HasDisplayValue {
        GENERAL("General"), NUMBER("Number"), DATE_TIME("Date Time"), TEXT("Text");

        private final String displayValue;

        Type(final String displayValue) {
            this.displayValue = displayValue;
        }

        @Override
        public String getDisplayValue() {
            return displayValue;
        }
    }
}
