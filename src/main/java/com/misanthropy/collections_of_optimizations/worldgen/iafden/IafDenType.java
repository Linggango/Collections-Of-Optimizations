package com.misanthropy.collections_of_optimizations.worldgen.iafden;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum IafDenType implements StringRepresentable {
    FIRE_CAVE("fire_dragon_cave", true),
    ICE_CAVE("ice_dragon_cave", true),
    LIGHTNING_CAVE("lightning_dragon_cave", true),
    FIRE_ROOST("fire_dragon_roost", false),
    ICE_ROOST("ice_dragon_roost", false),
    LIGHTNING_ROOST("lightning_dragon_roost", false);

    public static final Codec<IafDenType> CODEC = StringRepresentable.fromEnum(IafDenType::values);

    private final String id;
    private final boolean cave;

    IafDenType(String id, boolean cave) {
        this.id = id;
        this.cave = cave;
    }

    public boolean isCave() {
        return this.cave;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }
}
