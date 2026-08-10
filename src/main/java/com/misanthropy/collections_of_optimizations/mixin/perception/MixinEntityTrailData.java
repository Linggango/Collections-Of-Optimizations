package com.misanthropy.collections_of_optimizations.mixin.perception;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.PerceptionTrailDefaults;
import it.hurts.octostudios.perception.common.modules.trail.config.data.TrailConfigData;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Entity.class, priority = 1500)
public abstract class MixinEntityTrailData {

    @WrapOperation(
            method = "<init>",
            at = @At(
                    value = "NEW",
                    target = "it/hurts/octostudios/perception/common/modules/trail/config/data/TrailConfigData",
                    remap = false
            ),
            require = 0,
            expect = 0,
            remap = false
    )
    private TrailConfigData coo$shareDefaultTrailData(Operation<TrailConfigData> original) {
        return CoOConfig.perceptionShareDefaultTrailData ? PerceptionTrailDefaults.shared() : original.call();
    }
}
