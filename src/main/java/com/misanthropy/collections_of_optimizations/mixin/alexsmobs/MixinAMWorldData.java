package com.misanthropy.collections_of_optimizations.mixin.alexsmobs;

import com.github.alexthe666.alexsmobs.world.AMWorldData;
import com.misanthropy.collections_of_optimizations.core.AlexsMobsLevelMaps;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AMWorldData.class, remap = false)
public abstract class MixinAMWorldData {

    @Inject(
            method = "get",
            at = @At("HEAD"),
            require = 0
    )
    private static void coo$armLevelMapCleanup(Level world, CallbackInfoReturnable<AMWorldData> cir) {
        AlexsMobsLevelMaps.arm();
    }
}
