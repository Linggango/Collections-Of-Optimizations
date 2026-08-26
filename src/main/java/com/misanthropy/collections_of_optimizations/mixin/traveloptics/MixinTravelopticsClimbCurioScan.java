package com.misanthropy.collections_of_optimizations.mixin.traveloptics;

import com.gametechbc.traveloptics.events.ForgeServerEvents;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ForgeServerEvents.class, remap = false)
public abstract class MixinTravelopticsClimbCurioScan {

    @Inject(method = "applyClimbOnCurio", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipClimbCurioScan(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.travelopticsLeanClimbCurioScan) {
            return;
        }
        MobEffect spiderAspect = MobEffectRegistry.SPIDER_ASPECT.orElse(null);
        if (spiderAspect == null) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (!entity.hasEffect(spiderAspect)) {
            ci.cancel();
        }
    }
}
