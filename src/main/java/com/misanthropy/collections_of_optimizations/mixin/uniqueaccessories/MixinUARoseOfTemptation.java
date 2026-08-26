package com.misanthropy.collections_of_optimizations.mixin.uniqueaccessories;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.genzyuro.uniqueaccessories.system.RoseOfTemptationEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RoseOfTemptationEvents.class, remap = false)
public abstract class MixinUARoseOfTemptation {

    @Inject(method = "onAnyLivingTick", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipUnsetPersistentData(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.uniqueaccessoriesSkipUnsetPersistentData) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity instanceof EntityPersistentDataAccessor access && access.coo$persistentData() == null) {
            ci.cancel();
        }
    }
}
