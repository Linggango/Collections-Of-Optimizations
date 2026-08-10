package com.misanthropy.collections_of_optimizations.mixin.goety;

import com.Polarice3.Goety.common.capabilities.lichdom.ILichdom;
import com.Polarice3.Goety.common.capabilities.lichdom.LichProvider;
import com.Polarice3.Goety.utils.LichdomHelper;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LichdomHelper.class, remap = false)
public abstract class MixinLichdomHelper {

    @WrapMethod(method = "getCapability")
    private static ILichdom coo$resolveWithoutFallback(Player player, Operation<ILichdom> original) {
        if (CoOConfig.goetySkipCapabilityFallback && player != null) {
            ILichdom resolved = player.getCapability(LichProvider.CAPABILITY).orElse(null);
            if (resolved != null) {
                return resolved;
            }
        }
        return original.call(player);
    }
}
