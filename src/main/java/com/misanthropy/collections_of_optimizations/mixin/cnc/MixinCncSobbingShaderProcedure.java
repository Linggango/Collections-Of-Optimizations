package com.misanthropy.collections_of_optimizations.mixin.cnc;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.imasillylittleguy.cnc.procedures.SobbingShaderProcedure", remap = false)
public abstract class MixinCncSobbingShaderProcedure {

    @Unique
    private static boolean coo$loadedByCnc;

    @Inject(
            method = "onPlayerTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$localPlayerOnly(TickEvent.PlayerTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.cncFixSobbingShaderStomp || event == null || event.player == null) {
            return;
        }
        if (!event.player.level().isClientSide()) {
            ci.cancel();
            return;
        }
        if (event.player != Minecraft.getInstance().player) {
            ci.cancel();
        }
    }

    @WrapOperation(
            method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;loadEffect(Lnet/minecraft/resources/ResourceLocation;)V",
                    remap = true
            ),
            remap = true,
            require = 0
    )
    private static void coo$rememberOurEffect(GameRenderer renderer, ResourceLocation effect, Operation<Void> original) {
        original.call(renderer, effect);
        coo$loadedByCnc = true;
    }

    @WrapWithCondition(
            method = "execute(Lnet/minecraftforge/eventbus/api/Event;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;shutdownEffect()V",
                    remap = true
            ),
            remap = true,
            require = 0
    )
    private static boolean coo$onlyShutDownOurEffect(GameRenderer renderer) {
        if (!CoOConfig.cncFixSobbingShaderStomp) {
            return true;
        }
        if (!coo$loadedByCnc) {
            return false;
        }
        coo$loadedByCnc = false;
        return true;
    }
}
