package com.misanthropy.collections_of_optimizations.mixin.cucumber;

import com.blakebr0.cucumber.client.handler.TagTooltipHandler;
import com.blakebr0.cucumber.config.ModConfigs;
import com.blakebr0.cucumber.helper.FluidHelper;
import com.blakebr0.cucumber.lib.Tooltips;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TagTooltipHandler.class, remap = false)
public class MixinCucumberTagTooltip {

    @Inject(
            method = "onItemTooltip(Lnet/minecraftforge/event/entity/player/ItemTooltipEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void coo$skipTagListBuild(ItemTooltipEvent event, CallbackInfo ci) {
        if (!CoOConfig.cucumberLeanTagTooltip || Screen.hasControlDown()) {
            return;
        }

        if (!ModConfigs.ENABLE_TAG_TOOLTIPS.get() || !Minecraft.getInstance().options.advancedItemTooltips) {
            ci.cancel();
            return;
        }

        if (coo$hasAnyTag(event.getItemStack())) {
            event.getToolTip().add(Tooltips.HOLD_CTRL_FOR_TAGS.build());
        }

        ci.cancel();
    }

    @Unique
    private static boolean coo$hasAnyTag(ItemStack stack) {
        var block = Block.byItem(stack.getItem());
        if (block != Blocks.AIR && block.defaultBlockState().getTags().findAny().isPresent()) {
            return true;
        }

        if (stack.getTags().findAny().isPresent()) {
            return true;
        }

        for (var tags : FluidHelper.getFluidTags(stack).values()) {
            if (!tags.isEmpty()) {
                return true;
            }
        }

        return false;
    }
}
