package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.google.common.base.Supplier;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.SlotCopyMemo;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerMenu.class)
public abstract class MixinContainerMenuBroadcast {

    @Unique
    private SlotCopyMemo coo$slotCopy;

    @WrapOperation(
            method = "broadcastChanges",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/base/Suppliers;memoize(Lcom/google/common/base/Supplier;)Lcom/google/common/base/Supplier;"
            ),
            require = 0
    )
    private Supplier<ItemStack> coo$reuseSlotCopy(Supplier<ItemStack> factory,
                                                  Operation<Supplier<ItemStack>> original) {
        if (!CoOConfig.vanillaLeanMenuBroadcast) {
            return original.call(factory);
        }
        SlotCopyMemo memo = this.coo$slotCopy;
        if (memo == null) {
            memo = new SlotCopyMemo();
            this.coo$slotCopy = memo;
        }
        return memo.reset(factory);
    }
}
