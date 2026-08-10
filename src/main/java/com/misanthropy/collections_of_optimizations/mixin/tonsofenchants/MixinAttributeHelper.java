package com.misanthropy.collections_of_optimizations.mixin.tonsofenchants;

import com.deltajay.tonsofenchants.util.PU;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PU.AttributeHelper.class, remap = false)
public abstract class MixinAttributeHelper {

    @Inject(
            method = "removeModifier",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 0
    )
    private static void coo$skipAbsentAttributeRemoval(LivingEntity entity, Attribute attribute, AttributeModifier modifier, CallbackInfo ci) {
        if (!CoOConfig.tonsofenchantsSkipAbsentAttributeRemoval) {
            return;
        }

        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance == null || instance.getModifier(modifier.getId()) == null) {
            ci.cancel();
        }
    }
}
