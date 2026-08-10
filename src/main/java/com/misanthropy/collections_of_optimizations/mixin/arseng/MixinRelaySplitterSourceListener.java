package com.misanthropy.collections_of_optimizations.mixin.arseng;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "com.hollingsworth.arsnouveau.common.block.tile.RelaySplitterTile",
        remap = false,
        priority = 1500)
public abstract class MixinRelaySplitterSourceListener {

    @WrapWithCondition(
            method = "arseng$processStaleBlockPositions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/common/util/LazyOptional;addListener(Lnet/minecraftforge/common/util/NonNullConsumer;)V"
            ),
            require = 0
    )
    private boolean coo$skipDeadSourceListener(LazyOptional<?> cap, NonNullConsumer<?> listener) {
        return !CoOConfig.arsengSkipDeadRelayListeners || !cap.isPresent();
    }
}
