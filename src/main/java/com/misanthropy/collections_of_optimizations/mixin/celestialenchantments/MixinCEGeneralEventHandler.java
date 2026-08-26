package com.misanthropy.collections_of_optimizations.mixin.celestialenchantments;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.xiaoyue.celestial_enchantments.event.CEGeneralEventHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Map;

@Mixin(value = CEGeneralEventHandler.class, remap = false)
public abstract class MixinCEGeneralEventHandler {

    @Inject(method = "onLivingTick", at = @At("HEAD"), cancellable = true, require = 0)
    private static void coo$skipUnenchantedTick(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        if (!CoOConfig.celestialenchantmentsSkipUnenchantedTick) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity.tickCount % 4 != 0) {
            return;
        }
        if (entity.getItemBySlot(EquipmentSlot.MAINHAND).isEnchanted()
                || entity.getItemBySlot(EquipmentSlot.OFFHAND).isEnchanted()
                || entity.getItemBySlot(EquipmentSlot.HEAD).isEnchanted()
                || entity.getItemBySlot(EquipmentSlot.CHEST).isEnchanted()
                || entity.getItemBySlot(EquipmentSlot.LEGS).isEnchanted()
                || entity.getItemBySlot(EquipmentSlot.FEET).isEnchanted()) {
            return;
        }
        ci.cancel();
    }

    @WrapOperation(
            method = "onLivingTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/xiaoyue/celestial_enchantments/utils/IEnchUtils;getEnch(Lnet/minecraft/world/entity/LivingEntity;[Lnet/minecraft/world/entity/EquipmentSlot;)Ljava/util/Map;"),
            require = 0)
    private static Map<?, ?> coo$leanSlotEnchScan(LivingEntity entity, EquipmentSlot[] slots,
                                                  Operation<Map<?, ?>> original) {
        if (CoOConfig.celestialenchantmentsLeanSlotEnchScan) {
            boolean anyEnchanted = false;
            for (EquipmentSlot slot : slots) {
                if (entity.getItemBySlot(slot).isEnchanted()) {
                    anyEnchanted = true;
                    break;
                }
            }
            if (!anyEnchanted) {
                return Collections.emptyMap();
            }
        }
        return original.call(entity, slots);
    }
}
