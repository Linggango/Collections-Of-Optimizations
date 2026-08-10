package com.misanthropy.collections_of_optimizations.mixin.patchouli;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.PatchouliBookLookup;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.item.ItemModBook;
import vazkii.patchouli.common.util.ItemStackUtil;

@Mixin(value = ItemStackUtil.class, remap = false)
public abstract class MixinItemStackUtil {

    @Inject(
            method = "getBookFromStack",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$cachedBookLookup(ItemStack stack, CallbackInfoReturnable<Book> cir) {
        if (!CoOConfig.patchouliCacheBookItemLookup) {
            return;
        }
        if (stack.getItem() instanceof ItemModBook) {
            return;
        }
        cir.setReturnValue(PatchouliBookLookup.forItem(stack.getItem()));
    }
}
