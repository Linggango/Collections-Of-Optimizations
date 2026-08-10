package com.misanthropy.collections_of_optimizations.mixin.goety;

import com.Polarice3.Goety.client.events.ClientEvents;
import com.Polarice3.Goety.common.entities.hostile.Wight;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

@Mixin(value = ClientEvents.class, remap = false)
public abstract class MixinClientEventsFogWight {

    @Unique
    private static Wight coo$fogWight;

    @Unique
    private static long coo$fogWightStamp = Long.MIN_VALUE;

    @WrapOperation(
            method = "FogEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/common/entities/hostile/Wight;findWight(Lnet/minecraft/world/entity/Entity;Ljava/util/function/Predicate;)Lcom/Polarice3/Goety/common/entities/hostile/Wight;"
            ),
            require = 0
    )
    private static Wight coo$cacheFogWightScan(Entity entity, Predicate<Wight> filter, Operation<Wight> original) {
        if (!CoOConfig.goetyCacheFogWightScan) {
            return original.call(entity, filter);
        }
        long now = entity.level().getGameTime();
        if (coo$fogWightStamp != now) {
            coo$fogWightStamp = now;
            coo$fogWight = original.call(entity, filter);
        }
        return coo$fogWight;
    }
}
