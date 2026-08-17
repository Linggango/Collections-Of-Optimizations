package com.misanthropy.collections_of_optimizations.mixin.supplementaries;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData$BlockEntityInfo", priority = 1500)
public abstract class MixinBlockEntityInfoCapSync {

    @WrapWithCondition(
            method = "create",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/protocol/game/ClientboundLevelChunkPacketData$BlockEntityInfo;(Lnet/minecraft/world/level/block/entity/BlockEntity;Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;)V",
                    remap = false
            ),
            remap = true,
            require = 0
    )
    private static boolean coo$skipNonSignCapSync(BlockEntity te, CallbackInfoReturnable<?> cir) {
        return !CoOConfig.supplementariesSkipNonSignCapSync || te instanceof SignBlockEntity;
    }
}
