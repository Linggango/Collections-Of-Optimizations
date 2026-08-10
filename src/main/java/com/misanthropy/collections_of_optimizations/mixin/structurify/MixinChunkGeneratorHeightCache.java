package com.misanthropy.collections_of_optimizations.mixin.structurify;

import com.faboslav.structurify.common.world.level.chunk.ChunkGeneratorHeightCache;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.StructurifyHeightCache;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChunkGeneratorHeightCache.class, remap = false)
public abstract class MixinChunkGeneratorHeightCache {

    @Inject(
            method = "getFirstFreeHeight",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$getFirstFreeHeight(ChunkGenerator chunkGenerator,
                                               int x,
                                               int z,
                                               Heightmap.Types heightmapType,
                                               LevelHeightAccessor heightAccessor,
                                               RandomState randomState,
                                               CallbackInfoReturnable<Integer> cir) {
        if (!CoOConfig.structurifyLeanHeightCache) {
            return;
        }
        int height = StructurifyHeightCache.freeHeight(
                chunkGenerator, x, z, heightmapType, heightAccessor, randomState);
        cir.setReturnValue(height == StructurifyHeightCache.ABSENT ? null : height);
    }

    @Inject(
            method = "putFirstFreeHeight",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$putFirstFreeHeight(ChunkGenerator chunkGenerator,
                                               int x,
                                               int z,
                                               Heightmap.Types heightmapType,
                                               LevelHeightAccessor heightAccessor,
                                               RandomState randomState,
                                               int height,
                                               CallbackInfo ci) {
        if (!CoOConfig.structurifyLeanHeightCache) {
            return;
        }
        StructurifyHeightCache.putFreeHeight(
                chunkGenerator, x, z, heightmapType, heightAccessor, randomState, height);
        ci.cancel();
    }

    @Inject(
            method = "getFirstOccupiedHeight",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$getFirstOccupiedHeight(ChunkGenerator chunkGenerator,
                                                   int x,
                                                   int z,
                                                   Heightmap.Types heightmapType,
                                                   LevelHeightAccessor heightAccessor,
                                                   RandomState randomState,
                                                   CallbackInfoReturnable<Integer> cir) {
        if (!CoOConfig.structurifyLeanHeightCache) {
            return;
        }
        int height = StructurifyHeightCache.freeHeight(
                chunkGenerator, x, z, heightmapType, heightAccessor, randomState);
        cir.setReturnValue(height == StructurifyHeightCache.ABSENT ? null : height - 1);
    }

    @Inject(
            method = "putFirstOccupiedHeight",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$putFirstOccupiedHeight(ChunkGenerator chunkGenerator,
                                                   int x,
                                                   int z,
                                                   Heightmap.Types heightmapType,
                                                   LevelHeightAccessor heightAccessor,
                                                   RandomState randomState,
                                                   int height,
                                                   CallbackInfo ci) {
        if (!CoOConfig.structurifyLeanHeightCache) {
            return;
        }
        StructurifyHeightCache.putFreeHeight(
                chunkGenerator, x, z, heightmapType, heightAccessor, randomState, height + 1);
        ci.cancel();
    }
}
