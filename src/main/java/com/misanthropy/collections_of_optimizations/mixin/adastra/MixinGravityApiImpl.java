package com.misanthropy.collections_of_optimizations.mixin.adastra;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.PlanetDefaultsMemo;
import earth.terrarium.adastra.common.systems.GravityApiImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GravityApiImpl.class, remap = false)
public abstract class MixinGravityApiImpl {

    @Inject(
            method = "getGravity(Lnet/minecraft/resources/ResourceKey;)F",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$serveMemoisedDimensionGravity(ResourceKey<Level> dimension, CallbackInfoReturnable<Float> cir) {
        if (!CoOConfig.adastraMemoPlanetDefaults) {
            return;
        }
        Float hit = PlanetDefaultsMemo.lookupGravity(dimension);
        if (hit != null) {
            cir.setReturnValue(hit);
        }
    }

    @Inject(
            method = "getGravity(Lnet/minecraft/resources/ResourceKey;)F",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$storeDimensionGravity(ResourceKey<Level> dimension, CallbackInfoReturnable<Float> cir) {
        if (CoOConfig.adastraMemoPlanetDefaults) {
            PlanetDefaultsMemo.storeGravity(dimension, cir.getReturnValueF());
        }
    }
}
