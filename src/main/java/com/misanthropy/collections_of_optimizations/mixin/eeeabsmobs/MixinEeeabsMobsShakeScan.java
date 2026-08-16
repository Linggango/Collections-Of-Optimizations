package com.misanthropy.collections_of_optimizations.mixin.eeeabsmobs;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CameraShakeScanCache;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Pseudo
@Mixin(targets = "com.eeeab.eeeabsmobs.sever.handler.ClientEventHandler", remap = false)
public abstract class MixinEeeabsMobsShakeScan {

    @WrapOperation(
            method = "onSetupCamera",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
            ),
            remap = true,
            require = 0
    )
    private List<Entity> coo$cacheShakeScan(Level level, Class<Entity> type, AABB box,
                                            Operation<List<Entity>> original) {
        if (!CoOConfig.eeeabsmobsCacheShakeScan) {
            return original.call(level, type, box);
        }
        return CameraShakeScanCache.get(level, type, box, original);
    }
}
