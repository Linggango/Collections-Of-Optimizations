package com.misanthropy.collections_of_optimizations.mixin.morerelics;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;

@Mixin(targets = "com.blorb.morerelics.MoreRelicsUtil", remap = false)
public abstract class MixinMoreRelicsUtil {

    @Inject(
            method = "lambda$actuallyGetEquippedRelics$0",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$hoistEquippedCurios(List<ItemStack> items, ICuriosItemHandler handler, CallbackInfo ci) {
        if (!CoOConfig.morerelicsHoistEquippedCurios) {
            return;
        }
        IItemHandlerModifiable equipped = handler.getEquippedCurios();
        int slots = equipped.getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack stack = equipped.getStackInSlot(i);
            if (stack.getItem() instanceof IRelicItem) {
                items.add(stack);
            }
        }
        ci.cancel();
    }
}
