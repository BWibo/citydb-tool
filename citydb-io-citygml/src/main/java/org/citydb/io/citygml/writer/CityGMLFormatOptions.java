/*
 * citydb-tool - Command-line tool for the 3D City Database
 * https://www.3dcitydb.org/
 *
 * Copyright 2022-2023
 * Virtual City Systems, Germany
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

package org.citydb.io.citygml.writer;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.annotation.JSONField;
import org.citydb.io.citygml.writer.options.AddressMode;
import org.citygml4j.core.model.CityGMLVersion;

public class CityGMLFormatOptions {
    @JSONField(serializeFeatures = JSONWriter.Feature.WriteEnumUsingToString)
    private CityGMLVersion version;
    private boolean prettyPrint = true;
    private boolean useLod4AsLod3;
    private boolean mapLod0RoofEdge;
    private boolean mapLod1MultiSurfaces;
    @JSONField(serializeFeatures = JSONWriter.Feature.WriteEnumUsingToString)
    private AddressMode addressMode;

    public CityGMLVersion getVersion() {
        return version != null ? version : CityGMLVersion.v3_0;
    }

    public CityGMLFormatOptions setVersion(CityGMLVersion version) {
        this.version = version;
        return this;
    }

    public CityGMLFormatOptions setVersion(String version) {
        this.version = CityGMLVersion.fromValue(version);
        return this;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public CityGMLFormatOptions setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
        return this;
    }

    public boolean isUseLod4AsLod3() {
        return useLod4AsLod3;
    }

    public CityGMLFormatOptions setUseLod4AsLod3(boolean useLod4AsLod3) {
        this.useLod4AsLod3 = useLod4AsLod3;
        return this;
    }

    public boolean isMapLod0RoofEdge() {
        return mapLod0RoofEdge;
    }

    public CityGMLFormatOptions setMapLod0RoofEdge(boolean mapLod0RoofEdge) {
        this.mapLod0RoofEdge = mapLod0RoofEdge;
        return this;
    }

    public boolean isMapLod1MultiSurfaces() {
        return mapLod1MultiSurfaces;
    }

    public CityGMLFormatOptions setMapLod1MultiSurfaces(boolean mapLod1MultiSurfaces) {
        this.mapLod1MultiSurfaces = mapLod1MultiSurfaces;
        return this;
    }

    public AddressMode getAddressMode() {
        return addressMode != null ? addressMode : AddressMode.COLUMNS_FIRST;
    }

    public CityGMLFormatOptions setAddressMode(AddressMode addressMode) {
        this.addressMode = addressMode;
        return this;
    }

    public CityGMLFormatOptions setAddressMode(String addressMode) {
        this.addressMode = AddressMode.fromValue(addressMode);
        return this;
    }
}
