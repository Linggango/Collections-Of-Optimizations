package com.misanthropy.collections_of_optimizations.mixin.terracurio;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.TerraCurioAttributeMirror;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.confluence.mod.misc.ModAttributes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Hashtable;
import java.util.Map;

@Mixin(value = ModAttributes.class, remap = false)
public abstract class MixinTerraCurioAttributes {

    @Shadow
    @Final
    private static Hashtable<Attribute, Attribute> MAP;

    @Inject(method = "readJsonConfig", at = @At("RETURN"), require = 0)
    private static void coo$mirrorAttributeMap(CallbackInfo ci) {
        TerraCurioAttributeMirror.snapshot(MAP);
    }

    @Inject(
            method = "hasCustomAttribute",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$hasCustomAttributeFast(Attribute attribute, CallbackInfoReturnable<Boolean> cir) {
        Map<Attribute, Attribute> mirror = TerraCurioAttributeMirror.mirror();
        if (CoOConfig.terracurioLeanAttributeMap && mirror != null) {
            cir.setReturnValue(mirror.get(attribute) != null);
        }
    }

    @Inject(
            method = "getCustomAttribute",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$getCustomAttributeFast(Attribute attribute, CallbackInfoReturnable<Attribute> cir) {
        Map<Attribute, Attribute> mirror = TerraCurioAttributeMirror.mirror();
        if (CoOConfig.terracurioLeanAttributeMap && mirror != null) {
            Attribute target = mirror.get(attribute);
            cir.setReturnValue(target == null ? attribute : target);
        }
    }
}
