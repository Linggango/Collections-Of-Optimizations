package com.misanthropy.collections_of_optimizations.mixin.skarriermobs;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.mcreator.skarriermobs.entity.EndorahHeadMainEntity;
import net.mcreator.skarriermobs.entity.EndorahHeadMeleeEntity;
import net.mcreator.skarriermobs.entity.EndorahHeadRangedEntity;
import net.mcreator.skarriermobs.procedures.EndorahBodyOnEntityTickUpdateProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Predicate;

@Mixin(value = EndorahBodyOnEntityTickUpdateProcedure.class, remap = false)
public abstract class MixinSkarrierEndorahBodyScans {

    @WrapOperation(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/LevelAccessor;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
                    ordinal = 1
            ),
            remap = true,
            require = 0
    )
    private static List<Entity> coo$onlyCollectMainHeads(LevelAccessor level, Class<Entity> type, AABB box,
                                                         Predicate<? super Entity> filter,
                                                         Operation<List<Entity>> original) {
        return coo$narrow(level, type, box, filter, original, EndorahHeadMainEntity.class);
    }

    @WrapOperation(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/LevelAccessor;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
                    ordinal = 3
            ),
            remap = true,
            require = 0
    )
    private static List<Entity> coo$onlyCollectMeleeHeads(LevelAccessor level, Class<Entity> type, AABB box,
                                                          Predicate<? super Entity> filter,
                                                          Operation<List<Entity>> original) {
        return coo$narrow(level, type, box, filter, original, EndorahHeadMeleeEntity.class);
    }

    @WrapOperation(
            method = "execute(Lnet/minecraft/world/level/LevelAccessor;DDDLnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/LevelAccessor;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;",
                    ordinal = 5
            ),
            remap = true,
            require = 0
    )
    private static List<Entity> coo$onlyCollectRangedHeads(LevelAccessor level, Class<Entity> type, AABB box,
                                                           Predicate<? super Entity> filter,
                                                           Operation<List<Entity>> original) {
        return coo$narrow(level, type, box, filter, original, EndorahHeadRangedEntity.class);
    }

    @Unique
    private static List<Entity> coo$narrow(LevelAccessor level, Class<Entity> type, AABB box,
                                           Predicate<? super Entity> filter, Operation<List<Entity>> original,
                                           Class<?> head) {
        if (!CoOConfig.skarriermobsNarrowRegionScans || type != Entity.class || box.getXsize() != 20.0D) {
            return original.call(level, type, box, filter);
        }
        return original.call(level, head, box, filter);
    }
}
