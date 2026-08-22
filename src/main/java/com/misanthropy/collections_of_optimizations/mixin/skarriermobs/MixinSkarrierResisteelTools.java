package com.misanthropy.collections_of_optimizations.mixin.skarriermobs;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.SkarrierTags;
import net.mcreator.skarriermobs.procedures.ResisteelToolsEffectProcedure;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ResisteelToolsEffectProcedure.class, remap = false)
public abstract class MixinSkarrierResisteelTools {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipEmptyHands(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.skarriermobsLeanResisteelToolScan || event == null) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity != null && !entity.getMainHandItem().is(SkarrierTags.RESISTEEL_TOOLS)) {
            ci.cancel();
        }
    }
}
