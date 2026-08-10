package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.GeckoDynamicChain;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.render.entity.MowzieGeoEntityRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GeckoDynamicChain.class, remap = false)
public abstract class MixinGeckoDynamicChain {

    @Unique
    private Matrix4f coo$chainRenderMatrix;

    @Inject(method = "setChainFromRenderPos", at = @At("HEAD"), require = 0)
    private void coo$clearHoistedMatrix(
            MowzieGeoBone[] chainOrig,
            MowzieGeoBone[] chainDynamic,
            double alpha,
            MowzieGeoEntityRenderer renderer,
            CallbackInfo ci) {
        this.coo$chainRenderMatrix = null;
    }

    @WrapOperation(
            method = "setChainFromRenderPos",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/bobmowzie/mowziesmobs/client/render/entity/MowzieGeoEntityRenderer;getModelRenderMatrix()Lorg/joml/Matrix4f;"
            ),
            require = 0
    )
    private Matrix4f coo$hoistChainRenderMatrix(MowzieGeoEntityRenderer renderer, Operation<Matrix4f> original) {
        if (!CoOConfig.mowziesmobsHoistChainRenderMatrix) {
            return original.call(renderer);
        }
        Matrix4f cached = this.coo$chainRenderMatrix;
        if (cached == null) {
            cached = original.call(renderer);
            this.coo$chainRenderMatrix = cached;
        }
        return cached;
    }

    @ModifyArg(
            method = "updateChain",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/bobmowzie/mowziesmobs/client/model/tools/dynamics/GeckoDynamicChain;updateSpringConstraint(FFFZFIF)V"
            ),
            index = 5,
            require = 0
    )
    private int coo$capChainSubsteps(int numUpdates) {
        int cap = CoOConfig.mowziesmobsDynamicChainSubstepCap;
        return cap <= 0 ? numUpdates : Math.min(numUpdates, cap);
    }
}
