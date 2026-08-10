package com.misanthropy.collections_of_optimizations.mixin.pickupnotifier;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.PickUpSpriteState;
import fuzs.pickupnotifier.client.gui.entry.ExperienceDisplayEntry;
import fuzs.pickupnotifier.client.gui.entry.ItemDisplayEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {ItemDisplayEntry.class, ExperienceDisplayEntry.class}, remap = false)
public abstract class MixinDisplayEntrySprite {

    @Inject(method = "renderSprite", at = @At("HEAD"), require = 0)
    private void coo$markOpaqueSprite(Minecraft minecraft, GuiGraphics guiGraphics, int posX, int posY, float scale, float fadeTime, CallbackInfo ci) {
        PickUpSpriteState.opaque = CoOConfig.pickupnotifierSkipOpaqueSpriteBuffer && fadeTime >= 1.0F;
    }
}
