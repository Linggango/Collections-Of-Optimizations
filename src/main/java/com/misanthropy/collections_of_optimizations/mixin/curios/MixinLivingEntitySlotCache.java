package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.core.EntitySlotCacheHolder;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntitySlotCache implements EntitySlotCacheHolder {

    @Unique
    private int coo$slotCacheGen;

    @Unique
    private boolean coo$slotCacheHasSlots;

    @Override
    public int coo$slotCacheGeneration() {
        return this.coo$slotCacheGen;
    }

    @Override
    public boolean coo$slotCacheValue() {
        return this.coo$slotCacheHasSlots;
    }

    @Override
    public void coo$storeSlotCache(int generation, boolean value) {
        this.coo$slotCacheHasSlots = value;
        this.coo$slotCacheGen = generation;
    }
}
