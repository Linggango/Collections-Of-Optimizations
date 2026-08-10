package com.misanthropy.collections_of_optimizations.mixin.caelus;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.caelus.mixin.util.MixinHooks;

@Mixin(value = MixinHooks.class, remap = false)
public abstract class MixinCaelusMixinHooks {

    @Inject(
            method = "canFly(Lnet/minecraft/world/entity/LivingEntity;ZZ)Z",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipGroundedNonPlayers(LivingEntity livingEntity, boolean oldFlag, boolean newFlag,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.caelusSkipGroundedNonPlayers
                && !oldFlag
                && !newFlag
                && !(livingEntity instanceof Player)) {
            cir.setReturnValue(Boolean.FALSE);
        }
    }
}
