package com.misanthropy.collections_of_optimizations.mixin.crittersandcompanions;

import com.misanthropy.collections_of_optimizations.core.CacNecklaceHolder;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@Mixin(Player.class)
public abstract class MixinPlayerCacNecklace implements CacNecklaceHolder {

    @Unique
    private Optional<?> coo$cacNecklace;

    @Unique
    private long coo$cacNecklaceStamp = Long.MIN_VALUE;

    @Override
    public Optional<?> coo$cacNecklace() {
        return this.coo$cacNecklace;
    }

    @Override
    public long coo$cacNecklaceStamp() {
        return this.coo$cacNecklaceStamp;
    }

    @Override
    public void coo$storeCacNecklace(Optional<?> necklace, long stamp) {
        this.coo$cacNecklace = necklace;
        this.coo$cacNecklaceStamp = stamp;
    }
}
