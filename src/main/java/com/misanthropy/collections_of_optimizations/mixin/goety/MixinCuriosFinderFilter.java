package com.misanthropy.collections_of_optimizations.mixin.goety;

import com.Polarice3.Goety.utils.CuriosFinder;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.CurioPresenceHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;
import java.util.function.Predicate;

@Mixin(value = CuriosFinder.class, remap = false)
public abstract class MixinCuriosFinderFilter {

    @WrapMethod(method = "findCurio(Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Lnet/minecraft/world/item/ItemStack;")
    private static ItemStack coo$memoCurioFilter(LivingEntity livingEntity, Predicate<ItemStack> filter,
                                                 Operation<ItemStack> original) {
        if (!CoOConfig.goetyMemoCurioFilter
                || filter == null
                || !(livingEntity instanceof Player)
                || !(livingEntity instanceof CurioPresenceHolder holder)) {
            return original.call(livingEntity, filter);
        }
        Map<Object, ItemStack> memo = holder.coo$curioFilterMemo(livingEntity.tickCount);
        ItemStack cached = memo.get(filter);
        if (cached == null) {
            cached = original.call(livingEntity, filter);
            memo.put(filter, cached);
        }
        return cached;
    }
}
