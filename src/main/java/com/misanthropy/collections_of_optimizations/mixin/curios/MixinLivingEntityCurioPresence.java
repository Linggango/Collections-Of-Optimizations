package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.core.CurioPresenceHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntityCurioPresence implements CurioPresenceHolder {

    @Unique
    private Set<Item> coo$curioPresence;

    @Unique
    private long coo$curioPresenceStamp;

    @Override
    public Set<Item> coo$curioPresence() {
        return this.coo$curioPresence;
    }

    @Override
    public long coo$curioPresenceStamp() {
        return this.coo$curioPresenceStamp;
    }

    @Override
    public void coo$storeCurioPresence(Set<Item> items, long stamp) {
        this.coo$curioPresence = items;
        this.coo$curioPresenceStamp = stamp;
    }

    @Override
    public void coo$invalidateCurioPresence() {
        this.coo$curioPresence = null;
    }
}
