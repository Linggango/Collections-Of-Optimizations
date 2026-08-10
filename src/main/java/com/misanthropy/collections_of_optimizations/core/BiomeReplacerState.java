package com.misanthropy.collections_of_optimizations.core;

import net.jadenxgamer.elysium_api.Elysium;
import net.jadenxgamer.elysium_api.impl.biome.ElysiumBiomeHelper;
import net.jadenxgamer.elysium_api.impl.registry.ElysiumRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Biome;

public final class BiomeReplacerState {

    private static volatile Holder<Biome> placeholder;

    private BiomeReplacerState() {
    }

    public static boolean noReplacersConfigured() {
        try {
            if (!ElysiumBiomeHelper.overworldBiomes.isEmpty() || !ElysiumBiomeHelper.netherBiomes.isEmpty()) {
                return false;
            }
            RegistryAccess access = Elysium.registryAccess;
            if (access == null) {
                return false;
            }
            return access.registry(ElysiumRegistries.BIOME_REPLACER)
                    .map(registry -> registry.size() == 0)
                    .orElse(false);
        } catch (Throwable throwable) {
            return false;
        }
    }

    public static Holder<Biome> placeholder() {
        return placeholder;
    }

    public static void rememberPlaceholder(Holder<Biome> holder) {
        if (placeholder == null && holder != null) {
            placeholder = holder;
        }
    }
}
