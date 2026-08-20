package com.misanthropy.collections_of_optimizations.mixin.cosmeticarmor;

import com.misanthropy.collections_of_optimizations.core.CosArmorRestoreQueueHolder;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayDeque;
import java.util.Deque;

@Mixin(Player.class)
public abstract class MixinPlayerCosArmorQueue implements CosArmorRestoreQueueHolder {

    @Unique
    private Deque<Runnable> coo$cosArmorRestoreQueue;

    @Override
    public Deque<Runnable> coo$cosArmorRestoreQueue() {
        Deque<Runnable> queue = this.coo$cosArmorRestoreQueue;
        if (queue == null) {
            this.coo$cosArmorRestoreQueue = queue = new ArrayDeque<>();
        }
        return queue;
    }
}
