package com.misanthropy.collections_of_optimizations.mixin.vanilla;

import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.PlayerMap;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChunkMap.class)
public interface ChunkMapAccessor {

    @Accessor("playerMap")
    PlayerMap coo$getPlayerMap();

    @Invoker("removeEntity")
    void coo$removeEntity(Entity entity);
}
