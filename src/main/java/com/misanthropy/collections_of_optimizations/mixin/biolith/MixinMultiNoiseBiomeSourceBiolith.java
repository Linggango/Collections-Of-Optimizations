package com.misanthropy.collections_of_optimizations.mixin.biolith;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.terraformersmc.biolith.impl.biome.InterfaceBiomeSource;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MultiNoiseBiomeSource.class, priority = 500)
public abstract class MixinMultiNoiseBiomeSourceBiolith {

    @Inject(
            method = "parameters",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$reuseCachedBiomeEntries(CallbackInfoReturnable<Climate.ParameterList<Holder<Biome>>> cir) {
        if (!CoOConfig.biolithReuseBiomeEntries) {
            return;
        }

        Climate.ParameterList<Holder<Biome>> cached = ((InterfaceBiomeSource) this).biolith$getBiomeEntries();
        if (cached != null) {
            cir.setReturnValue(cached);
        }
    }
}
