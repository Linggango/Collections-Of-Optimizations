package com.misanthropy.collections_of_optimizations.mixin.goety;

import com.Polarice3.Goety.client.events.ClientEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CameraShakeScanCache;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = ClientEvents.class, remap = false)
public abstract class MixinGoetyShakeScan {

    @WrapOperation(
            method = "onSetupCamera",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
            ),
            remap = true,
            require = 0
    )
    private static List<Entity> coo$cacheShakeScan(Level level, Class<Entity> type, AABB box,
                                            Operation<List<Entity>> original) {
        if (!CoOConfig.goetyCacheShakeScan) {
            return original.call(level, type, box);
        }
        return CameraShakeScanCache.get(level, type, box, original);
    }
}
