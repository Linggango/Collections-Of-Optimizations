package com.misanthropy.collections_of_optimizations.mixin.dungeoncrawl;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xiroc.dungeoncrawl.dungeon.piece.DungeonPiece;

@Mixin(value = DungeonPiece.class, remap = false)
public abstract class MixinDungeonPiece {

    @WrapOperation(
            method = "placeBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/LevelAccessor;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;",
                    remap = true
            ),
            require = 0
    )
    private BlockEntity coo$skipBlockEntityProbe(
            LevelAccessor world,
            BlockPos position,
            Operation<BlockEntity> original,
            @Local(argsOnly = true) BlockState state) {
        if (CoOConfig.dungeoncrawlSkipBlockEntityProbe && !state.hasBlockEntity()) {
            return null;
        }
        return original.call(world, position);
    }
}
