package com.misanthropy.collections_of_optimizations.mixin.structurify;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.StructurifyHeightCache;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChunkGenerator.class, priority = 1500)
public abstract class MixinChunkGeneratorHeightHooks {

    @Inject(
            method = "getFirstFreeHeight",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$cachedFreeHeight(int x,
                                      int z,
                                      Heightmap.Types heightmapType,
                                      LevelHeightAccessor heightAccessor,
                                      RandomState randomState,
                                      CallbackInfoReturnable<Integer> cir) {
        if (!CoOConfig.structurifyLeanHeightCache) {
            return;
        }
        int height = StructurifyHeightCache.freeHeight(
                (ChunkGenerator) (Object) this, x, z, heightmapType, heightAccessor, randomState);
        if (height != StructurifyHeightCache.ABSENT) {
            cir.setReturnValue(height);
        }
    }

    @Inject(
            method = "getFirstOccupiedHeight",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$cachedOccupiedHeight(int x,
                                          int z,
                                          Heightmap.Types heightmapType,
                                          LevelHeightAccessor heightAccessor,
                                          RandomState randomState,
                                          CallbackInfoReturnable<Integer> cir) {
        if (!CoOConfig.structurifyLeanHeightCache) {
            return;
        }
        int height = StructurifyHeightCache.freeHeight(
                (ChunkGenerator) (Object) this, x, z, heightmapType, heightAccessor, randomState);
        if (height != StructurifyHeightCache.ABSENT) {
            cir.setReturnValue(height - 1);
        }
    }
}
