package com.amadornes.artifactural.api.artifact;

public class MissingArtifactException extends RuntimeException {
    private static final long serialVersionUID = 4902516963452435653L;

    public MissingArtifactException(ArtifactIdentifier identifier) {
        super("Could not find artifact: " + identifier);
    }

}
