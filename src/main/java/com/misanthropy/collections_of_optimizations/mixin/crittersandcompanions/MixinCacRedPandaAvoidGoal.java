package com.misanthropy.collections_of_optimizations.mixin.crittersandcompanions;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CacRedPandaTracker;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AvoidEntityGoal.class)
public abstract class MixinCacRedPandaAvoidGoal {

    @Shadow
    @Final
    protected Class<?> avoidClass;

    @Inject(
            method = "canUse",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipWhenNoRedPandaLoaded(CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.crittersandcompanionsGateRedPandaAvoidGoal
                && CacRedPandaTracker.noneLoaded(this.avoidClass)) {
            cir.setReturnValue(false);
        }
    }
}
