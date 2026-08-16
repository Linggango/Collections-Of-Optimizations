package com.misanthropy.collections_of_optimizations.mixin.create;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BigOutlineProbeCache;
import com.simibubi.create.foundation.block.BigOutlines;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BigOutlines.class, remap = false)
public abstract class MixinBigOutlines {

    @Inject(
            method = "pick()V",
            at = @At("HEAD"),
            require = 0
    )
    private static void coo$resetProbes(CallbackInfo ci) {
        if (CoOConfig.createDedupeBigOutlineProbes) {
            BigOutlineProbeCache.beginPick();
        }
    }

    @WrapOperation(
            method = "lambda$pick$0(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;DLnet/minecraft/core/BlockPos;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
                    remap = true
            ),
            require = 0
    )
    private static BlockState coo$skipRepeatedProbe(ClientLevel level, BlockPos pos, Operation<BlockState> original) {
        if (CoOConfig.createDedupeBigOutlineProbes && !BigOutlineProbeCache.firstProbe(pos.asLong())) {
            return Blocks.AIR.defaultBlockState();
        }
        return original.call(level, pos);
    }
}
