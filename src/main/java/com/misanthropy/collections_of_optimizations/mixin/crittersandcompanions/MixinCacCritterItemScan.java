package com.misanthropy.collections_of_optimizations.mixin.crittersandcompanions;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.goal.LeafInsectSearchLeavesGoal;
import io.github.bonsaistudi0s.crittersandcompanions.common.entity.brain.goal.SearchFoodGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {SearchFoodGoal.class, LeafInsectSearchLeavesGoal.class}, remap = false)
public abstract class MixinCacCritterItemScan {

    @Unique
    private int coo$itemScanDelay;

    @Unique
    private boolean coo$itemScanFound;

    @Inject(
            method = "m_8036_",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$throttleItemScan(CallbackInfoReturnable<Boolean> cir) {
        int interval = CoOConfig.crittersandcompanionsCritterItemScanInterval;
        if (interval <= 1 || this.coo$itemScanFound) {
            return;
        }
        if (--this.coo$itemScanDelay > 0) {
            cir.setReturnValue(false);
            return;
        }
        this.coo$itemScanDelay = interval;
    }

    @Inject(
            method = "m_8036_",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$recordItemScan(CallbackInfoReturnable<Boolean> cir) {
        this.coo$itemScanFound = cir.getReturnValueZ();
    }
}
