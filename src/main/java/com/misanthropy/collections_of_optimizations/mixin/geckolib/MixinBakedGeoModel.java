package com.misanthropy.collections_of_optimizations.mixin.geckolib;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(value = BakedGeoModel.class, remap = false)
public abstract class MixinBakedGeoModel {

    @Unique
    private volatile Map<String, Optional<GeoBone>> coo$boneLookup;

    @Inject(
            method = "getBone",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$readBoneLookup(String name, CallbackInfoReturnable<Optional<GeoBone>> cir) {
        if (!CoOConfig.geckolibCacheBoneLookup) {
            return;
        }
        Map<String, Optional<GeoBone>> cache = this.coo$boneLookup;
        if (cache == null) {
            return;
        }
        Optional<GeoBone> hit = cache.get(name);
        if (hit != null) {
            cir.setReturnValue(hit);
        }
    }

    @Inject(
            method = "getBone",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$writeBoneLookup(String name, CallbackInfoReturnable<Optional<GeoBone>> cir) {
        if (!CoOConfig.geckolibCacheBoneLookup) {
            return;
        }
        Map<String, Optional<GeoBone>> cache = this.coo$boneLookup;
        if (cache == null) {
            cache = new ConcurrentHashMap<>();
            this.coo$boneLookup = cache;
        }
        Optional<GeoBone> result = cir.getReturnValue();
        if (result != null) {
            cache.put(name, result);
        }
    }
}
