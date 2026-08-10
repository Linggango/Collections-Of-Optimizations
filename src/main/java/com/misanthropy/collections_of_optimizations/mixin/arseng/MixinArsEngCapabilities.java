package com.misanthropy.collections_of_optimizations.mixin.arseng;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ArsEngGenericInvFilter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "gripe._90.arseng.definition.ArsEngCapabilities", remap = false)
public abstract class MixinArsEngCapabilities {

    @WrapWithCondition(
            method = "attach",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/event/AttachCapabilitiesEvent;addCapability(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraftforge/common/capabilities/ICapabilityProvider;)V",
                    ordinal = 1
            ),
            require = 0
    )
    private static boolean coo$gateGenericInvWrapper(AttachCapabilitiesEvent<?> event,
                                                     ResourceLocation id,
                                                     ICapabilityProvider provider) {
        if (!CoOConfig.arsengGateGenericInvWrapper) {
            return true;
        }
        return !(event.getObject() instanceof BlockEntity be)
                || ArsEngGenericInvFilter.mayProvideGenericInv(be.getClass());
    }
}
