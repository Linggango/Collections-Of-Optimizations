package com.misanthropy.collections_of_optimizations.mixin.curios;

import com.misanthropy.collections_of_optimizations.core.CurioPresenceHolder;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;
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
        this.coo$curioFilterMemo = null;
    }

    @Unique
    private Map<Object, ItemStack> coo$curioFilterMemo;

    @Unique
    private long coo$curioFilterMemoStamp = Long.MIN_VALUE;

    @Override
    public Map<Object, ItemStack> coo$curioFilterMemo(long stamp) {
        if (this.coo$curioFilterMemo == null) {
            this.coo$curioFilterMemo = new Reference2ObjectOpenHashMap<>(8);
            this.coo$curioFilterMemoStamp = stamp;
        } else if (this.coo$curioFilterMemoStamp != stamp) {
            this.coo$curioFilterMemo.clear();
            this.coo$curioFilterMemoStamp = stamp;
        }
        return this.coo$curioFilterMemo;
    }
}
