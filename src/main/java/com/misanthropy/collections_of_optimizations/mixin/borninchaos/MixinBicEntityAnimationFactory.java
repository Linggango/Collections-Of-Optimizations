package com.misanthropy.collections_of_optimizations.mixin.borninchaos;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ModEntityFilter;
import net.mcreator.borninchaosv.init.EntityAnimationFactory;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityAnimationFactory.class, remap = false)
public abstract class MixinBicEntityAnimationFactory {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipForeignEntities(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.borninchaosSkipForeignEntityAnimations || event == null) {
            return;
        }
        if (!ModEntityFilter.BORN_IN_CHAOS.matches(event.getEntity())) {
            ci.cancel();
        }
    }
}
