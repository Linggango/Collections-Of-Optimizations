package com.misanthropy.collections_of_optimizations.mixin.naturesaura;

import com.misanthropy.collections_of_optimizations.core.AuraChunkHolder;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LevelChunk.class)
public abstract class MixinLevelChunkAuraCache implements AuraChunkHolder {

    @Unique
    private LazyOptional<?> coo$auraChunkCap;

    @Unique
    private boolean coo$auraChunkCapResolved;

    @Override
    public LazyOptional<?> coo$auraChunkCap() {
        if (this.coo$auraChunkCapResolved) {
            return this.coo$auraChunkCap;
        }

        LazyOptional<IAuraChunk> resolved =
                ((LevelChunk) (Object) this).getCapability(NaturesAuraAPI.CAP_AURA_CHUNK, null);
        this.coo$auraChunkCap = resolved;
        this.coo$auraChunkCapResolved = true;
        resolved.addListener(ignored -> {
            this.coo$auraChunkCap = null;
            this.coo$auraChunkCapResolved = false;
        });
        return resolved;
    }
}
