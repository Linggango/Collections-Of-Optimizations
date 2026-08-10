package com.misanthropy.collections_of_optimizations.mixin.saintsdragons;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

@Pseudo
@Mixin(targets = "com.leon.saintsdragons.client.renderer.DragonGeoEntityRenderer", remap = false)
public abstract class MixinDragonGeoEntityRenderer {

    @Unique
    private Set<Object> coo$trackingEnabledModels;

    @Inject(
            method = "enableTrackingForBones",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipRedundantBoneTracking(BakedGeoModel model, CallbackInfo ci) {
        if (!CoOConfig.saintsdragonsSkipRedundantBoneTracking || model == null) {
            return;
        }
        Set<Object> seen = this.coo$trackingEnabledModels;
        if (seen == null) {
            seen = Collections.newSetFromMap(new IdentityHashMap<>());
            this.coo$trackingEnabledModels = seen;
        }
        if (!seen.add(model)) {
            ci.cancel();
        }
    }
}
