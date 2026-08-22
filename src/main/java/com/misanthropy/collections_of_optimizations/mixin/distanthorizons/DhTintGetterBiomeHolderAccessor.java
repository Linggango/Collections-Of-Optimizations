package com.misanthropy.collections_of_optimizations.mixin.distanthorizons;

import com.seibel.distanthorizons.common.wrappers.block.AbstractDhTintGetter_forge;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.ConcurrentHashMap;

@Mixin(value = AbstractDhTintGetter_forge.class, remap = false)
public interface DhTintGetterBiomeHolderAccessor {

    @Accessor("BIOME_BY_RESOURCE_STRING")
    static ConcurrentHashMap<String, Holder<Biome>> coo$biomeByResourceString() {
        throw new AssertionError();
    }
}
