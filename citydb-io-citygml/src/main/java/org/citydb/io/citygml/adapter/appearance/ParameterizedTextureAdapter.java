/*
 * citydb-tool - Command-line tool for the 3D City Database
 * https://www.3dcitydb.org/
 *
 * Copyright 2022-2024
 * virtualcitysystems GmbH, Germany
 * https://vc.systems/
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

package org.citydb.io.citygml.adapter.appearance;

import org.citydb.io.citygml.annotation.DatabaseType;
import org.citydb.model.appearance.ParameterizedTexture;
import org.citydb.model.common.Namespaces;

@DatabaseType(name = "ParameterizedTexture", namespace = Namespaces.APPEARANCE)
public class ParameterizedTextureAdapter extends TextureAdapter<ParameterizedTexture, org.citygml4j.core.model.appearance.ParameterizedTexture> {

    @Override
    public ParameterizedTexture createModel(org.citygml4j.core.model.appearance.ParameterizedTexture source) {
        return ParameterizedTexture.newInstance();
    }

    @Override
    public org.citygml4j.core.model.appearance.ParameterizedTexture createObject(ParameterizedTexture source) {
        return new org.citygml4j.core.model.appearance.ParameterizedTexture();
    }
}
