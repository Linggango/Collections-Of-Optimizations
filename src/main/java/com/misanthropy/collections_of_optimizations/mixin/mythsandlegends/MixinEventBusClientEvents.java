package com.misanthropy.collections_of_optimizations.mixin.mythsandlegends;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.ovinter.mythsandlegends.event.EventBusClientEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Mixin(value = EventBusClientEvents.class, remap = false)
public abstract class MixinEventBusClientEvents {

    @Unique
    private static List<Entity> coo$fogBosses = Collections.emptyList();

    @Unique
    private static long coo$fogBossStamp = Long.MIN_VALUE;

    @Unique
    private static List<?> coo$shakers = Collections.emptyList();

    @Unique
    private static long coo$shakerStamp = Long.MIN_VALUE;

    @SuppressWarnings("unchecked")
    @WrapOperation(
            method = {"onRenderFog", "onRenderFogColor"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
            ),
            remap = true,
            require = 0
    )
    private static List<Entity> coo$cacheFogBossScan(ClientLevel level,
                                                     Entity except,
                                                     AABB box,
                                                     Predicate<? super Entity> filter,
                                                     Operation<List<Entity>> original) {
        if (!CoOConfig.mythsandlegendsCacheFogBossScan) {
            return original.call(level, except, box, filter);
        }
        long now = level.getGameTime();
        if (coo$fogBossStamp != now) {
            coo$fogBossStamp = now;
            coo$fogBosses = (List<Entity>) original.call(level, except, box, filter);
        }
        return coo$fogBosses;
    }

    @SuppressWarnings("unchecked")
    @WrapOperation(
            method = "onCameraSetup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
            ),
            remap = true,
            require = 0
    )
    private static List<?> coo$cacheShakeScan(Level level,
                                              Class<?> type,
                                              AABB box,
                                              Operation<List<?>> original) {
        if (!CoOConfig.mythsandlegendsCacheShakeScan) {
            return original.call(level, type, box);
        }
        long now = level.getGameTime();
        if (coo$shakerStamp != now) {
            coo$shakerStamp = now;
            coo$shakers = original.call(level, type, box);
        }
        return coo$shakers;
    }
}
