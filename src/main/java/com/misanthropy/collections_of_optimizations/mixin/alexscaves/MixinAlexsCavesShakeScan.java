package com.misanthropy.collections_of_optimizations.mixin.alexscaves;

import com.github.alexmodguy.alexscaves.client.event.ClientEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CameraShakeScanCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Predicate;

@Mixin(value = ClientEvents.class, remap = false)
public abstract class MixinAlexsCavesShakeScan {

    @WrapOperation(
            method = "computeCameraAngles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
            ),
            remap = true,
            require = 0
    )
    private List<Mob> coo$cacheShakeScan(ClientLevel level,
                                         Class<Mob> type,
                                         AABB box,
                                         Predicate<? super Mob> filter,
                                         Operation<List<Mob>> original) {
        if (!CoOConfig.alexscavesCacheShakeScan) {
            return original.call(level, type, box, filter);
        }
        return CameraShakeScanCache.get("alexscaves", level, type, box, filter, original);
    }
}
