package com.misanthropy.collections_of_optimizations.mixin.bloodmagic;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.FastVisitedList;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import wayoftime.bloodmagic.common.tile.routing.TileMasterRoutingNode;

import java.util.List;

@Mixin(value = TileMasterRoutingNode.class, remap = false)
public abstract class MixinTileMasterRoutingNode {

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lwayoftime/bloodmagic/common/tile/routing/TileMasterRoutingNode;isConnected(Ljava/util/List;Lnet/minecraft/core/BlockPos;)Z"
            ),
            require = 0
    )
    private boolean coo$fastConnectivitySearch(TileMasterRoutingNode self,
                                               List<BlockPos> path,
                                               BlockPos nodePos,
                                               Operation<Boolean> original) {
        if (!CoOConfig.bloodmagicFastRoutingConnectivity) {
            return original.call(self, path, nodePos);
        }
        return original.call(self, new FastVisitedList(), nodePos);
    }
}
