package com.misanthropy.collections_of_optimizations.mixin.crittersandcompanions;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import io.github.bonsaistudi0s.crittersandcompanions.common.handler.PlayerHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerHandler.class, remap = false)
public abstract class MixinCacPlayerHandler {

    @Inject(
            method = "onPlayerTick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$throttleKoiLuckScan(Player player, CallbackInfo ci) {
        int interval = CoOConfig.crittersandcompanionsKoiLuckScanInterval;
        if (interval <= 1 || player == null || player.level().isClientSide()) {
            return;
        }
        if (player.tickCount % interval != 0) {
            ci.cancel();
        }
    }
}
