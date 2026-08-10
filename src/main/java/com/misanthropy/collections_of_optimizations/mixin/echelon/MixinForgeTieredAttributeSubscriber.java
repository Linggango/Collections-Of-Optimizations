package com.misanthropy.collections_of_optimizations.mixin.echelon;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.TierAttributeUuidCache;
import elocindev.tierify.forge.ForgeTieredAttributeSubscriber;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(value = ForgeTieredAttributeSubscriber.class, remap = false)
public abstract class MixinForgeTieredAttributeSubscriber {

    @WrapOperation(
            method = "onItemAttributeModifiers",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/UUID;nameUUIDFromBytes([B)Ljava/util/UUID;"
            ),
            require = 0
    )
    private static UUID coo$memoiseTierModifierUuid(byte[] name, Operation<UUID> original) {
        if (!CoOConfig.echelonCacheTierAttributeUuids) {
            return original.call(name);
        }
        UUID cached = TierAttributeUuidCache.lookup(name);
        if (cached != null) {
            return cached;
        }
        UUID computed = original.call(name);
        TierAttributeUuidCache.store(name, computed);
        return computed;
    }
}
