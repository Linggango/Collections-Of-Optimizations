package com.misanthropy.collections_of_optimizations.mixin.crittersandcompanions;

import com.misanthropy.collections_of_optimizations.core.CacRedPandaTracker;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentEntitySectionManager.class)
public abstract class MixinCacEntityTracking {

    @Inject(
            method = "startTracking",
            at = @At("HEAD"),
            require = 0
    )
    private void coo$cacTrackRedPandaStart(EntityAccess entity, CallbackInfo ci) {
        CacRedPandaTracker.onTrackingStart(entity);
    }

    @Inject(
            method = "stopTracking",
            at = @At("HEAD"),
            require = 0
    )
    private void coo$cacTrackRedPandaEnd(EntityAccess entity, CallbackInfo ci) {
        CacRedPandaTracker.onTrackingEnd(entity);
    }
}
