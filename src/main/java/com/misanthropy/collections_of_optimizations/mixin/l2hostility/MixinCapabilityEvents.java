package com.misanthropy.collections_of_optimizations.mixin.l2hostility;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.TraitCapPresenceHolder;
import dev.xkmc.l2hostility.content.capability.mob.MobTraitCap;
import dev.xkmc.l2hostility.events.CapabilityEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CapabilityEvents.class, remap = false)
public abstract class MixinCapabilityEvents {

    @WrapOperation(
            method = "livingTickEvent",
            at = @org.spongepowered.asm.mixin.injection.At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getCapability(Lnet/minecraftforge/common/capabilities/Capability;)Lnet/minecraftforge/common/util/LazyOptional;"
            ),
            require = 0
    )
    private static LazyOptional<MobTraitCap> coo$skipKnownAbsentTraitCap(
            LivingEntity entity,
            Capability<MobTraitCap> capability,
            Operation<LazyOptional<MobTraitCap>> original) {
        if (!CoOConfig.l2hostilitySkipTraitlessCapLookup || !(entity instanceof TraitCapPresenceHolder holder)) {
            return original.call(entity, capability);
        }
        if (holder.coo$hasNoTraitCap()) {
            return LazyOptional.empty();
        }
        LazyOptional<MobTraitCap> resolved = original.call(entity, capability);

        if (!resolved.isPresent()) {
            holder.coo$markNoTraitCap();
        }
        return resolved;
    }
}
