package com.misanthropy.collections_of_optimizations.mixin.cnc;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ModEntityFilter;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.imasillylittleguy.cnc.init.EntityAnimationFactory", remap = false)
public abstract class MixinCncEntityAnimationFactory {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipForeignEntities(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.cncSkipForeignEntityAnimations || event == null) {
            return;
        }
        if (!ModEntityFilter.CRITTERS_N_CRAWLERS.matches(event.getEntity())) {
            ci.cancel();
        }
    }
}
