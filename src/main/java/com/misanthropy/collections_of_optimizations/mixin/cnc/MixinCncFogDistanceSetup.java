package com.misanthropy.collections_of_optimizations.mixin.cnc;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CncWechugeFogCache;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.ViewportEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.imasillylittleguy.cnc.procedures.FogDistanceSetupProcedure", remap = false)
public abstract class MixinCncFogDistanceSetup {

    @Inject(
            method = "renderFog",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$cachePerClientTick(ViewportEvent.RenderFog event, CallbackInfo ci) {
        if (!CoOConfig.cncCacheWechugeFogScan || event == null) {
            return;
        }
        ClientLevel level = Minecraft.getInstance().level;
        Camera camera = event.getCamera();
        Entity entity = camera == null ? null : camera.getEntity();
        if (level == null || entity == null) {
            return;
        }
        if (!CncWechugeFogCache.nearCamera(level, entity.getPosition((float) event.getPartialTick()))) {
            ci.cancel();
        }
    }
}
