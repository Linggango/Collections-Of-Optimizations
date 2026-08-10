package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(Block.class)
public abstract class MixinBlockPredictableDrops {

    @WrapOperation(
            method = "popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Ljava/util/function/Supplier;Lnet/minecraft/world/item/ItemStack;)V"
            ),
            require = 0
    )
    private static void coo$centreDroppedItem(Level scatteredLevel, Supplier<ItemEntity> scattered, ItemStack scatteredStack,
                                              Operation<Void> original,
                                              Level level, BlockPos pos, ItemStack stack) {
        if (!CoOConfig.vanillaPredictableItemDrops) {
            original.call(scatteredLevel, scattered, scatteredStack);
            return;
        }

        Supplier<ItemEntity> centred = () -> {
            ItemEntity item = scattered.get();
            double halfHeight = (double) EntityType.ITEM.getHeight() / 2.0D;
            item.moveTo(pos.getX() + 0.5D, pos.getY() + 0.5D - halfHeight, pos.getZ() + 0.5D);
            item.setDeltaMovement(0.0D, 0.2D, 0.0D);
            return item;
        };
        original.call(scatteredLevel, centred, scatteredStack);
    }
}
