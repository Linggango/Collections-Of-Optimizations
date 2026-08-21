package com.misanthropy.collections_of_optimizations.mixin.xaeroworldmap;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "xaero.map.MapProcessor", remap = false)
public abstract class MixinMapProcessor {

    @Unique
    private long coo$lastRenderProcess;

    @Inject(method = "onRenderProcess", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$throttleRenderProcess(Minecraft mc, CallbackInfo ci) {
        int interval = CoOConfig.xaeroworldmapRenderProcessInterval;
        if (interval <= 0) {
            return;
        }

        Screen screen = mc.screen;
        if (screen != null && screen.getClass().getName().startsWith("xaero.map.gui.")) {
            return;
        }

        long now = System.nanoTime() / 1_000_000L;
        if (now - this.coo$lastRenderProcess < interval) {
            ci.cancel();
            return;
        }
        this.coo$lastRenderProcess = now;
    }
}
