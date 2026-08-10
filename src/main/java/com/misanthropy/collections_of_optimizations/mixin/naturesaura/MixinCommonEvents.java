package com.misanthropy.collections_of_optimizations.mixin.naturesaura;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.AuraChunkHolder;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.events.CommonEvents;
import net.minecraft.core.Direction;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = CommonEvents.class, remap = false)
public abstract class MixinCommonEvents {

    @SuppressWarnings("unchecked")
    @WrapOperation(
            method = "onLevelTick(Lnet/minecraftforge/event/TickEvent$LevelTickEvent;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/chunk/LevelChunk;getCapability(Lnet/minecraftforge/common/capabilities/Capability;Lnet/minecraft/core/Direction;)Lnet/minecraftforge/common/util/LazyOptional;"
            ),
            require = 0
    )
    private LazyOptional<IAuraChunk> coo$cachedAuraChunkCap(
            LevelChunk chunk,
            Capability<IAuraChunk> capability,
            Direction side,
            Operation<LazyOptional<IAuraChunk>> original) {
        if (!CoOConfig.naturesauraFastAuraChunkSweep
                || side != null
                || capability != NaturesAuraAPI.CAP_AURA_CHUNK
                || !(chunk instanceof AuraChunkHolder holder)) {
            return original.call(chunk, capability, side);
        }
        return (LazyOptional<IAuraChunk>) holder.coo$auraChunkCap();
    }
}
