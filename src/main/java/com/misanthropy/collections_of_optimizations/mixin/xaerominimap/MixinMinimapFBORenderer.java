package com.misanthropy.collections_of_optimizations.mixin.xaerominimap;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.common.graphics.CustomVertexConsumers;
import xaero.common.minimap.MinimapProcessor;
import xaero.common.minimap.render.MinimapFBORenderer;
import xaero.hud.minimap.module.MinimapSession;

@Mixin(value = MinimapFBORenderer.class, remap = false)
public abstract class MixinMinimapFBORenderer {

    @Unique
    private long coo$lastFboRender;

    @Inject(
            method = "renderChunksToFBO",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$capMinimapRenderRate(MinimapSession minimapSession, GuiGraphics guiGraphics,
                                          MinimapProcessor minimap, Vec3 renderPos,
                                          ResourceKey<Level> mapDimension, double mapDimensionScale,
                                          int viewW, float partial, int level, boolean useWorldMap,
                                          boolean lockedNorth, int shape, double ps, double pc,
                                          boolean cave, CustomVertexConsumers cvc, CallbackInfo ci) {
        int cap = CoOConfig.xaeroMinimapRenderFpsCap;
        if (cap <= 0) {
            return;
        }

        long now = Util.getMillis();
        if (this.coo$lastFboRender != 0L && now - this.coo$lastFboRender < 1000L / cap) {
            ci.cancel();
            return;
        }
        this.coo$lastFboRender = now;
    }
}
