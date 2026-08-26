package com.misanthropy.collections_of_optimizations.mixin.enigmaticaddons;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityPersistentDataAccessor {

    @Accessor(value = "persistentData", remap = false)
    CompoundTag coo$persistentData();
}
