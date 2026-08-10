package com.misanthropy.collections_of_optimizations.mixin.iafdragonfix;

import com.github.alexthe666.iceandfire.world.gen.WorldGenDragonRoosts;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = WorldGenDragonRoosts.class, remap = false)
public interface WorldGenDragonRoostsInvoker {

    @Invoker("spawnDragon")
    void coo$spawnDragon(FeaturePlaceContext<NoneFeatureConfiguration> context, int size, boolean isMale);

    @Invoker("generateSurface")
    void coo$generateSurface(FeaturePlaceContext<NoneFeatureConfiguration> context, int radius);

    @Invoker("generateShell")
    void coo$generateShell(FeaturePlaceContext<NoneFeatureConfiguration> context, int radius);

    @Invoker("hollowOut")
    void coo$hollowOut(FeaturePlaceContext<NoneFeatureConfiguration> context, int radius);

    @Invoker("generateDecoration")
    void coo$generateDecoration(FeaturePlaceContext<NoneFeatureConfiguration> context, int radius, boolean isMale);
}
