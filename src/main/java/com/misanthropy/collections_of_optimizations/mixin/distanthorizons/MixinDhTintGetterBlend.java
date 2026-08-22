package com.misanthropy.collections_of_optimizations.mixin.distanthorizons;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.seibel.distanthorizons.common.wrappers.block.AbstractDhTintGetter_forge;
import com.seibel.distanthorizons.common.wrappers.block.BiomeWrapper_forge;
import com.seibel.distanthorizons.common.wrappers.block.BlockStateWrapper_forge;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = AbstractDhTintGetter_forge.class, remap = false)
public abstract class MixinDhTintGetterBlend {

    @Shadow
    protected BlockStateWrapper_forge blockStateWrapper;

    @Unique
    private Object coo$memoBlockState;

    @Unique
    private Object coo$memoResolver;

    @Unique
    private Object coo$memoBiome;

    @Unique
    private int coo$memoColor;

    @WrapOperation(
            method = "tryGetBlockTint(Lcom/seibel/distanthorizons/core/pos/blockPos/DhBlockPosMutable;Lnet/minecraft/world/level/ColorResolver;)I",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/seibel/distanthorizons/common/wrappers/block/AbstractDhTintGetter_forge;tryGetClientBiomeColor(Lnet/minecraft/world/level/ColorResolver;Lcom/seibel/distanthorizons/common/wrappers/block/BiomeWrapper_forge;)I"
            ),
            require = 0
    )
    private int coo$memoBlendedBiomeColor(AbstractDhTintGetter_forge self, ColorResolver colorResolver, BiomeWrapper_forge biomeWrapper,
                                          Operation<Integer> original) {
        if (!CoOConfig.distanthorizonsMemoBiomeBlendColors) {
            return original.call(self, colorResolver, biomeWrapper);
        }
        if (biomeWrapper == this.coo$memoBiome
                && colorResolver == this.coo$memoResolver
                && this.blockStateWrapper == this.coo$memoBlockState) {
            return this.coo$memoColor;
        }
        int color = original.call(self, colorResolver, biomeWrapper);
        this.coo$memoBiome = biomeWrapper;
        this.coo$memoResolver = colorResolver;
        this.coo$memoBlockState = this.blockStateWrapper;
        this.coo$memoColor = color;
        return color;
    }
}
