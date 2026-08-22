package com.misanthropy.collections_of_optimizations.mixin.distanthorizons;

import com.seibel.distanthorizons.core.dataObjects.BlockBiomeWrapperPair;
import com.seibel.distanthorizons.core.wrapperInterfaces.block.IBlockStateWrapper;
import com.seibel.distanthorizons.core.wrapperInterfaces.world.IBiomeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.ConcurrentHashMap;

@Mixin(value = BlockBiomeWrapperPair.class, remap = false)
public interface DhBlockBiomePairAccessor {

    @Accessor("CACHED_PAIR_BY_BIOME_BY_BLOCK")
    static ConcurrentHashMap<IBlockStateWrapper, ConcurrentHashMap<IBiomeWrapper, BlockBiomeWrapperPair>> coo$pairCache() {
        throw new AssertionError();
    }
}
