package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;

@Mixin(value = MowzieGeoModel.class, remap = false)
public abstract class MixinMowzieGeoModel {

    @Inject(method = "getMowzieBone", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$boneWithoutOptional(String boneName, CallbackInfoReturnable<MowzieGeoBone> cir) {
        if (!CoOConfig.mowziesmobsLeanBoneLookup) {
            return;
        }
        cir.setReturnValue((MowzieGeoBone) coo$rawBone((MowzieGeoModel<?>) (Object) this, boneName));
    }

    @Inject(method = "getControllerValue", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$controllerValueWithoutOptional(String controllerName, CallbackInfoReturnable<Float> cir) {
        if (!CoOConfig.mowziesmobsLeanBoneLookup) {
            return;
        }
        MowzieGeoModel<?> self = (MowzieGeoModel<?>) (Object) this;
        if (!self.isInitialized()) {
            cir.setReturnValue(0.0F);
            return;
        }
        GeoBone bone = coo$rawBone(self, controllerName);
        cir.setReturnValue(bone == null ? 0.0F : bone.getPosX());
    }

    @Inject(method = "getControllerValueInverted", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$invertedControllerValueWithoutOptional(String controllerName, CallbackInfoReturnable<Float> cir) {
        if (!CoOConfig.mowziesmobsLeanBoneLookup) {
            return;
        }
        MowzieGeoModel<?> self = (MowzieGeoModel<?>) (Object) this;
        if (!self.isInitialized()) {
            cir.setReturnValue(1.0F);
            return;
        }
        GeoBone bone = coo$rawBone(self, controllerName);
        cir.setReturnValue(bone == null ? 1.0F : 1.0F - bone.getPosX());
    }

    private static GeoBone coo$rawBone(MowzieGeoModel<?> model, String boneName) {
        CoreGeoBone bone = model.getAnimationProcessor().getBone(boneName);
        return bone instanceof GeoBone geoBone ? geoBone : null;
    }
}
