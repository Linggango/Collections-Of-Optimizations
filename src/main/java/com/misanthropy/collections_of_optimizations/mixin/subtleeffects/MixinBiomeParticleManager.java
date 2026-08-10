package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import einstein.subtle_effects.ticking.biome_particles.BiomeParticleManager;
import einstein.subtle_effects.ticking.biome_particles.BiomeParticleSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.List;

@Mixin(value = BiomeParticleManager.class, remap = false)
public abstract class MixinBiomeParticleManager {

    @Shadow
    @Final
    private static List<BiomeParticleSettings> REGISTERED;

    @ModifyConstant(
            method = "tickBiomeParticles",
            constant = @Constant(intValue = 100),
            require = 0,
            expect = 0
    )
    private static int coo$capBiomeParticleScan(int original) {
        if (!CoOConfig.subtleeffectsCapBiomeParticleScan) {
            return original;
        }

        int limit = 0;
        for (int i = 0; i < REGISTERED.size(); i++) {
            BiomeParticleSettings settings = REGISTERED.get(i);
            if (settings.getBiomes().isEmpty()) {
                continue;
            }

            int density = settings.getDensity();
            if (density > limit) {
                limit = density;
            }
        }

        return Math.min(limit, original);
    }
}
