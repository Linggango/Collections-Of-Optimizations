package com.misanthropy.collections_of_optimizations.core;

import com.google.common.base.Supplier;
import net.minecraft.world.item.ItemStack;

public final class SlotCopyMemo implements Supplier<ItemStack> {

    private Supplier<ItemStack> factory;
    private ItemStack copy;

    public SlotCopyMemo reset(Supplier<ItemStack> factory) {
        this.factory = factory;
        this.copy = null;
        return this;
    }

    @Override
    public ItemStack get() {
        ItemStack cached = this.copy;
        if (cached == null) {
            cached = this.factory.get();
            this.copy = cached;
        }
        return cached;
    }
}
