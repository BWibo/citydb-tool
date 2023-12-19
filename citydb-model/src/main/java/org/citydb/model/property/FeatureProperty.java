/*
 * citydb-tool - Command-line tool for the 3D City Database
 * https://www.3dcitydb.org/
 *
 * Copyright 2022-2023
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

package org.citydb.model.property;

import org.citydb.model.common.InlineOrByReferenceProperty;
import org.citydb.model.common.Name;
import org.citydb.model.common.Reference;
import org.citydb.model.common.RelationType;
import org.citydb.model.feature.Feature;

import java.util.Objects;
import java.util.Optional;

public class FeatureProperty extends Property<FeatureProperty> implements InlineOrByReferenceProperty<Feature> {
    private final Feature feature;
    private final Reference reference;
    private RelationType relationType;

    private FeatureProperty(Name name, Feature feature) {
        super(name, DataType.FEATURE_PROPERTY);
        this.feature = asChild(Objects.requireNonNull(feature, "The feature must not be null."));
        reference = null;
    }

    private FeatureProperty(Name name, Reference reference) {
        super(name, DataType.FEATURE_PROPERTY);
        this.reference = asChild(Objects.requireNonNull(reference, "The reference must not be null."));
        feature = null;
    }

    public static FeatureProperty of(Name name, Feature feature) {
        return new FeatureProperty(name, feature);
    }

    public static FeatureProperty of(Name name, Reference reference) {
        return new FeatureProperty(name, reference);
    }

    @Override
    public Optional<Feature> getObject() {
        return Optional.ofNullable(feature);
    }

    @Override
    public Optional<Reference> getReference() {
        return Optional.ofNullable(reference);
    }

    public RelationType getRelationType() {
        return relationType != null ? relationType : RelationType.RELATES;
    }

    public FeatureProperty setRelationType(RelationType relationType) {
        this.relationType = relationType;
        return this;
    }

    @Override
    FeatureProperty self() {
        return this;
    }
}
