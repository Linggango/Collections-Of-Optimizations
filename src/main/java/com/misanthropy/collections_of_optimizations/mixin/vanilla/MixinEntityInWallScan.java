package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.sugar.Local;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntityInWallScan {

    @Inject(
            method = "isInWall",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/BlockPos;betweenClosedStream(Lnet/minecraft/world/phys/AABB;)Ljava/util/stream/Stream;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true,
            require = 0
    )
    private void coo$scanWithoutStream(CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) AABB aabb) {
        if (!CoOConfig.vanillaLeanSuffocationScan) {
            return;
        }
        Level level = ((Entity) (Object) this).level();
        int minX = Mth.floor(aabb.minX);
        int minY = Mth.floor(aabb.minY);
        int minZ = Mth.floor(aabb.minZ);
        int maxX = Mth.floor(aabb.maxX);
        int maxY = Mth.floor(aabb.maxY);
        int maxZ = Mth.floor(aabb.maxZ);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        VoxelShape box = null;

        for (int z = minZ; z <= maxZ; ++z) {
            for (int y = minY; y <= maxY; ++y) {
                for (int x = minX; x <= maxX; ++x) {
                    pos.set(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if (state.isAir() || !state.isSuffocating(level, pos)) {
                        continue;
                    }
                    if (box == null) {
                        box = Shapes.create(aabb);
                    }
                    if (Shapes.joinIsNotEmpty(state.getCollisionShape(level, pos).move(x, y, z), box, BooleanOp.AND)) {
                        cir.setReturnValue(true);
                        return;
                    }
                }
            }
        }
        cir.setReturnValue(false);
    }
}
