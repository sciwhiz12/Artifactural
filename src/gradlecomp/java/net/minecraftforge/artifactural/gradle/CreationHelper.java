package net.minecraftforge.artifactural.gradle;

import org.gradle.api.artifacts.ComponentMetadataListerDetails;
import org.gradle.api.artifacts.ComponentMetadataSupplierDetails;
import org.gradle.api.internal.artifacts.ivyservice.ArtifactCacheLockingManager;
import org.gradle.api.internal.artifacts.ivyservice.resolutionstrategy.ExternalResourceCachePolicy;
import org.gradle.api.internal.artifacts.repositories.maven.MavenMetadataLoader;
import org.gradle.api.internal.artifacts.repositories.metadata.ImmutableMetadataSources;
import org.gradle.api.internal.artifacts.repositories.metadata.MetadataArtifactProvider;
import org.gradle.api.internal.artifacts.repositories.resolver.MavenResolver;
import org.gradle.api.internal.file.temp.TemporaryFileProvider;
import org.gradle.cache.internal.ProducerGuard;
import org.gradle.internal.action.InstantiatingAction;
import org.gradle.internal.component.external.model.ModuleComponentArtifactIdentifier;
import org.gradle.internal.component.external.model.ModuleComponentArtifactMetadata;
import org.gradle.internal.hash.ChecksumService;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.internal.resource.ExternalResourceName;
import org.gradle.internal.resource.cached.CachedExternalResourceIndex;
import org.gradle.internal.resource.local.FileResourceListener;
import org.gradle.internal.resource.local.FileResourceRepository;
import org.gradle.internal.resource.local.FileStore;
import org.gradle.internal.resource.local.LocallyAvailableResourceFinder;
import org.gradle.internal.resource.transfer.CacheAwareExternalResourceAccessor;
import org.gradle.internal.resource.transfer.DefaultCacheAwareExternalResourceAccessor;
import org.gradle.internal.resource.transport.file.FileTransport;
import org.gradle.util.BuildCommencedTimeProvider;

import javax.annotation.Nullable;
import java.net.URI;

class CreationHelper {
    public static FileTransport createFileTransport(FileTransport original, FileResourceRepository newRepository) {

        // (FileTransport as AbstractRepositoryTransport).(String)name
        String name = ReflectionUtils.get(original, "name");
        // FileTransport.(FileCacheAwareExternalResourceAccessor)resourceAccessor.delegate
        DefaultCacheAwareExternalResourceAccessor delegate = ReflectionUtils.get(original, "resourceAccessor.delegate");
        // DefaultCacheAwareExternalResourceAccessor.cachedExternalResourceIndex
        CachedExternalResourceIndex<String> cachedExternalResourceIndex = ReflectionUtils.get(delegate, "cachedExternalResourceIndex");
        // DefaultCacheAwareExternalResourceAccessor.temporaryFileProvider
        TemporaryFileProvider temporaryFileProvider = ReflectionUtils.get(delegate, "temporaryFileProvider");
        // DefaultCacheAwareExternalResourceAccessor.timeProvider
        BuildCommencedTimeProvider timeProvider = ReflectionUtils.get(delegate, "timeProvider");
        // DefaultCacheAwareExternalResourceAccessor.artifactCacheLockingManager
        ArtifactCacheLockingManager artifactCacheLockingManager = ReflectionUtils.get(delegate, "artifactCacheLockingManager");
        // DefaultCacheAwareExternalResourceAccessor.producerGuard
        ProducerGuard<ExternalResourceName> producerGuard = ReflectionUtils.get(delegate, "producerGuard");
        // DefaultCacheAwareExternalResourceAccessor.checksumService
        ChecksumService checksumService = ReflectionUtils.get(delegate, "checksumService");
        // FileTransport.(FileCacheAwareExternalResourceAccessor)resourceAccessor.listener
        FileResourceListener listener = ReflectionUtils.get(original, "listener");

        return new FileTransport(name, newRepository, cachedExternalResourceIndex, temporaryFileProvider, timeProvider, artifactCacheLockingManager,
                producerGuard, checksumService, listener);
    }

