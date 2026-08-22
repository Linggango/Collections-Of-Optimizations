package com.misanthropy.collections_of_optimizations.mixin.terracurio;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.event.ForgeEvents;
import org.confluence.mod.item.curio.combat.IHoneycomb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ForgeEvents.class, remap = false)
public abstract class MixinTerraCurioForgeEvents {

    @WrapWithCondition(
            method = "livingHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/confluence/mod/item/curio/combat/IHoneycomb;apply(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/util/RandomSource;)V"
            ),
            require = 0
    )
    private static boolean coo$skipAbsentHoneycomb(LivingEntity living, RandomSource random) {
        if (!CoOConfig.terracurioCachedCurioLookup) {
            return true;
        }
        return CurioPresenceCache.equippedInstanceOf(living, IHoneycomb.class) != Boolean.FALSE;
    }
}
