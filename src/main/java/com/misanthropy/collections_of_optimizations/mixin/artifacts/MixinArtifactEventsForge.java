package com.misanthropy.collections_of_optimizations.mixin.artifacts;

import artifacts.forge.event.ArtifactEventsForge;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ArtifactEventsForge.class, remap = false)
public abstract class MixinArtifactEventsForge {

    @Inject(
            method = "onLivingUpdate(Lnet/minecraftforge/event/entity/living/LivingEvent$LivingTickEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipClientTickOnNonPlayers(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        LivingEntity entity = event.getEntity();
        if (CoOConfig.artifactsSkipClientTickOnNonPlayers
                && entity.level().isClientSide()
                && !(entity instanceof Player)) {
            ci.cancel();
        }
    }

    @Inject(
            method = "onKittySlippersLivingUpdate(Lnet/minecraftforge/event/entity/living/LivingEvent$LivingTickEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipKittySlippersScan(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (CoOConfig.artifactsFastPathKittySlippers && event.getEntity().getLastHurtByMob() == null) {
            ci.cancel();
        }
    }

    @WrapOperation(
            method = "onUmbrellaLivingUpdate(Lnet/minecraftforge/event/entity/living/LivingEvent$LivingTickEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lartifacts/item/wearable/necklace/CharmOfSinkingItem;shouldSink(Lnet/minecraft/world/entity/LivingEntity;)Z"
            ),
            require = 0
    )
    private static boolean coo$skipUnusedSinkScan(LivingEntity entity, Operation<Boolean> original) {
        if (CoOConfig.artifactsFastPathUmbrella
                && (entity.onGround() || entity.getDeltaMovement().y >= 0.0D)) {
            return false;
        }
        return original.call(entity);
    }
}
