package com.misanthropy.collections_of_optimizations.mixin.biolith;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.terraformersmc.biolith.impl.biome.InterfaceBiomeSource;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServerBiolith {

    @Inject(
            method = "createLevels",
            at = @At("HEAD"),
            require = 0
    )
    private void coo$restampSwappedBiomeSources(ChunkProgressListener listener, CallbackInfo ci) {
        if (!CoOConfig.biolithRestampSwappedBiomeSource) {
            return;
        }

        MinecraftServer server = (MinecraftServer) (Object) this;
        Registry<LevelStem> stems = server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);

        for (LevelStem stem : stems) {
            BiomeSource source = stem.generator().getBiomeSource();
            if (!(source instanceof MultiNoiseBiomeSource) || !(source instanceof InterfaceBiomeSource biolith)) {
                continue;
            }
            if (!InterfaceBiomeSource.DIMENSION_TYPE_UNDEFINED.location().equals(biolith.biolith$getDimensionType().location())) {
                continue;
            }
            Optional<ResourceKey<DimensionType>> type = stem.type().unwrapKey();
            if (type.isEmpty()) {
                continue;
            }
            ((MultiNoiseBiomeSourceAccessor) source).coo$parameters();
            biolith.biolith$setDimensionType(type.get());
        }
    }
}
