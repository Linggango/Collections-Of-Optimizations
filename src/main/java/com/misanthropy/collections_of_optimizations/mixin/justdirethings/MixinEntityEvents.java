package com.misanthropy.collections_of_optimizations.mixin.justdirethings;

import com.direwolf20.justdirethings.common.events.EntityEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityEvents.class, remap = false)
public abstract class MixinEntityEvents {

    @WrapOperation(
            method = "levelTick(Lnet/minecraftforge/event/TickEvent$LevelTickEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
                    remap = true
            ),
            require = 0
    )
    private static BlockState coo$readWithoutChunkTicket(Level level, BlockPos pos, Operation<BlockState> original) {
        if (CoOConfig.justdirethingsAvoidChunkTickets
                && level instanceof ServerLevel serverLevel
                && !level.isOutsideBuildHeight(pos)) {
            LevelChunk chunk = serverLevel.getChunkSource().getChunkNow(
                    SectionPos.blockToSectionCoord(pos.getX()),
                    SectionPos.blockToSectionCoord(pos.getZ()));
            if (chunk != null) {
                return chunk.getBlockState(pos);
            }
        }
        return original.call(level, pos);
    }
}
