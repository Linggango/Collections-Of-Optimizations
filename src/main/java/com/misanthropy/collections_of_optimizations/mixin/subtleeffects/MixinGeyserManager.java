package com.misanthropy.collections_of_optimizations.mixin.subtleeffects;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.SubtleEffectsGeyserTypes;
import einstein.subtle_effects.ticking.GeyserManager;
import einstein.subtle_effects.ticking.tickers.geyser.GeyserType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = GeyserManager.class, remap = false)
public abstract class MixinGeyserManager {

    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 0
    )
    private static void coo$skipImpossibleGeyserScan(Level level, BlockState state, BlockPos pos, CallbackInfo ci) {
        if (!CoOConfig.subtleeffectsGeyserBlockPreFilter) {
            return;
        }

        Block block = state.getBlock();
        Block fluidBlock = state.getFluidState().createLegacyBlock().getBlock();

        for (GeyserType type : SubtleEffectsGeyserTypes.TYPES) {
            List<? extends Block> spawnableBlocks = type.spawnableBlocks.get();
            if (spawnableBlocks.contains(block) || spawnableBlocks.contains(fluidBlock)) {
                return;
            }
        }

        ci.cancel();
    }
}
