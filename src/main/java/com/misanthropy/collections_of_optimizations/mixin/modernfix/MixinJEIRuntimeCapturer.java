package com.misanthropy.collections_of_optimizations.mixin.modernfix;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import mezz.jei.library.runtime.JeiRuntime;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import org.embeddedt.modernfix.searchtree.JEIRuntimeCapturer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Mixin(value = JEIRuntimeCapturer.class, remap = false)
public abstract class MixinJEIRuntimeCapturer {

    @Shadow
    private static JeiRuntime runtimeHandle;

    @Shadow
    private static Set<CreativeModeTab> representedTabs;

    @Inject(
            method = "getRepresentedTabs",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$indexStacksOnce(CallbackInfoReturnable<Set<CreativeModeTab>> cir) {
        if (!CoOConfig.modernfixFastRepresentedTabs || representedTabs != null || runtimeHandle == null) {
            return;
        }
        Object2IntOpenCustomHashMap<ItemStack> jeiStacks = new Object2IntOpenCustomHashMap<>(ItemStackLinkedSet.TYPE_AND_TAG);
        for (ItemStack stack : runtimeHandle.getIngredientManager().getAllItemStacks()) {
            jeiStacks.addTo(stack, 1);
        }
        Set<CreativeModeTab> represented = new HashSet<>();
        represented.add(CreativeModeTabs.searchTab());
        for (CreativeModeTab tab : CreativeModeTabs.allTabs()) {
            if (tab.getType() == CreativeModeTab.Type.SEARCH) {
                continue;
            }
            Collection<ItemStack> displayed = tab.getSearchTabDisplayItems();
            int matched = 0;
            for (ItemStack stack : displayed) {
                matched += jeiStacks.getInt(stack);
            }
            if (matched >= displayed.size() / 4) {
                represented.add(tab);
            }
        }
        representedTabs = Set.copyOf(represented);
        cir.setReturnValue(representedTabs);
    }
}
