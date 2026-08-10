package com.misanthropy.collections_of_optimizations.mixin.w2w2;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.WaypointSaveQueue;
import fr.shoqapik.w2w2.W2w2Mod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.settings.ModSettings;

@Mixin(value = W2w2Mod.class, remap = false)
public abstract class MixinW2w2Mod {

    @WrapOperation(
            method = "onWaystoneUpdated",
            at = @At(
                    value = "INVOKE",
                    target = "Lxaero/common/settings/ModSettings;saveWaypoints(Lxaero/common/minimap/waypoints/WaypointWorld;)V"
            ),
            require = 0
    )
    private void coo$deferWaypointSave(ModSettings settings, WaypointWorld world, Operation<Void> original) {
        if (!CoOConfig.w2w2DeferWaypointSave) {
            original.call(settings, world);
            return;
        }
        WaypointSaveQueue.queue(settings, world);
    }
}
