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

package stroom.entity.shared;

import stroom.util.shared.VoidResult;

public class EntityServiceDeleteAction extends AbstractEntityAction<VoidResult> {
    private static final long serialVersionUID = 800905016214418723L;

    private Entity entity;

    public EntityServiceDeleteAction() {
        // Default constructor necessary for GWT serialisation.
    }

    public EntityServiceDeleteAction(final Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String getTaskName() {
        return "Save: " + entity;
    }
}