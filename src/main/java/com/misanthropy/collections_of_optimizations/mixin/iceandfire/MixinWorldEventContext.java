package com.misanthropy.collections_of_optimizations.mixin.iceandfire;

import com.github.alexthe666.iceandfire.pathfinding.raycoms.Pathfinding;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.WorldEventContext;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldEventContext.class, remap = false)
public abstract class MixinWorldEventContext {

    @Inject(method = "renderWorldLastEvent", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$skipIdlePathDebugRender(RenderLevelStageEvent event, CallbackInfo ci) {
        if (!CoOConfig.iceandfireSkipPathDebugRender) {
            return;
        }
        if (!Pathfinding.isDebug()) {
            ci.cancel();
        }
    }
}
