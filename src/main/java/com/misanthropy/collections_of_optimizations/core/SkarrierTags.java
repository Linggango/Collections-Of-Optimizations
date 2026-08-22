 package com.misanthropy.collections_of_optimizations.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public final class SkarrierTags {

    public static final TagKey<EntityType<?>> BURNS_IN_DAYLIGHT =
            TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("skarrier_mobs", "burns_in_daylight"));

    public static final TagKey<EntityType<?>> FLORE_HEAD_HOST =
            TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("skarrier_mobs", "flore_head_host"));

    public static final TagKey<EntityType<?>> RAIDERS =
            TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("minecraft", "raiders"));

    public static final TagKey<Item> RESISTEEL_TOOLS =
            TagKey.create(Registries.ITEM, new ResourceLocation("skarrier_mobs", "resisteel_tools"));

    private SkarrierTags() {
    }
}
