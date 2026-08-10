package com.misanthropy.collections_of_optimizations;

import com.misanthropy.collections_of_optimizations.core.CurioPresenceCache;
import com.misanthropy.collections_of_optimizations.core.CuriosSlotCache;
import com.misanthropy.collections_of_optimizations.core.IafDenRegistry;
import com.misanthropy.collections_of_optimizations.core.RecipeCacheGeneration;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Collections_of_optimizations.MODID)
public class Collections_of_optimizations {
    public static final String MODID = "collections_of_optimizations";

    public Collections_of_optimizations() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CoOConfig.SPEC, MODID + "-common.toml");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CoOConfig::onLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CoOConfig::onReload);

        if (ModList.get().isLoaded("bloodmagic")) {
            RecipeCacheGeneration.register();
        }

        if (ModList.get().isLoaded("curios")) {
            CuriosSlotCache.register();
            CurioPresenceCache.register();
        }

        IafDenRegistry.register();
    }
}
