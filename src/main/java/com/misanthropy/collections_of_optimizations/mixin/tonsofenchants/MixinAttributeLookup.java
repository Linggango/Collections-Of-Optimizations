package com.misanthropy.collections_of_optimizations.mixin.tonsofenchants;

import com.deltajay.tonsofenchants.util.PU;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PU.AttributeHelper.class, remap = false)
public abstract class MixinAttributeLookup {

    @Inject(
            method = "getPlayerCurrentAttribute",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 0
    )
    private static void coo$leanAttributeLookup(LivingEntity entity, Attribute attribute, CallbackInfoReturnable<PU.OptionalPair<AttributeInstance, Double>> cir) {
        if (!CoOConfig.tonsofenchantsLeanAttributeLookup) {
            return;
        }

        AttributeInstance instance = entity.getAttribute(attribute);
        cir.setReturnValue(new PU.OptionalPair<>(instance, instance == null ? 0.0 : instance.getValue()));
    }
}
