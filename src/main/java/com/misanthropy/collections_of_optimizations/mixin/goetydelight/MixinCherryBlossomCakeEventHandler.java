package com.misanthropy.collections_of_optimizations.mixin.goetydelight;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.v_black_cat.goetydelight.item.food.CherryBlossomCakeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CherryBlossomCakeItem.CherryBlossomCakeEventHandler.class, remap = false)
public abstract class MixinCherryBlossomCakeEventHandler {

    @Inject(
            method = "onServerTick(Lnet/minecraftforge/event/TickEvent$ServerTickEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$throttle(TickEvent.ServerTickEvent event, CallbackInfo ci) {
        int interval = CoOConfig.goetydelightCakeScanInterval;
        if (interval <= 1 || event.phase != TickEvent.Phase.START) {
            return;
        }
        MinecraftServer server = event.getServer();
        if (server != null && server.getTickCount() % interval != 0) {
            ci.cancel();
        }
    }
}
