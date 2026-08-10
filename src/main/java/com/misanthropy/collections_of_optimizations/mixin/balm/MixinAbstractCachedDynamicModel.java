package com.misanthropy.collections_of_optimizations.mixin.balm;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.BlockStateKeyCache;
import net.blay09.mods.balm.common.client.rendering.AbstractCachedDynamicModel;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AbstractCachedDynamicModel.class, remap = false)
public abstract class MixinAbstractCachedDynamicModel {

    @WrapOperation(
            method = "*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;toString()Ljava/lang/String;"
            ),
            require = 0
    )
    private String coo$memoiseDynamicModelKey(BlockState state, Operation<String> original) {
        if (!CoOConfig.balmMemoDynamicModelKeys) {
            return original.call(state);
        }
        String cached = BlockStateKeyCache.lookup(state);
        if (cached != null) {
            return cached;
        }
        String computed = original.call(state);
        BlockStateKeyCache.store(state, computed);
        return computed;
    }
}
