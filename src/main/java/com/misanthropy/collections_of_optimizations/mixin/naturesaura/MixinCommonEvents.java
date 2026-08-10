package com.misanthropy.collections_of_optimizations.mixin.naturesaura;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.AuraChunkAccess;
import com.misanthropy.collections_of_optimizations.core.AuraChunkHolder;
import de.ellpeck.naturesaura.chunk.AuraChunk;
import de.ellpeck.naturesaura.events.CommonEvents;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CommonEvents.class, remap = false)
public abstract class MixinCommonEvents {

    @Inject(
            method = "onLevelTick(Lnet/minecraftforge/event/TickEvent$LevelTickEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$fastAuraChunkSweep(TickEvent.LevelTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.naturesauraFastAuraChunkSweep) {
            return;
        }

        Level level = event.level;
        if (level.isClientSide
                || event.phase != TickEvent.Phase.END
                || level.getGameTime() % 20L != 0L
                || !(level.getChunkSource() instanceof ServerChunkCache cache)) {
            return;
        }

        ci.cancel();

        ProfilerFiller profiler = level.getProfiler();
        profiler.push("naturesaura:onLevelTick");

        ChunkMap map = cache.chunkMap;
        for (ChunkHolder holder : ((ChunkMapAccessor) map).coo$getChunks()) {
            LevelChunk chunk = holder.getTickingChunk();
            if (chunk == null) {
                continue;
            }
            Object cached = ((AuraChunkHolder) chunk).coo$auraChunk();
            if (cached instanceof AuraChunk auraChunk && !((AuraChunkAccess) auraChunk).coo$hasNoWork()) {
                auraChunk.update();
            }
        }

        profiler.pop();
    }
}
