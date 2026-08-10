package com.misanthropy.collections_of_optimizations.mixin.terramity;

import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClipContext.class)
public interface ClipContextAccessor {

    @Accessor("block")
    ClipContext.Block coo$block();

    @Accessor("fluid")
    ClipContext.Fluid coo$fluid();

    @Accessor("collisionContext")
    CollisionContext coo$collisionContext();
}
