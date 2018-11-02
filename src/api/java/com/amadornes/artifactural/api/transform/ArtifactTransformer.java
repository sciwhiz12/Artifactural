/*
 * Artifactural
 * Copyright (c) 2018.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.amadornes.artifactural.api.transform;

import com.amadornes.artifactural.api.artifact.Artifact;
import com.amadornes.artifactural.api.artifact.ArtifactMetadata;

import java.util.function.UnaryOperator;

public interface ArtifactTransformer {

    static ArtifactTransformer of(UnaryOperator<Artifact> operator) {
        return new ArtifactTransformer() {
            @Override
            public Artifact transform(Artifact artifact) {
                return operator.apply(artifact);
            }

            @Override
            public ArtifactMetadata withInfo(ArtifactMetadata metadata) {
                return metadata;
            }
        };
    }

    default boolean appliesTo(Artifact artifact) {
        return true;
    }

    Artifact transform(Artifact artifact);

    ArtifactMetadata withInfo(ArtifactMetadata metadata);

    default ArtifactTransformer andThen(ArtifactTransformer other) {
        ArtifactTransformer current = this;
        return new ArtifactTransformer() {

            @Override
            public Artifact transform(Artifact artifact) {
                return other.transform(current.transform(artifact));
            }

            @Override
            public ArtifactMetadata withInfo(ArtifactMetadata metadata) {
                return other.withInfo(current.withInfo(metadata));
            }

        };
    }

}
