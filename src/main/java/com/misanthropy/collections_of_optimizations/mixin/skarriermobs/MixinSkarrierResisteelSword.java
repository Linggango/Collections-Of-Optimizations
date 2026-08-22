package com.misanthropy.collections_of_optimizations.mixin.skarriermobs;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mcreator.skarriermobs.init.SkarrierMobsModItems;
import net.mcreator.skarriermobs.init.SkarrierMobsModMobEffects;
import net.mcreator.skarriermobs.procedures.ResisteelSwordEffectProcedure;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ResisteelSwordEffectProcedure.class, remap = false)
public abstract class MixinSkarrierResisteelSword {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipEffectlessEntities(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.skarriermobsLeanResisteelSwordScan || event == null) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity == null || entity.getMainHandItem().getItem() == SkarrierMobsModItems.RESISTEEL_SWORD.get()) {
            return;
        }
        if (!entity.hasEffect(SkarrierMobsModMobEffects.RESISTEEL_DAMAGE_BOOST.get())) {
            ci.cancel();
        }
    }
}
