package com.misanthropy.collections_of_optimizations.mixin.enigmaticlegacy;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(targets = "com.aizistral.enigmaticlegacy.items.CursedRing", remap = false)
public abstract class MixinCursedRingAngerScan {

    @Redirect(
            method = "curioTick",
            at = @At(
                    value = "INVOKE",
                    ordinal = 0,
                    target = "Lnet/minecraft/world/level/Level;m_45976_(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
            ),
            require = 0
    )
    private List<LivingEntity> coo$narrowAngerScan(Level level, Class<LivingEntity> type, AABB box) {
        if (!CoOConfig.enigmaticlegacyNarrowCursedRingAngerScan) {
            return level.getEntitiesOfClass(type, box);
        }
        return level.getEntitiesOfClass(type, box, entity -> entity instanceof Piglin || entity instanceof NeutralMob);
    }
}
