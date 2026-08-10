package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.StructureStateHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureCheck;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(StructureCheck.class)
public abstract class MixinStructureCheck implements StructureStateHolder {

    @Shadow
    @Final
    private Registry<Structure> structureConfigs;

    @Unique
    private ChunkGeneratorStructureState coo$structureState;

    @Unique
    private Map<Structure, List<StructurePlacement>> coo$placements;

    @Override
    public void coo$setStructureState(ChunkGeneratorStructureState state) {
        if (this.coo$structureState != state) {
            this.coo$structureState = state;
            this.coo$placements = null;
        }
    }

    @WrapOperation(
            method = "canCreateStructure",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/Structure;findValidGenerationPoint(Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)Ljava/util/Optional;"
            ),
            require = 0
    )
    private Optional<Structure.GenerationStub> coo$skipImpossiblePlacements(Structure structure,
                                                                           Structure.GenerationContext context,
                                                                           Operation<Optional<Structure.GenerationStub>> original) {
        ChunkGeneratorStructureState state = this.coo$structureState;
        if (!CoOConfig.vanillaFasterStructureLocation || state == null) {
            return original.call(structure, context);
        }
        ChunkPos pos = context.chunkPos();
        for (StructurePlacement placement : this.coo$placementsFor(structure, state)) {
            if (placement.isStructureChunk(state, pos.x, pos.z)) {
                return original.call(structure, context);
            }
        }
        return Optional.empty();
    }

    @Unique
    private List<StructurePlacement> coo$placementsFor(Structure structure, ChunkGeneratorStructureState state) {
        Map<Structure, List<StructurePlacement>> cache = this.coo$placements;
        if (cache == null) {
            cache = new IdentityHashMap<>(4);
            this.coo$placements = cache;
        }
        List<StructurePlacement> placements = cache.get(structure);
        if (placements == null) {
            Holder<Structure> holder = this.structureConfigs.wrapAsHolder(structure);
            placements = state.getPlacementsForStructure(holder);
            cache.put(structure, placements);
        }
        return placements;
    }
}
