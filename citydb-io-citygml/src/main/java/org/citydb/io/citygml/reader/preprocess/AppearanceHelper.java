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

package org.citydb.io.citygml.reader.preprocess;

import org.citydb.io.citygml.reader.util.FeatureHelper;
import org.citygml4j.core.model.appearance.*;
import org.citygml4j.core.model.core.AbstractFeature;
import org.citygml4j.core.visitor.ObjectWalker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppearanceHelper {
    private final List<Appearance> appearances = new ArrayList<>();
    private final Map<String, List<TextureAssociationProperty>> parameterizedTextures = new HashMap<>();
    private final Map<String, List<GeometryReference>> georeferencedTextures = new HashMap<>();
    private final Map<String, List<GeometryReference>> materials = new HashMap<>();

    AppearanceHelper(AbstractFeature feature) {
        feature.accept(new ObjectWalker() {
            @Override
            public void visit(Appearance appearance) {
                appearances.add(appearance);
            }
        });

        process();
    }

    List<Appearance> getAppearances() {
        return appearances;
    }

    boolean hasAppearances() {
        return !appearances.isEmpty();
    }

    private void process() {
        if (!appearances.isEmpty()) {
            TargetCollector collector = new TargetCollector((int) Math.min(10, appearances.stream()
                    .map(Appearance::getTheme)
                    .distinct().count()));

            appearances.forEach(appearance -> appearance.accept(collector));
        }
    }

    List<TextureAssociationProperty> getParameterizedTextures(String geometryId) {
        return parameterizedTextures.get(geometryId);
    }

    List<TextureAssociationProperty> getAndRemoveParameterizedTextures(String geometryId) {
        return parameterizedTextures.remove(geometryId);
    }

    List<GeometryReference> getGeoreferencedTextures(String geometryId) {
        return georeferencedTextures.get(geometryId);
    }

    List<GeometryReference> getAndRemoveGeoreferencedTextures(String geometryId) {
        return georeferencedTextures.remove(geometryId);
    }

    List<GeometryReference> getMaterials(String geometryId) {
        return materials.get(geometryId);
    }

    List<GeometryReference> getAndRemoveMaterials(String geometryId) {
        return materials.remove(geometryId);
    }

    private class TargetCollector extends ObjectWalker {
        private final int capacity;

        TargetCollector(int capacity) {
            this.capacity = capacity;
        }

        @Override
        public void visit(ParameterizedTexture texture) {
            if (texture.isSetTextureParameterizations()) {
                for (TextureAssociationProperty property : texture.getTextureParameterizations()) {
                    if (property.getObject() != null
                            && property.getObject().getTarget() != null
                            && property.getObject().getTarget().getHref() != null) {
                        String id = FeatureHelper.getIdFromReference(property.getObject().getTarget().getHref());
                        parameterizedTextures.computeIfAbsent(id, v -> new ArrayList<>(capacity)).add(property);
                    }
                }
            }
        }

        @Override
        public void visit(GeoreferencedTexture texture) {
            if (texture.isSetTargets()) {
                process(texture.getTargets(), georeferencedTextures);
            }
        }

        @Override
        public void visit(X3DMaterial material) {
            if (material.isSetTargets()) {
                process(material.getTargets(), materials);
            }
        }

        private void process(List<GeometryReference> references, Map<String, List<GeometryReference>> targets) {
            for (GeometryReference reference : references) {
                if (reference.getHref() != null) {
                    String id = FeatureHelper.getIdFromReference(reference.getHref());
                    targets.computeIfAbsent(id, v -> new ArrayList<>(capacity)).add(reference);
                }
            }
        }
    }
}
