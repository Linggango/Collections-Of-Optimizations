package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = MMRenderType.class, remap = false)
public abstract class MixinMMRenderType {

    @Unique
    private static final Map<ResourceLocation, RenderType> COO$GLOWING_EFFECT = new HashMap<>();

    @Unique
    private static final Map<ResourceLocation, RenderType> COO$SOLAR_FLARE = new HashMap<>();

    @Inject(method = "getGlowingEffect", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$glowingEffectFromCache(ResourceLocation location, CallbackInfoReturnable<RenderType> cir) {
        if (!CoOConfig.mowziesmobsCacheEffectRenderTypes) {
            return;
        }
        RenderType cached = COO$GLOWING_EFFECT.get(location);
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }

    @Inject(method = "getGlowingEffect", at = @At("RETURN"), require = 0)
    private static void coo$rememberGlowingEffect(ResourceLocation location, CallbackInfoReturnable<RenderType> cir) {
        if (CoOConfig.mowziesmobsCacheEffectRenderTypes && cir.getReturnValue() != null) {
            COO$GLOWING_EFFECT.put(location, cir.getReturnValue());
        }
    }

    @Inject(method = "getSolarFlare", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$solarFlareFromCache(ResourceLocation location, CallbackInfoReturnable<RenderType> cir) {
        if (!CoOConfig.mowziesmobsCacheEffectRenderTypes) {
            return;
        }
        RenderType cached = COO$SOLAR_FLARE.get(location);
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }

    @Inject(method = "getSolarFlare", at = @At("RETURN"), require = 0)
    private static void coo$rememberSolarFlare(ResourceLocation location, CallbackInfoReturnable<RenderType> cir) {
        if (CoOConfig.mowziesmobsCacheEffectRenderTypes && cir.getReturnValue() != null) {
            COO$SOLAR_FLARE.put(location, cir.getReturnValue());
        }
    }
}
