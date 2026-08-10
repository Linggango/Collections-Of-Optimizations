package com.misanthropy.collections_of_optimizations.mixin.pickupnotifier;

import com.misanthropy.collections_of_optimizations.core.PickUpSpriteState;
import fuzs.pickupnotifier.client.util.TransparencyBuffer;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TransparencyBuffer.class, remap = false)
public abstract class MixinTransparencyBuffer {

    @Inject(method = "prepareExtraFramebuffer", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipFramebufferBind(CallbackInfo ci) {
        if (PickUpSpriteState.opaque) {
            ci.cancel();
        }
    }

    @Inject(method = "drawExtraFramebuffer", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipFramebufferBlit(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (PickUpSpriteState.opaque) {
            ci.cancel();
        }
    }
}
