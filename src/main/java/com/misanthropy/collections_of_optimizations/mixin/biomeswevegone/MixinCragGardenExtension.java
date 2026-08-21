package com.misanthropy.collections_of_optimizations.mixin.biomeswevegone;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BwgTerrainChunkFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(targets = "net.potionstudios.biomeswevegone.world.level.levelgen.customterrain.CragGardenExtension", remap = false)
public abstract class MixinCragGardenExtension {

    @Inject(
            method = "runCragGardenExtension",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipChunksWithoutCragGardens(Function<BlockPos, Holder<Biome>> biomeGetter,
                                                         ChunkAccess chunk,
                                                         long worldSeed,
                                                         NormalNoise.NoiseParameters noiseParameters,
                                                         NormalNoise.NoiseParameters cliffSpacingParams,
                                                         CallbackInfo ci) {
        if (CoOConfig.biomeswevegoneSkipForeignChunkTerrain && !BwgTerrainChunkFilter.hasCragGardens(chunk)) {
            ci.cancel();
        }
    }
}
