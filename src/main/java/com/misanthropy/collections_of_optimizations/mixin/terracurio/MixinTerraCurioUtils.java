package com.misanthropy.collections_of_optimizations.mixin.terracurio;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.confluence.mod.util.CuriosUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = CuriosUtils.class, remap = false)
public abstract class MixinTerraCurioUtils {

    @Inject(
            method = "noSameCurio(Lnet/minecraft/world/entity/LivingEntity;Ljava/lang/Class;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$noSameCurioClassMiss(LivingEntity living, Class<?> clazz, CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.terracurioCachedCurioLookup
                && CurioPresenceCache.equippedInstanceOf(living, clazz) == Boolean.FALSE) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "noSameCurio(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/Item;)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$noSameCurioItemMiss(LivingEntity living, Item curio, CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.terracurioCachedCurioLookup
                && !CurioPresenceCache.mayHaveEquipped(living, curio)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "findCurio(Lnet/minecraft/world/entity/LivingEntity;Ljava/lang/Class;)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$findCurioClassMiss(LivingEntity living, Class<?> clazz, CallbackInfoReturnable<Optional<?>> cir) {
        if (CoOConfig.terracurioCachedCurioLookup
                && CurioPresenceCache.equippedInstanceOf(living, clazz) == Boolean.FALSE) {
            cir.setReturnValue(Optional.empty());
        }
    }

    @Inject(
            method = "findCurio(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/Item;)Ljava/util/Optional;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$findCurioItemMiss(LivingEntity living, Item curio, CallbackInfoReturnable<Optional<?>> cir) {
        if (CoOConfig.terracurioCachedCurioLookup
                && !CurioPresenceCache.mayHaveEquipped(living, curio)) {
            cir.setReturnValue(Optional.empty());
        }
    }
}
