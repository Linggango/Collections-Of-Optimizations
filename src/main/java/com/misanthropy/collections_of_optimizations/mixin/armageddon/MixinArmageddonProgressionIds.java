package com.misanthropy.collections_of_optimizations.mixin.armageddon;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.core.ArmageddonProgressionIds;
import net.mcreator.armageddonmod.procedures.BloodyArmorEquippedPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.CALArmorPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.DiamondArmorPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.ElveniteArmorPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.EmeraldArmorPreventionProcedure;
import net.mcreator.armageddonmod.procedures.EnchantedArmorPreventionProcedure;
import net.mcreator.armageddonmod.procedures.GoldArmorPreventionProcedrueProcedure;
import net.mcreator.armageddonmod.procedures.HelioniteArmorEquippedPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.HollowPickaxeTickUpdateProcedure;
import net.mcreator.armageddonmod.procedures.IncreaseLifeOfMobsAndDamgetestProcedureProcedure;
import net.mcreator.armageddonmod.procedures.IronArmorPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.NetherPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.NetheriteArmorPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.ShadowArmorEquippedPreventionProcedureProcedure;
import net.mcreator.armageddonmod.procedures.VoideriteArmorPreventionProcedureProcedure;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {
        BloodyArmorEquippedPreventionProcedureProcedure.class,
        CALArmorPreventionProcedureProcedure.class,
        DiamondArmorPreventionProcedureProcedure.class,
        ElveniteArmorPreventionProcedureProcedure.class,
        EmeraldArmorPreventionProcedure.class,
        EnchantedArmorPreventionProcedure.class,
        GoldArmorPreventionProcedrueProcedure.class,
        HelioniteArmorEquippedPreventionProcedureProcedure.class,
        HollowPickaxeTickUpdateProcedure.class,
        IncreaseLifeOfMobsAndDamgetestProcedureProcedure.class,
        IronArmorPreventionProcedureProcedure.class,
        NetherPreventionProcedureProcedure.class,
        NetheriteArmorPreventionProcedureProcedure.class,
        ShadowArmorEquippedPreventionProcedureProcedure.class,
        VoideriteArmorPreventionProcedureProcedure.class
}, remap = false)
public abstract class MixinArmageddonProgressionIds {

    @WrapOperation(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resources/ResourceLocation;parse(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"
            ),
            require = 0
    )
    private static ResourceLocation coo$cacheParsedId(String id, Operation<ResourceLocation> original) {
        if (!CoOConfig.armageddonCacheProgressionIds) {
            return original.call(id);
        }
        return ArmageddonProgressionIds.parse(id);
    }

    @WrapOperation(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resources/ResourceLocation;toString()Ljava/lang/String;"
            ),
            require = 0
    )
    private static String coo$cachePrintedId(ResourceLocation id, Operation<String> original) {
        if (!CoOConfig.armageddonCacheProgressionIds) {
            return original.call(id);
        }
        return ArmageddonProgressionIds.print(id);
    }
}
