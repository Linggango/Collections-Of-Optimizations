package com.misanthropy.collections_of_optimizations.mixin.goety;

import com.Polarice3.Goety.common.capabilities.misc.IMisc;
import com.Polarice3.Goety.common.capabilities.misc.MiscProvider;
import com.Polarice3.Goety.utils.MiscCapHelper;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MiscCapHelper.class, remap = false)
public abstract class MixinMiscCapHelper {

    @WrapMethod(method = "getCapability")
    private static IMisc coo$resolveWithoutFallback(LivingEntity livingEntity, Operation<IMisc> original) {
        if (CoOConfig.goetySkipCapabilityFallback && livingEntity != null) {
            IMisc resolved = livingEntity.getCapability(MiscProvider.CAPABILITY).orElse(null);
            if (resolved != null) {
                return resolved;
            }
        }
        return original.call(livingEntity);
    }
}
