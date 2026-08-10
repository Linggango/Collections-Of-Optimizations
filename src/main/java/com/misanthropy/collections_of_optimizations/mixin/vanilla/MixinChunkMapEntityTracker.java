package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.world.level.entity.EntityAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChunkMap.class)
public abstract class MixinChunkMapEntityTracker {

    @WrapOperation(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/SectionPos;of(Lnet/minecraft/world/level/entity/EntityAccess;)Lnet/minecraft/core/SectionPos;"
            ),
            require = 0
    )
    private SectionPos coo$reuseUnchangedSectionPos(EntityAccess entity, Operation<SectionPos> original,
                                                    @Local(ordinal = 0) SectionPos lastSectionPos) {
        if (!CoOConfig.vanillaLeanTrackerSectionPos || lastSectionPos == null) {
            return original.call(entity);
        }
        BlockPos pos = entity.blockPosition();
        if (lastSectionPos.x() == SectionPos.blockToSectionCoord(pos.getX())
                && lastSectionPos.y() == SectionPos.blockToSectionCoord(pos.getY())
                && lastSectionPos.z() == SectionPos.blockToSectionCoord(pos.getZ())) {
            return lastSectionPos;
        }
        return original.call(entity);
    }
}
