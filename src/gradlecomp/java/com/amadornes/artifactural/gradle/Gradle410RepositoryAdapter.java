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

package com.amadornes.artifactural.gradle;

import com.amadornes.artifactural.api.repository.Repository;
import org.gradle.api.internal.artifacts.repositories.DefaultMavenLocalArtifactRepository;
import org.gradle.api.internal.artifacts.repositories.descriptor.FlatDirRepositoryDescriptor;
import org.gradle.api.internal.artifacts.repositories.descriptor.RepositoryDescriptor;
import org.gradle.api.model.ObjectFactory;

import java.util.ArrayList;

public class Gradle410RepositoryAdapter extends GradleRepositoryAdapter {

    Gradle410RepositoryAdapter(ObjectFactory objectFactory, Repository repository, DefaultMavenLocalArtifactRepository local) {
        super(objectFactory, repository, local);
    }


    @Override
    public RepositoryDescriptor getDescriptor() {
        return new FlatDirRepositoryDescriptor("ArtifacturalRepository", new ArrayList<>());
    }

}
