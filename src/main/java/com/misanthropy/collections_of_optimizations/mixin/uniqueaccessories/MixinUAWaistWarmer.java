package com.misanthropy.collections_of_optimizations.mixin.uniqueaccessories;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import net.genzyuro.uniqueaccessories.registry.UAItems;
import net.genzyuro.uniqueaccessories.system.WaistWarmerEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WaistWarmerEvents.class, remap = false)
public abstract class MixinUAWaistWarmer {

    @Inject(method = "onLivingTick", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipWaistWarmerScan(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.uniqueaccessoriesLeanWaistWarmerScan) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) {
            return;
        }
        Item waistWarmer = UAItems.WAIST_WARMER.orElse(null);
        if (waistWarmer != null && !CurioPresenceCache.mayHaveEquipped(entity, waistWarmer)) {
            ci.cancel();
        }
    }
}
