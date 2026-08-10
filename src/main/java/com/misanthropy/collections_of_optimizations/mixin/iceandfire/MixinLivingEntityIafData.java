package com.misanthropy.collections_of_optimizations.mixin.iceandfire;

import com.misanthropy.collections_of_optimizations.core.IafEntityDataHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntityIafData implements IafEntityDataHolder {

    @Unique
    private LazyOptional<?> coo$iafEntityData;

    @Override
    public LazyOptional<?> coo$getIafEntityData() {
        return this.coo$iafEntityData;
    }

    @Override
    public void coo$setIafEntityData(LazyOptional<?> data) {
        this.coo$iafEntityData = data;
    }
}
