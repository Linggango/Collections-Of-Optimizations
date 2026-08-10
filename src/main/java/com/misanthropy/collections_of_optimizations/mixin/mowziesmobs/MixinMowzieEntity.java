package com.misanthropy.collections_of_optimizations.mixin.mowziesmobs;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MowzieEntity.class, remap = false)
public abstract class MixinMowzieEntity {

    @Unique
    private int coo$lastMusicPacketTick = Integer.MIN_VALUE;

    @Unique
    private int coo$lastMusicPacketId = -1;

    @WrapWithCondition(
            method = "m_8119_",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;broadcastEntityEvent(Lnet/minecraft/world/entity/Entity;B)V",
                    remap = true
            ),
            require = 0
    )
    private boolean coo$throttleBossMusicPacket(Level level, Entity entity, byte eventId) {
        int interval = CoOConfig.mowziesmobsBossMusicPacketInterval;
        if (interval <= 1) {
            return true;
        }
        int tick = ((MowzieEntity) (Object) this).tickCount;
        if (eventId != this.coo$lastMusicPacketId || tick - this.coo$lastMusicPacketTick >= interval) {
            this.coo$lastMusicPacketId = eventId;
            this.coo$lastMusicPacketTick = tick;
            return true;
        }
        return false;
    }
}
