package com.misanthropy.collections_of_optimizations.mixin.naturesaura;

import com.misanthropy.collections_of_optimizations.core.AuraChunkAccess;
import de.ellpeck.naturesaura.chunk.AuraChunk;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(value = AuraChunk.class, remap = false)
public abstract class MixinAuraChunk implements AuraChunkAccess {

    @Shadow
    @Final
    private Map<BlockPos, AuraChunk.DrainSpot> drainSpots;

    @Shadow
    private boolean needsSync;

    @Override
    public boolean coo$hasNoWork() {
        return !this.needsSync && this.drainSpots.isEmpty();
    }
}
