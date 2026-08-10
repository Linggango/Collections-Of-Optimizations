package com.misanthropy.collections_of_optimizations.mixin.goety;

import com.Polarice3.Goety.common.capabilities.soulenergy.ISoulEnergy;
import com.Polarice3.Goety.common.capabilities.soulenergy.SEProvider;
import com.Polarice3.Goety.utils.SEHelper;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SEHelper.class, remap = false)
public abstract class MixinSEHelper {

    @WrapMethod(method = "getCapability")
    private static ISoulEnergy coo$resolveWithoutFallback(Player player, Operation<ISoulEnergy> original) {
        if (CoOConfig.goetySkipCapabilityFallback && player != null) {
            ISoulEnergy resolved = player.getCapability(SEProvider.CAPABILITY).orElse(null);
            if (resolved != null) {
                return resolved;
            }
        }
        return original.call(player);
    }

    @WrapMethod(method = "isAlly(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/LivingEntity;)Z")
    private static boolean coo$skipEmptyAllyLookup(Player owner, LivingEntity livingEntity,
                                                   Operation<Boolean> original) {
        if (CoOConfig.goetyFastEmptyAllyCheck && owner != null) {
            ISoulEnergy soulEnergy = owner.getCapability(SEProvider.CAPABILITY).orElse(null);
            if (soulEnergy != null && soulEnergy.allyList().isEmpty() && soulEnergy.allyTypeList().isEmpty()) {
                return false;
            }
        }
        return original.call(owner, livingEntity);
    }
}
