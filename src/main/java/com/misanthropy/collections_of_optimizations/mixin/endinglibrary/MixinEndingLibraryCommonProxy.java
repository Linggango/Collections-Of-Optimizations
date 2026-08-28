package com.misanthropy.collections_of_optimizations.mixin.endinglibrary;

import com.mega.endinglib.common.capability.EndingLibraryPlayerCapability;
import com.mega.endinglib.proxy.CommonProxy;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CommonProxy.class, remap = false)
public abstract class MixinEndingLibraryCommonProxy {

    @Inject(
            method = "getCameraCapOptional(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraftforge/common/util/LazyOptional;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$leanCameraCapLookup(Player player, CallbackInfoReturnable<LazyOptional<EndingLibraryPlayerCapability>> cir) {
        if (!CoOConfig.endinglibraryLeanCameraCapLookup) {
            return;
        }

        LazyOptional<Capability<EndingLibraryPlayerCapability>> holder = CommonProxy.PLAYER_CAP;
        if (holder == null) {
            return;
        }

        Capability<EndingLibraryPlayerCapability> capability = holder.orElse(null);
        if (capability == null) {
            return;
        }

        cir.setReturnValue(player.getCapability(capability));
    }
}
