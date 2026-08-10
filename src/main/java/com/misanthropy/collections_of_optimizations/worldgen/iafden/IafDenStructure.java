package com.misanthropy.collections_of_optimizations.worldgen.iafden;

import com.misanthropy.collections_of_optimizations.core.IafDenRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class IafDenStructure extends Structure {

    public static final Codec<IafDenStructure> CODEC = RecordCodecBuilder.<IafDenStructure>mapCodec(instance -> instance
            .group(settingsCodec(instance), IafDenType.CODEC.fieldOf("dragon_type").forGetter(structure -> structure.denType))
            .apply(instance, IafDenStructure::new)).codec();

    private final IafDenType denType;

    public IafDenStructure(StructureSettings settings, IafDenType denType) {
        super(settings);
        this.denType = denType;
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {

        if (!IafDenRegistry.isPortActive()) {
            return Optional.empty();
        }
        BlockPos origin = context.chunkPos().getMiddleBlockPosition(0);
        if (this.denType.isCave()) {
            return Optional.of(new GenerationStub(origin, builder -> builder.addPiece(new IafDenPiece(origin, this.denType))));
        }
        int surface = context.chunkGenerator().getFirstOccupiedHeight(
                origin.getX(), origin.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockPos surfacePos = new BlockPos(origin.getX(), surface, origin.getZ());
        return Optional.of(new GenerationStub(surfacePos, builder -> builder.addPiece(new IafDenPiece(surfacePos, this.denType))));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return IafDenRegistry.DEN_TYPE.get();
    }
}
