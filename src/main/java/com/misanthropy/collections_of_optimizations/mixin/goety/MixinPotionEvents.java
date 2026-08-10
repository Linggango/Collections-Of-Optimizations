package com.misanthropy.collections_of_optimizations.mixin.goety;

import com.Polarice3.Goety.common.events.PotionEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.GoetyModifierCache;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(value = PotionEvents.class, remap = false)
public abstract class MixinPotionEvents {

    @WrapOperation(
            method = {"LivingEffects", "ChargeEffect"},
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/UUID;fromString(Ljava/lang/String;)Ljava/util/UUID;"
            ),
            require = 0
    )
    private static UUID coo$memoiseModifierUuid(String text, Operation<UUID> original) {
        if (!CoOConfig.goetyMemoAttributeModifiers) {
            return original.call(text);
        }
        UUID cached = GoetyModifierCache.uuid(text);
        if (cached != null) {
            return cached;
        }
        UUID computed = original.call(text);
        GoetyModifierCache.storeUuid(text, computed);
        return computed;
    }

    @WrapOperation(
            method = {"LivingEffects", "ChargeEffect"},
            at = @At(
                    value = "NEW",
                    target = "(Ljava/util/UUID;Ljava/lang/String;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;"
            ),
            require = 0
    )
    private static AttributeModifier coo$shareModifier(UUID id, String name, double amount,
                                                       AttributeModifier.Operation operation,
                                                       Operation<AttributeModifier> original) {
        if (!CoOConfig.goetyMemoAttributeModifiers) {
            return original.call(id, name, amount, operation);
        }
        AttributeModifier cached = GoetyModifierCache.modifier(id, name, amount, operation);
        if (cached != null) {
            return cached;
        }
        AttributeModifier built = original.call(id, name, amount, operation);
        GoetyModifierCache.storeModifier(built);
        return built;
    }
}
