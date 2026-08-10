package com.misanthropy.collections_of_optimizations.mixin.ambientsounds;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.AmbientBiomeMatchCache;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import team.creative.ambientsounds.condition.BiomeCondition;
import team.creative.ambientsounds.environment.BiomeEnvironment;

@Mixin(value = BiomeEnvironment.BiomeArea.class, remap = false)
public abstract class MixinBiomeArea {

    @Shadow
    @Final
    public Holder<Biome> biome;

    @WrapMethod(method = "checkBiome", require = 0)
    private boolean coo$memoBiomeMatch(BiomeCondition[] conditions, Operation<Boolean> original) {

        if (!CoOConfig.ambientsoundsMemoBiomeMatch || conditions == null || !Minecraft.getInstance().isSameThread()) {
            return original.call((Object) conditions);
        }

        Boolean cached = AmbientBiomeMatchCache.get(conditions, this.biome);
        if (cached != null) {
            return cached;
        }

        boolean matched = original.call((Object) conditions);
        AmbientBiomeMatchCache.put(conditions, this.biome, matched);
        return matched;
    }
}
