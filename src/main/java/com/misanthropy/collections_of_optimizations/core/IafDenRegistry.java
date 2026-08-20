package com.misanthropy.collections_of_optimizations.core;

import com.misanthropy.collections_of_optimizations.CoOConfig;
import com.misanthropy.collections_of_optimizations.Collections_of_optimizations;
import com.misanthropy.collections_of_optimizations.worldgen.iafden.IafDenPiece;
import com.misanthropy.collections_of_optimizations.worldgen.iafden.IafDenStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class IafDenRegistry {

    public static final Logger LOGGER = LogManager.getLogger("collections_of_optimizations/iafden");

    private static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, Collections_of_optimizations.MODID);
    private static final DeferredRegister<StructurePieceType> PIECE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PIECE, Collections_of_optimizations.MODID);

    public static final RegistryObject<StructureType<IafDenStructure>> DEN_TYPE =
            STRUCTURE_TYPES.register("iaf_dragon_den", () -> () -> IafDenStructure.CODEC);
    public static final RegistryObject<StructurePieceType> DEN_PIECE_TYPE =
            PIECE_TYPES.register("iaf_dragon_den_piece", () -> (StructurePieceType.ContextlessType) IafDenPiece::new);

    private IafDenRegistry() {
    }

    public static void register() {
        STRUCTURE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        PIECE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static boolean isPortActive() {
        if (!CoOConfig.iafdragonfixStructureDens) {
            return false;
        }
        if (portInstalled == null) {
            portInstalled = ModList.get().isLoaded("iceandfire") && !ModList.get().isLoaded("iafdragonfix");
            if (portInstalled) {
                try {
                    Class.forName("com.github.alexthe666.iceandfire.entity.EntityDragonBase",
                            false, IafDenRegistry.class.getClassLoader());
                } catch (Throwable throwable) {
                    portInstalled = false;
                    LOGGER.warn("Ice and Fire fork is being used (e.g. Community Edition) - "
                            + "dragon den generation disabled. Use the original Ice and Fire mod (which is recommended) to enable it.");
                }
            }
        }
        return portInstalled;
    }

    private static Boolean portInstalled;
}
