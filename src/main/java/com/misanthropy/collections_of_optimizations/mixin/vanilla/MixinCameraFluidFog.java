package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.Camera;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class MixinCameraFluidFog {

    @Unique
    private FogType coo$fluidInCamera;

    @Inject(
            method = "setPosition(Lnet/minecraft/world/phys/Vec3;)V",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$dropFluidMemo(Vec3 position, CallbackInfo ci) {
        this.coo$fluidInCamera = null;
    }

    @Inject(
            method = "getFluidInCamera()Lnet/minecraft/world/level/material/FogType;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$useFluidMemo(CallbackInfoReturnable<FogType> cir) {
        FogType memo = this.coo$fluidInCamera;
        if (memo != null && CoOConfig.vanillaMemoCameraFluid) {
            cir.setReturnValue(memo);
        }
    }

    @Inject(
            method = "getFluidInCamera()Lnet/minecraft/world/level/material/FogType;",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$storeFluidMemo(CallbackInfoReturnable<FogType> cir) {
        if (CoOConfig.vanillaMemoCameraFluid) {
            this.coo$fluidInCamera = cir.getReturnValue();
        }
    }
}
