package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinMovementCheckSlack {

    @ModifyExpressionValue(
            method = "handleMovePlayer",
            at = @At(value = "CONSTANT", args = "floatValue=100.0"),
            require = 0
    )
    private float coo$playerSpeedLimit(float original) {
        return (float) (original * CoOConfig.vanillaMovementCheckSlack);
    }

    @ModifyExpressionValue(
            method = "handleMovePlayer",
            at = @At(value = "CONSTANT", args = "floatValue=300.0"),
            require = 0
    )
    private float coo$elytraSpeedLimit(float original) {
        return (float) (original * CoOConfig.vanillaMovementCheckSlack);
    }

    @ModifyExpressionValue(
            method = "handleMovePlayer",
            at = @At(value = "CONSTANT", args = "doubleValue=0.0625"),
            require = 0
    )
    private double coo$playerDesyncTolerance(double original) {
        return original * CoOConfig.vanillaMovementCheckSlack;
    }

    @ModifyExpressionValue(
            method = "handleMoveVehicle",
            at = @At(value = "CONSTANT", args = "doubleValue=100.0"),
            require = 0
    )
    private double coo$vehicleSpeedLimit(double original) {
        return original * CoOConfig.vanillaMovementCheckSlack;
    }

    @ModifyExpressionValue(
            method = "handleMoveVehicle",
            at = @At(value = "CONSTANT", args = "doubleValue=0.0625", ordinal = 1),
            require = 0
    )
    private double coo$vehicleDesyncTolerance(double original) {
        return original * CoOConfig.vanillaMovementCheckSlack;
    }

    @ModifyExpressionValue(
            method = {"handleMovePlayer", "handleMoveVehicle"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;isFlightAllowed()Z"
            ),
            require = 0
    )
    private boolean coo$skipFloatingKick(boolean original) {
        return CoOConfig.vanillaDisableFlyingKick || original;
    }
}
