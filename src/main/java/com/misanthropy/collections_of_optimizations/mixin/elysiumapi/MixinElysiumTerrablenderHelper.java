package com.misanthropy.collections_of_optimizations.mixin.elysiumapi;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BiomeReplacerState;
import net.jadenxgamer.elysium_api.impl.compat.ElysiumTerrablenderHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ElysiumTerrablenderHelper.class, remap = false)
public abstract class MixinElysiumTerrablenderHelper {

    @Inject(
            method = "getCurrentBiome",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipUnusedResolution(
            MultiNoiseBiomeSource source,
            int x,
            int y,
            int z,
            Climate.Sampler sampler,
            CallbackInfoReturnable<Holder<Biome>> cir) {
        if (!CoOConfig.elysiumapiSkipUnusedBiomeReplacerLookup) {
            return;
        }
        Holder<Biome> placeholder = BiomeReplacerState.placeholder();
        if (placeholder == null) {
            return;
        }
        if (BiomeReplacerState.noReplacersConfigured()) {
            cir.setReturnValue(placeholder);
        }
    }

    @Inject(
            method = "getCurrentBiome",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$rememberPlaceholder(
            MultiNoiseBiomeSource source,
            int x,
            int y,
            int z,
            Climate.Sampler sampler,
            CallbackInfoReturnable<Holder<Biome>> cir) {
        if (CoOConfig.elysiumapiSkipUnusedBiomeReplacerLookup) {
            BiomeReplacerState.rememberPlaceholder(cir.getReturnValue());
        }
    }
}
