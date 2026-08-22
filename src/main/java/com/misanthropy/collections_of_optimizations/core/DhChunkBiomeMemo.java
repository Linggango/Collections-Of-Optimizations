package com.misanthropy.collections_of_optimizations.core;

import com.seibel.distanthorizons.core.wrapperInterfaces.chunk.IChunkWrapper;
import com.seibel.distanthorizons.core.wrapperInterfaces.world.IBiomeWrapper;

public final class DhChunkBiomeMemo {

    private static final ThreadLocal<DhChunkBiomeMemo> LOCAL = ThreadLocal.withInitial(DhChunkBiomeMemo::new);

    private IChunkWrapper chunk;
    private IBiomeWrapper biome;
    private int relX;
    private int relZ;
    private int quartY;

    private DhChunkBiomeMemo() {
    }

    public static DhChunkBiomeMemo local() {
        return LOCAL.get();
    }

    public IBiomeWrapper tryGet(IChunkWrapper chunkWrapper, int relX, int relY, int relZ) {
        return this.chunk == chunkWrapper && this.relX == relX && this.relZ == relZ && this.quartY == (relY >> 2)
                ? this.biome
                : null;
    }

    public void put(IChunkWrapper chunkWrapper, int relX, int relY, int relZ, IBiomeWrapper biomeWrapper) {
        this.chunk = chunkWrapper;
        this.relX = relX;
        this.relZ = relZ;
        this.quartY = relY >> 2;
        this.biome = biomeWrapper;
    }
}
