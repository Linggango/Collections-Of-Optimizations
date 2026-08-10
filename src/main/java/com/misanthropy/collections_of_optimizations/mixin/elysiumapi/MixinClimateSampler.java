package com.misanthropy.collections_of_optimizations.mixin.elysiumapi;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ClimateSampleMemo;
import net.minecraft.world.level.biome.Climate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Climate.Sampler.class)
public abstract class MixinClimateSampler {

    @Inject(
            method = "sample",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$serveMemoisedSample(int x, int y, int z, CallbackInfoReturnable<Climate.TargetPoint> cir) {
        if (!CoOConfig.elysiumapiMemoClimateSample && !CoOConfig.alexscavesMemoClimateSample) {
            return;
        }
        Climate.TargetPoint hit = ClimateSampleMemo.get().lookup(this, x, y, z);
        if (hit != null) {
            cir.setReturnValue(hit);
        }
    }

    @Inject(
            method = "sample",
            at = @At("RETURN"),
            require = 0
    )
    private void coo$storeSample(int x, int y, int z, CallbackInfoReturnable<Climate.TargetPoint> cir) {
        if (!CoOConfig.elysiumapiMemoClimateSample && !CoOConfig.alexscavesMemoClimateSample) {
            return;
        }
        Climate.TargetPoint value = cir.getReturnValue();
        if (value != null) {
            ClimateSampleMemo.get().store(this, x, y, z, value);
        }
    }
}
