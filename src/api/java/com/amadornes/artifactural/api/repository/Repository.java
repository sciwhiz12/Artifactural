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

package com.amadornes.artifactural.api.repository;

import java.io.File;

import com.amadornes.artifactural.api.artifact.Artifact;
import com.amadornes.artifactural.api.artifact.ArtifactIdentifier;

public interface Repository {

    Artifact getArtifact(ArtifactIdentifier identifier);

    /**
     * Returns a file in maven-metadata.xml format for the specified artifact,
     * this is used by gradle to list all known versions, so that it can resolve wildcard
     * dependencies such as foo:bar:1.+
     *
     * @param group Group
     * @param name Artifact name
     * @return maven-metadata.xml file listing all versions of the artifact this repo can provide. Or null if you don't want to list any.
     */
    default File getMavenMetadata(String group, String name) {
        return null;
    }

}
