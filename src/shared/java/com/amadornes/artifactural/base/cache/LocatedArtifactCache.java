package com.amadornes.artifactural.base.cache;

import com.amadornes.artifactural.api.artifact.Artifact;
import com.amadornes.artifactural.api.artifact.ArtifactIdentifier;
import com.amadornes.artifactural.base.util.PatternReplace;

import java.io.File;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocatedArtifactCache extends ArtifactCacheBase {
    private static final String PATTERN = "[group]/[name](/[meta_hash])/[version]/[name]-[version](-[classifier])(-[specifier]).[extension]";
    private final File path;

    public LocatedArtifactCache(File path) {
        this.path = path;
    }

    @Override
    public Artifact.Cached store(Artifact artifact) {
        return doStore(getPath(artifact), artifact);
    }

    public File getPath(Artifact artifact) {
        ArtifactIdentifier identifier = artifact.getIdentifier();
        Map<String, String> names = Stream.of(
            entry("group", identifier.getGroup()),
            entry("name", identifier.getName()),
            entry("version", identifier.getVersion()),
            entry("classifier", identifier.getClassifier()),
            entry("extension", identifier.getExtension()),
            //entry("specifier", specifier), /?
            entry("meta_hash", artifact.getMetadata().getHash())
        ).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        return new File(path, PatternReplace.replace(PATTERN, names));
    }

    private static <K,V> Entry<K,V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @Override
    public String toString() {
        return "LocatedArtifactCache(" + path + ")";
    }

}
