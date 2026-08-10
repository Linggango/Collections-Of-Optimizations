package com.misanthropy.collections_of_optimizations.mixin.macabre;

import com.curseforge.macabre.init.EntityAnimationFactory;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ModEntityFilter;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityAnimationFactory.class, remap = false)
public abstract class MixinMacabreEntityAnimationFactory {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipForeignEntities(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.macabreSkipForeignEntityAnimations || event == null) {
            return;
        }
        if (!ModEntityFilter.MACABRE.matches(event.getEntity())) {
            ci.cancel();
        }
    }
}
