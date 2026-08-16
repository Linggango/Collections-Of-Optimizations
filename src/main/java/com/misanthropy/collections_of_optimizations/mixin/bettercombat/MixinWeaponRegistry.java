package com.misanthropy.collections_of_optimizations.mixin.bettercombat;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.WeaponAttributesCache;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.logic.WeaponRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WeaponRegistry.class, remap = false)
public abstract class MixinWeaponRegistry {

    @Unique
    private static boolean coo$carriesOwnAttributes(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains("weapon_attributes", Tag.TAG_STRING);
    }

    @Inject(
            method = "getAttributes(Lnet/minecraft/world/item/ItemStack;)Lnet/bettercombat/api/WeaponAttributes;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void coo$cachedByItem(ItemStack stack, CallbackInfoReturnable<WeaponAttributes> cir) {
        if (!CoOConfig.bettercombatCacheWeaponAttributes || stack == null || coo$carriesOwnAttributes(stack)) {
            return;
        }
        Object cached = WeaponAttributesCache.lookup(stack.getItem());
        if (cached != null) {
            cir.setReturnValue((WeaponAttributes) WeaponAttributesCache.unwrap(cached));
        }
    }

    @Inject(
            method = "getAttributes(Lnet/minecraft/world/item/ItemStack;)Lnet/bettercombat/api/WeaponAttributes;",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$rememberByItem(ItemStack stack, CallbackInfoReturnable<WeaponAttributes> cir) {
        if (!CoOConfig.bettercombatCacheWeaponAttributes || stack == null || coo$carriesOwnAttributes(stack)) {
            return;
        }
        WeaponAttributesCache.store(stack.getItem(), cir.getReturnValue());
    }

    @Inject(
            method = "loadAttributes(Lnet/minecraft/server/packs/resources/ResourceManager;)V",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$dropCacheOnReload(ResourceManager resourceManager, CallbackInfo ci) {
        WeaponAttributesCache.clear();
    }

    @Inject(
            method = "decodeRegistry(Lnet/minecraft/network/FriendlyByteBuf;)V",
            at = @At("RETURN"),
            require = 0
    )
    private static void coo$dropCacheOnSync(FriendlyByteBuf buffer, CallbackInfo ci) {
        WeaponAttributesCache.clear();
    }
}
