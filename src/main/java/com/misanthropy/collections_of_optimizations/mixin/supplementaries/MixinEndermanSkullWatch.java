package com.misanthropy.collections_of_optimizations.mixin.supplementaries;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mehvahdjukaar.supplementaries.common.block.tiles.EndermanSkullBlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EndermanSkullBlockTile.class, remap = false)
public abstract class MixinEndermanSkullWatch {

    @Inject(
            method = "isBeingWatched",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$skipUnaimedSkullRay(Level level, BlockPos pos, BlockState state,
                                                CallbackInfoReturnable<Boolean> cir) {
        if (!CoOConfig.supplementariesLeanEndermanSkullWatch) {
            return;
        }
        double cx = pos.getX() + 0.5D;
        double cy = pos.getY() + 0.5D;
        double cz = pos.getZ() + 0.5D;
        for (Player player : level.players()) {
            double dx = cx - player.getX();
            double dy = cy - player.getEyeY();
            double dz = cz - player.getZ();
            double distSqr = dx * dx + dy * dy + dz * dz;
            if (distSqr > 4225.0D) {
                continue;
            }
            Vec3 look = player.getViewVector(1.0F);
            double along = dx * look.x + dy * look.y + dz * look.z;
            if (along <= 0.0D) {
                continue;
            }
            if (distSqr - along * along > 1.0D) {
                continue;
            }
            return;
        }
        cir.setReturnValue(false);
    }
}
