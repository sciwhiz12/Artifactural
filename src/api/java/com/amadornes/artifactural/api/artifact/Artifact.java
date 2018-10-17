package com.amadornes.artifactural.api.artifact;

import com.amadornes.artifactural.api.cache.ArtifactCache;
import com.amadornes.artifactural.api.transform.ArtifactTransformer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface Artifact {

    static Artifact none() {
        return Internal.NO_ARTIFACT;
    }

    ArtifactIdentifier getIdentifier();

    ArtifactMetadata getMetadata();

    ArtifactType getType();

    Artifact withMetadata(ArtifactMetadata metadata);

    Artifact apply(ArtifactTransformer transformer);

    Artifact.Cached cache(ArtifactCache cache);

    default Artifact.Cached optionallyCache(ArtifactCache cache) {
        return this instanceof Artifact.Cached ? (Artifact.Cached) this : cache(cache);
    }

    boolean isPresent();

    InputStream openStream() throws IOException, MissingArtifactException;

    interface Cached extends Artifact {

        // Gets the file location, AND writes the file to disc if it hasn't already.
        File asFile() throws IOException, MissingArtifactException;

        // Gets the file location, but doesn't guarantee that it exists. As the wrapped Artifact may not of been written. What's the point of this?
        File getFileLocation() throws IOException, MissingArtifactException;

    }

}
