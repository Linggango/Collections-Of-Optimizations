package com.misanthropy.collections_of_optimizations.mixin.adastra;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.PlanetDefaultsMemo;
import earth.terrarium.adastra.common.systems.TemperatureApiImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TemperatureApiImpl.class, remap = false)
public abstract class MixinTemperatureApiImpl {

    @Inject(
            method = "getTemperature(Lnet/minecraft/resources/ResourceKey;)S",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$serveMemoisedDimensionTemperature(ResourceKey<Level> dimension, CallbackInfoReturnable<Short> cir) {
        if (!CoOConfig.adastraMemoPlanetDefaults) {
            return;
        }
        Short hit = PlanetDefaultsMemo.lookupTemperature(dimension);
        if (hit != null) {
            cir.setReturnValue(hit);
        }
    }

    @Inject(
            method = "getTemperature(Lnet/minecraft/resources/ResourceKey;)S",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$storeDimensionTemperature(ResourceKey<Level> dimension, CallbackInfoReturnable<Short> cir) {
        if (CoOConfig.adastraMemoPlanetDefaults) {
            PlanetDefaultsMemo.storeTemperature(dimension, cir.getReturnValueS());
        }
    }
}
