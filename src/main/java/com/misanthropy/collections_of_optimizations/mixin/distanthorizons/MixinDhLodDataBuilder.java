package com.misanthropy.collections_of_optimizations.mixin.distanthorizons;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.DhChunkBiomeMemo;
import com.seibel.distanthorizons.core.dataObjects.transformers.LodDataBuilder;
import com.seibel.distanthorizons.core.wrapperInterfaces.chunk.IChunkWrapper;
import com.seibel.distanthorizons.core.wrapperInterfaces.world.IBiomeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LodDataBuilder.class, remap = false)
public abstract class MixinDhLodDataBuilder {

    @WrapOperation(
            method = "createFromChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/seibel/distanthorizons/core/wrapperInterfaces/chunk/IChunkWrapper;getBiome(III)Lcom/seibel/distanthorizons/core/wrapperInterfaces/world/IBiomeWrapper;"
            ),
            require = 0
    )
    private static IBiomeWrapper coo$memoQuartBiome(IChunkWrapper chunkWrapper, int relX, int relY, int relZ,
                                                    Operation<IBiomeWrapper> original) {
        if (!CoOConfig.distanthorizonsCacheChunkBiomeLookup) {
            return original.call(chunkWrapper, relX, relY, relZ);
        }
        DhChunkBiomeMemo memo = DhChunkBiomeMemo.local();
        IBiomeWrapper cached = memo.tryGet(chunkWrapper, relX, relY, relZ);
        if (cached != null) {
            return cached;
        }
        IBiomeWrapper biome = original.call(chunkWrapper, relX, relY, relZ);
        if (biome != null) {
            memo.put(chunkWrapper, relX, relY, relZ, biome);
        }
        return biome;
    }
}
