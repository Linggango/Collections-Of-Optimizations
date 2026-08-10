package com.misanthropy.collections_of_optimizations.mixin.borninchaos;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ModEntityFilter;
import net.mcreator.borninchaosv.procedures.BonescallerStaffKazhdyiTikVInvientarieProcedure;
import net.mcreator.borninchaosv.procedures.MissionaryHatKazhdyiTikDliaShliemaProcedure;
import net.mcreator.borninchaosv.procedures.PumpkinstaffaKazhdyiTikVInvientarieProcedure;
import net.mcreator.borninchaosv.procedures.SpiderBiteKazhdyiTikVInvientarieProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Predicate;

@Mixin(value = {
        BonescallerStaffKazhdyiTikVInvientarieProcedure.class,
        MissionaryHatKazhdyiTikDliaShliemaProcedure.class,
        PumpkinstaffaKazhdyiTikVInvientarieProcedure.class,
        SpiderBiteKazhdyiTikVInvientarieProcedure.class
}, remap = false)
public abstract class MixinBicMinionScans {

    @WrapOperation(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/LevelAccessor;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"
            ),
            remap = true,
            require = 0
    )
    private static List<Entity> coo$onlyCollectBornInChaosEntities(LevelAccessor level, Class<Entity> type, AABB box,
                                                                   Predicate<? super Entity> filter,
                                                                   Operation<List<Entity>> original) {
        if (!CoOConfig.borninchaosNarrowMinionScans) {
            return original.call(level, type, box, filter);
        }
        Predicate<? super Entity> narrowed =
                (Predicate<Entity>) entity -> ModEntityFilter.BORN_IN_CHAOS.matches(entity) && filter.test(entity);
        return original.call(level, type, box, narrowed);
    }
}
