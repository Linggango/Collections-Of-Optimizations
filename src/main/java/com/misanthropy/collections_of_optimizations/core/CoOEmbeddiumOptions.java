package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.Collections_of_optimizations;
import me.jellysquid.mods.sodium.client.gui.options.OptionFlag;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpact;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.resources.ResourceLocation;
import org.embeddedt.embeddium.api.OptionPageConstructionEvent;
import org.embeddedt.embeddium.client.gui.options.StandardOptions;

public final class CoOEmbeddiumOptions {

    private static final Object DATA = new Object();

    private static final OptionStorage<Object> STORAGE = new OptionStorage<>() {
        @Override
        public Object getData() {
            return DATA;
        }

        @Override
        public void save() {
            CoOConfig.save();
        }
    };

    private CoOEmbeddiumOptions() {
    }

    public static void register() {
        OptionPageConstructionEvent.BUS.addListener(CoOEmbeddiumOptions::onPageBuilt);
    }

    private static void onPageBuilt(OptionPageConstructionEvent event) {
        if (!event.getId().matches(StandardOptions.Pages.QUALITY)) {
            return;
        }

        event.addGroup(OptionGroup.createBuilder()
                .setId(new ResourceLocation(Collections_of_optimizations.MODID, "biome_blend"))
                .add(OptionImpl.createBuilder(Boolean.class, STORAGE)
                        .setId(new ResourceLocation(Collections_of_optimizations.MODID, "fast_biome_blend"))
                        .setControl(TickBoxControl::new)
                        .setBinding((data, value) -> CoOConfig.setFastBiomeBlend(value),
                                data -> CoOConfig.vanillaFastBiomeBlend)
                        .setImpact(OptionImpact.MEDIUM)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build())
                .build());
    }
}
