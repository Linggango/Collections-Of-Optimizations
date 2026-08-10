package com.misanthropy.collections_of_optimizations.mixin.goetyrevelation;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.item.AscensionHalo;
import z1gned.goetyrevelation.item.BrokenAscensionHalo;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(value = ATAHelper.class, remap = false)
public abstract class MixinATAHelper {

    @Inject(method = "hasHalo", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$cachedHasHalo(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        Boolean answer = coo$lookup(entity, AscensionHalo.class);
        if (answer != null) {
            cir.setReturnValue(answer);
        }
    }

    @Inject(method = "hasBrokenHalo", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$cachedHasBrokenHalo(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        Boolean answer = coo$lookup(entity, BrokenAscensionHalo.class);
        if (answer != null) {
            cir.setReturnValue(answer);
        }
    }

    @Unique
    private static Boolean coo$lookup(LivingEntity entity, Class<?> type) {
        if (!CoOConfig.goetyrevelationCacheHaloLookup) {
            return null;
        }
        if (!(entity instanceof Player)) {
            return Boolean.FALSE;
        }
        return CurioPresenceCache.equippedInstanceOf(entity, type);
    }
}
