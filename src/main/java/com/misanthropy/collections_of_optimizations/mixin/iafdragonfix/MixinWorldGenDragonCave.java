package com.misanthropy.collections_of_optimizations.mixin.iafdragonfix;

import com.github.alexthe666.iceandfire.world.gen.WorldGenDragonCave;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.IafDenGenFlag;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldGenDragonCave.class, remap = false)
public abstract class MixinWorldGenDragonCave {

    @Inject(method = "m_142674_", at = @At("HEAD"), cancellable = true, require = 0)
    private void coo$onlyGenerateFromStructure(
            FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
        if (CoOConfig.iafdragonfixStructureDens && !IafDenGenFlag.isActive()) {
            cir.setReturnValue(false);
        }
    }
}
