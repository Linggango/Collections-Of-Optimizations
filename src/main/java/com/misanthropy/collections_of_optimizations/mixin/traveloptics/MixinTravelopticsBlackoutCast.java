package com.misanthropy.collections_of_optimizations.mixin.traveloptics;

import com.gametechbc.traveloptics.effects.Blackout.BlackoutHandler;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlackoutHandler.class, remap = false)
public abstract class MixinTravelopticsBlackoutCast {

    @Inject(method = "onEntityCast", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipBlackoutEffectLookup(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (CoOConfig.travelopticsLeanCastEffectChecks
                && !(event.getEntity() instanceof AbstractSpellCastingMob)) {
            ci.cancel();
        }
    }
}
