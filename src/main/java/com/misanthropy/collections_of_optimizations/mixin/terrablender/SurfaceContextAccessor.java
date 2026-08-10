package com.misanthropy.collections_of_optimizations.mixin.terrablender;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Mixin(SurfaceRules.Context.class)
public interface SurfaceContextAccessor {

    @Accessor("biome")
    Supplier<Holder<Biome>> coo$getBiome();
}
