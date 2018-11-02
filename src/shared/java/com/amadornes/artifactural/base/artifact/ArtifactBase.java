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

package com.amadornes.artifactural.base.artifact;

import com.amadornes.artifactural.api.artifact.Artifact;
import com.amadornes.artifactural.api.artifact.ArtifactMetadata;
import com.amadornes.artifactural.api.artifact.ArtifactType;
import com.amadornes.artifactural.api.cache.ArtifactCache;
import com.amadornes.artifactural.api.artifact.ArtifactIdentifier;
import com.amadornes.artifactural.api.transform.ArtifactTransformer;

public abstract class ArtifactBase implements Artifact {

    private final ArtifactIdentifier identifier;
    private final ArtifactType type;
    private final ArtifactMetadata metadata;

    public ArtifactBase(ArtifactIdentifier identifier, ArtifactType type, ArtifactMetadata metadata) {
        this.identifier = identifier;
        this.type = type;
        this.metadata = metadata;
    }

    @Override
    public ArtifactIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public ArtifactType getType() {
        return type;
    }

    @Override
    public ArtifactMetadata getMetadata() {
        return metadata;
    }

    @Override
    public Artifact apply(ArtifactTransformer transformer) {
        if (!transformer.appliesTo(this)) return this;
        return transformer.transform(this);
    }

    @Override
    public Artifact.Cached cache(ArtifactCache cache) {
        return cache.store(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + identifier + ", " + type +", " + metadata;
    }

}
