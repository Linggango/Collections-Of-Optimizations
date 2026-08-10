package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.client.ClientEventHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(value = ClientEventHandler.class, remap = false)
public abstract class MixinMowzieClientEventHandler {

    @Unique
    private List<EntityCameraShake> coo$cameraShakes;

    @Unique
    private int coo$cameraShakeTick = Integer.MIN_VALUE;

    @Unique
    private Level coo$cameraShakeLevel;

    @WrapOperation(
            method = "onSetupCamera",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;",
                    remap = true
            ),
            require = 0
    )
    private List<EntityCameraShake> coo$reuseCameraShakeScan(
            Level level, Class<EntityCameraShake> type, AABB box, Operation<List<EntityCameraShake>> original) {
        if (!CoOConfig.mowziesmobsCacheCameraShakeScan) {
            return original.call(level, type, box);
        }
        int tick = coo$clientTick(level);
        if (this.coo$cameraShakes != null && this.coo$cameraShakeTick == tick && this.coo$cameraShakeLevel == level) {
            return this.coo$cameraShakes;
        }
        List<EntityCameraShake> resolved = original.call(level, type, box);
        this.coo$cameraShakes = resolved;
        this.coo$cameraShakeTick = tick;
        this.coo$cameraShakeLevel = level;
        return resolved;
    }

    @Unique
    private static int coo$clientTick(Level level) {
        Entity camera = net.minecraft.client.Minecraft.getInstance().getCameraEntity();
        return camera != null ? camera.tickCount : (int) level.getGameTime();
    }
}
