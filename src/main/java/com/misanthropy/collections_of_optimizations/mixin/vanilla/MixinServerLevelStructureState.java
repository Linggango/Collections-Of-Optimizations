package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.misanthropy.collections_of_optimizations.core.StructureStateHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevelStructureState {

    @Shadow
    @Final
    private ServerChunkCache chunkSource;

    @Shadow
    @Final
    private StructureCheck structureCheck;

    @Inject(method = "<init>", at = @At("RETURN"), require = 0)
    private void coo$passStructureState(CallbackInfo ci) {
        if (this.structureCheck instanceof StructureStateHolder holder) {
            holder.coo$setStructureState(this.chunkSource.getGeneratorState());
        }
    }
}
