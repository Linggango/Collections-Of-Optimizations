package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public abstract class MixinStringRenderOutputFontSet {

    @Unique
    private ResourceLocation coo$lastFontName;

    @Unique
    private FontSet coo$lastFontSet;

    @WrapOperation(
            method = "accept",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Font;getFontSet(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/gui/font/FontSet;"
            ),
            require = 0
    )
    private FontSet coo$memoFontSet(Font font, ResourceLocation name, Operation<FontSet> original) {
        if (!CoOConfig.vanillaMemoGlyphFontSet) {
            return original.call(font, name);
        }
        if (this.coo$lastFontSet == null || this.coo$lastFontName != name) {
            this.coo$lastFontName = name;
            this.coo$lastFontSet = original.call(font, name);
        }
        return this.coo$lastFontSet;
    }
}
