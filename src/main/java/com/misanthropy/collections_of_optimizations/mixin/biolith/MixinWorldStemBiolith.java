package com.misanthropy.collections_of_optimizations.mixin.biolith;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.terraformersmc.biolith.impl.biome.BiomeCoordinator;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.WorldStem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = WorldStem.class, priority = 100)
public class MixinWorldStemBiolith {

    @ModifyVariable(
            method = "<init>",
            at = @At("HEAD"),
            argsOnly = true,
            index = 3,
            require = 0
    )
    private static LayeredRegistryAccess<RegistryLayer> coo$captureRegistriesEarly(LayeredRegistryAccess<RegistryLayer> registries) {
        if (CoOConfig.biolithEarlyRegistryCapture && registries != null) {
            BiomeCoordinator.setRegistryManager(registries);
        }
        return registries;
    }
}