    public static DefaultCacheAwareExternalResourceAccessor createDefaultCacheAwareExternalResourceAccessor(DefaultCacheAwareExternalResourceAccessor original,
                                                                                                            FileResourceRepository newDelegate,
                                                                                                            FileResourceRepository newFileResourceRepository) {

        // DefaultCacheAwareExternalResourceAccessor.cachedExternalResourceIndex
        CachedExternalResourceIndex<String> cachedExternalResourceIndex = ReflectionUtils.get(original, "cachedExternalResourceIndex");
        // DefaultCacheAwareExternalResourceAccessor.timeProvider
        BuildCommencedTimeProvider timeProvider = ReflectionUtils.get(original, "timeProvider");
        // DefaultCacheAwareExternalResourceAccessor.temporaryFileProvider
        TemporaryFileProvider temporaryFileProvider = ReflectionUtils.get(original, "temporaryFileProvider");
        // DefaultCacheAwareExternalResourceAccessor.artifactCacheLockingManager
        ArtifactCacheLockingManager artifactCacheLockingManager = ReflectionUtils.get(original, "artifactCacheLockingManager");
        // DefaultCacheAwareExternalResourceAccessor.producerGuard
        ProducerGuard<ExternalResourceName> producerGuard = ReflectionUtils.get(original, "producerGuard");
        // DefaultCacheAwareExternalResourceAccessor.externalResourceCachePolicy
        ExternalResourceCachePolicy externalResourceCachePolicy = ReflectionUtils.get(original, "externalResourceCachePolicy");
        // DefaultCacheAwareExternalResourceAccessor.checksumService
        ChecksumService checksumService = ReflectionUtils.get(original, "checksumService");

        return new DefaultCacheAwareExternalResourceAccessor(newDelegate, cachedExternalResourceIndex, timeProvider, temporaryFileProvider,
                artifactCacheLockingManager, externalResourceCachePolicy, producerGuard, newFileResourceRepository, checksumService);
    }

    public static MavenMetadataLoader createMavenMetaDataLoader(MavenMetadataLoader original,
                                                                CacheAwareExternalResourceAccessor newCacheAwareExternalResourceAccessor) {

        // MavenMetadataLoader.resourcesFileStore
        FileStore<String> resourcesFileStore = ReflectionUtils.get(original, "resourcesFileStore");

        return new MavenMetadataLoader(newCacheAwareExternalResourceAccessor, resourcesFileStore);
    }

    public static MavenResolver createMavenResolver(MavenResolver original, FileTransport newTransport, MavenMetadataLoader newMavenMetaDataLoader) {
        String name = original.getName();
        URI root = original.getRoot();
        // (MavenResolver as ExternalResourceResolver).locallyAvailableResourceFinder
        LocallyAvailableResourceFinder<ModuleComponentArtifactMetadata> locallyAvailableResourceFinder = ReflectionUtils.get(original, "locallyAvailableResourceFinder");
        // (MavenResolver as ExternalResourceResolver).artifactFileStore
        FileStore<ModuleComponentArtifactIdentifier> artifactFileStore = ReflectionUtils.get(original, "artifactFileStore");
        ImmutableMetadataSources metadataSources = original.getMetadataSources();
        // (MavenResolver as ExternalResourceResolver).metadataArtifactProvider
        MetadataArtifactProvider metadataArtifactProvider = ReflectionUtils.get(original, "metadataArtifactProvider");
        // (MavenResolver as ExternalResourceResolver).componentMetadataSupplierFactory
        @Nullable InstantiatingAction<ComponentMetadataSupplierDetails> componentMetadataSupplierFactory = ReflectionUtils.get(original, "componentMetadataSupplierFactory");
        // (MavenResolver as ExternalResourceResolver).providedVersionLister
        @Nullable InstantiatingAction<ComponentMetadataListerDetails> versionListerFactory = ReflectionUtils.get(original, "providedVersionLister");
        Instantiator injector = original.getComponentMetadataInstantiator();
        // (MavenResolver as ExternalResourceResolver).checksumService
        ChecksumService checksumService = ReflectionUtils.get(original, "checksumService");

        return new MavenResolver(name, root, newTransport, locallyAvailableResourceFinder, artifactFileStore, metadataSources,
                metadataArtifactProvider, newMavenMetaDataLoader, componentMetadataSupplierFactory, versionListerFactory,
                injector, checksumService);
    }
}
