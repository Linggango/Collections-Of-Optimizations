package com.misanthropy.collections_of_optimizations.mixin.iceandfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.ai.DragonAITarget;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DragonAITarget.class, remap = false)
public abstract class MixinDragonAITarget {

    @Shadow
    @Final
    private EntityDragonBase dragon;

    @Inject(method = "m_7255_", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$clampTargetSearchHeight(double targetDistance, CallbackInfoReturnable<AABB> cir) {
        int height = CoOConfig.iceandfireDragonTargetSearchHeight;
        if (height < 0 || (double) height >= targetDistance) {
            return;
        }
        cir.setReturnValue(this.dragon.getBoundingBox().inflate(targetDistance, height, targetDistance));
    }
}
