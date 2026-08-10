package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import einstein.subtle_effects.ticking.FireflyManager;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FireflyManager.class, remap = false)
public abstract class MixinFireflyManager {

    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 0
    )
    private static void coo$skipLitFireflyScan(Level level, BlockPos pos, BlockState state, RandomSource random, CallbackInfo ci) {
        if (!CoOConfig.subtleeffectsFireflyDarknessGate) {
            return;
        }

        if (level.getBrightness(LightLayer.BLOCK, pos) > 5) {
            ci.cancel();
            return;
        }

        float time = level.getDayTime() % 24000F;
        if (time > 13000 && time < 23000) {
            return;
        }

        if (level.getBrightness(LightLayer.SKY, pos) != 0) {
            ci.cancel();
        }
    }
}
