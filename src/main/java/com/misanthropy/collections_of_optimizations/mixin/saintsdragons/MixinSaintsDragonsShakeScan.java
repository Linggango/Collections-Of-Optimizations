package com.misanthropy.collections_of_optimizations.mixin.saintsdragons;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Pseudo
@Mixin(targets = "com.leon.saintsdragons.forge.client.event.ClientEventHandler", remap = false)
public abstract class MixinSaintsDragonsShakeScan {

    @Unique
    private static List<Mob> coo$shakers = Collections.emptyList();

    @Unique
    private static long coo$shakerStamp = Long.MIN_VALUE;

    @WrapOperation(
            method = "onComputeCamera",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
            ),
            remap = true,
            require = 0
    )
    private static List<Mob> coo$cacheShakeScan(ClientLevel level,
                                                Class<Mob> type,
                                                AABB box,
                                                Predicate<? super Mob> filter,
                                                Operation<List<Mob>> original) {
        if (!CoOConfig.saintsdragonsCacheShakeScan) {
            return original.call(level, type, box, filter);
        }
        long now = level.getGameTime();
        if (coo$shakerStamp != now) {
            coo$shakerStamp = now;
            coo$shakers = original.call(level, type, box, filter);
        }
        return coo$shakers;
    }
}
