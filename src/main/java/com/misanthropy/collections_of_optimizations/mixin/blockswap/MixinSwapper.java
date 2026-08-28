package com.misanthropy.collections_of_optimizations.mixin.blockswap;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import corgitaco.blockswap.config.BlockSwapConfig;
import corgitaco.blockswap.swapper.Swapper;
import corgitaco.blockswap.util.TickHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(value = Swapper.class, remap = false)
public abstract class MixinSwapper {

    @Inject(
            method = "runRetroGenerator(Lnet/minecraft/world/level/Level;[Lnet/minecraft/world/level/chunk/LevelChunkSection;Lnet/minecraft/world/level/chunk/LevelChunk;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$fastRetroGenerator(Level world, LevelChunkSection[] sections, LevelChunk chunk, CallbackInfo ci) {
        if (!CoOConfig.blockswapPaletteFilteredRetroGen) {
            return;
        }
        ci.cancel();

        BlockSwapConfig config = BlockSwapConfig.getConfig(false);
        TickHelper tickHelper = (TickHelper) chunk;
        if (!config.retroGen() || tickHelper.markTickDirty()) {
            return;
        }

        Map<Block, Block> blockMap = config.blockBlockMap();
        Map<BlockState, BlockState> stateMap = config.blockStateBlockStateMap();
        if (!blockMap.isEmpty() || !stateMap.isEmpty()) {
            coo$sweep(world, sections, chunk, config, blockMap, stateMap);
        }

        tickHelper.setTickDirty();
    }

    @Unique
    private static void coo$sweep(Level world, LevelChunkSection[] sections, LevelChunk chunk, BlockSwapConfig config,
                                  Map<Block, Block> blockMap, Map<BlockState, BlockState> stateMap) {
        int baseX = SectionPos.sectionToBlockCoord(chunk.getPos().x);
        int baseZ = SectionPos.sectionToBlockCoord(chunk.getPos().z);

        Predicate<BlockState> tracked = config::contains;

        for (int index = 0; index < sections.length; index++) {
            LevelChunkSection section = sections[index];
            if (section == null || !section.maybeHas(tracked)) {
                continue;
            }

            int baseY = SectionPos.sectionToBlockCoord(chunk.getSectionYFromSectionIndex(index));

            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        BlockState state = section.getBlockState(x, y, z);
                        boolean swapBlock = blockMap.containsKey(state.getBlock());
                        BlockState swapState = stateMap.get(state);

                        if (!swapBlock && swapState == null) {
                            continue;
                        }

                        BlockPos pos = new BlockPos(baseX + x, baseY + y, baseZ + z);
                        if (swapBlock) {
                            world.setBlock(pos, Swapper.remapState(state), 2);
                        }
                        if (swapState != null) {
                            world.setBlock(pos, swapState, 2);
                        }
                    }
                }
            }
        }
    }
}
