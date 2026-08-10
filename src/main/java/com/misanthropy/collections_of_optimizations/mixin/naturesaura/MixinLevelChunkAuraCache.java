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
    private Object coo$auraChunk;

    @Unique
    private boolean coo$auraChunkResolved;

    @Override
    public Object coo$auraChunk() {
        if (this.coo$auraChunkResolved) {
            return this.coo$auraChunk;
        }

        LazyOptional<IAuraChunk> optional =
                ((LevelChunk) (Object) this).getCapability(NaturesAuraAPI.CAP_AURA_CHUNK, null);
        IAuraChunk resolved = optional.orElse(null);
        this.coo$auraChunk = resolved;
        this.coo$auraChunkResolved = true;

        if (resolved != null) {
            optional.addListener(ignored -> {
                this.coo$auraChunk = null;
                this.coo$auraChunkResolved = false;
            });
        }
        return resolved;
    }
}
