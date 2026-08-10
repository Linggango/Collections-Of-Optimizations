package com.misanthropy.collections_of_optimizations.mixin.tonsofenchants;

import com.deltajay.tonsofenchants.enchantments.Enchantments.Frostbite;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Frostbite.class, remap = false)
public abstract class MixinFrostbite {

    @Inject(
            method = "doWhenFrostbit",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 0
    )
    private static void coo$skipClientFrostbiteScan(TickEvent.PlayerTickEvent event, CallbackInfo ci) {
        if (CoOConfig.tonsofenchantsFrostbiteSkipClient && event.player.level().isClientSide) {
            ci.cancel();
        }
    }
}
