package com.misanthropy.collections_of_optimizations.mixin.skarriermobs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface SkarrierEntityDataAccessor {

    @Accessor(value = "persistentData", remap = false)
    CompoundTag coo$persistentData();
}
