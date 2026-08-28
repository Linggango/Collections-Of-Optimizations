package com.misanthropy.collections_of_optimizations.mixin.terracurio;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import com.misanthropy.collections_of_optimizations.core.TerraCurioAggroState;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import org.confluence.mod.event.ForgeEvents;
import org.confluence.mod.item.curio.combat.IHoneycomb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "livingChangeTarget", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipIdleAggroScan(LivingChangeTargetEvent event, CallbackInfo ci) {
        if (!CoOConfig.terracurioSkipIdleAggroScan) {
            return;
        }

        LivingEntity self = event.getEntity();
        if (self != null && !TerraCurioAggroState.anyAggro(self.level())) {
            ci.cancel();
        }
    }
}
