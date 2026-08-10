package com.misanthropy.collections_of_optimizations;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public final class CoOConfig {

    public static final ForgeConfigSpec SPEC;
    private static final CoOConfig VALUES;

    public static boolean masterEnabled = true;

    public static boolean curiosSkipSlotlessEntities = true;
    public static boolean curiosSkipClientTickOnNonPlayers = true;
    public static boolean curiosSkipNonPlayerRenderLayer = true;
    public static boolean curiosCacheEntitySlotLookup = true;
    public static boolean curiosFastEquippedItemMiss = true;

    public static boolean artifactsSkipClientTickOnNonPlayers = true;
    public static boolean artifactsFastPathKittySlippers = true;
    public static boolean artifactsFastPathUmbrella = true;

    public static boolean caelusSkipGroundedNonPlayers = true;

    public static boolean blockswapPaletteFilteredRetroGen = true;
    public static boolean justdirethingsAvoidChunkTickets = true;
    public static int goetydelightCakeScanInterval = 4;

    public static boolean geckolibReuseRenderVectors = true;
    public static boolean geckolibCacheBoneLookup = true;

    public static boolean saintsdragonsSkipRedundantBoneTracking = true;
    public static boolean saintsdragonsCacheShakeScan = true;

    public static boolean immediatelyfastSingleBufferLookup = true;

    public static int fancymenuSeamlessCaptureInterval = 30;

    public static boolean emfDropZeroAngerEntries = true;

    public static boolean etfFastValidPath = true;

    public static boolean oculusSkipSignTextInShadowPass = true;
    public static boolean oculusSkipGlintInShadowPass = true;
    public static boolean oculusSkipNameTagsInShadowPass = true;
    public static boolean oculusSkipBannerPatternsInShadowPass = true;

    public static boolean lootrSkipIdleTileTicker = true;
    public static int lootrTileTickerBudget = 512;

    public static boolean naturesauraFastAuraChunkSweep = true;

    public static int xaeroMinimapRenderFpsCap = 30;
    public static boolean w2w2DeferWaypointSave = true;

    public static boolean terrablenderCacheNamespaceRule = true;

    public static boolean terramitySkipItemAnimationCopies = true;
    public static boolean terramitySkipForeignEntityAnimations = true;
    public static boolean terramityMemoizeProcedureRaycasts = true;
    public static boolean terramitySkipClientCurioScans = true;
    public static boolean terramitySkipArmorAnimationScan = true;
    public static boolean terramityFixPhasingShaderStomp = true;

    public static boolean armageddonSkipForeignEntityAnimations = true;
    public static boolean armageddonCacheProgressionIds = true;

    public static boolean borninchaosSkipItemAnimationCopies = true;
    public static boolean borninchaosSkipForeignEntityAnimations = true;
    public static boolean borninchaosSkipRedundantDimensionRefresh = true;
    public static boolean borninchaosNarrowMinionScans = true;

    public static boolean bloodmagicCacheArcRecipeList = true;
    public static boolean bloodmagicCacheArcFurnaceRecipe = true;
    public static boolean bloodmagicFastRoutingConnectivity = true;

    public static boolean animusCacheEquivalencyPreview = true;

    public static boolean patchouliCacheBookItemLookup = true;

    public static boolean structurifyFastStructureSetLookup = true;
    public static boolean structurifySkipDisabledStructureChecks = true;
    public static boolean structurifyLeanHeightCache = true;
    public static boolean structurifyLeanOverlapSections = true;
    public static boolean structurifyCacheStructureSetEntries = true;
    public static boolean structurifySkipStartCheckWrap = true;

    public static boolean bossesriseNarrowCinematicScan = true;
    public static boolean soulsweaponsLeanDespawnTimer = true;
    public static boolean konweaponSkipItemAnimationCopies = true;
    public static boolean immersiveaircraftBatchOverlay = true;
    public static boolean ftbchunksSkipHiddenMinimapWork = true;
    public static boolean ftbchunksFastRegionWrite = true;
    public static boolean punchyCacheResourceStackMisses = true;
    public static boolean l2hostilitySkipTraitlessCapLookup = true;
    public static boolean iceandfireFastEntityDataLookup = true;
    public static boolean iceandfireSkipPathDebugRender = true;
    public static boolean iceandfireSkipEmptyArmorLayer = true;
    public static boolean iceandfireCacheDragonTexture = true;
    public static boolean iceandfireSkipEmptyDragonLayers = true;
    public static boolean iceandfireLeanMultipartTick = true;
    public static int iceandfireDragonTargetSearchHeight = 32;
    public static boolean iafdragonfixStructureDens = true;
    public static int iafdragonfixRoostSpawnDistance = 800;
    public static int iafdragonfixCaveSpawnDistance = 800;
    public static boolean mowziesmobsFastCapabilityLookup = true;
    public static boolean mowziesmobsDedupeCapabilityAttach = true;
    public static boolean mowziesmobsCacheCameraShakeScan = true;
    public static int mowziesmobsBossMusicPacketInterval = 5;
    public static boolean mowziesmobsLeanBoneLookup = true;
    public static boolean mowziesmobsHoistChainRenderMatrix = true;
    public static int mowziesmobsDynamicChainSubstepCap = 4;
    public static boolean mowziesmobsCacheUmvuthanaLeader = true;
    public static boolean mowziesmobsLeanModelBoxVectors = true;
    public static boolean mowziesmobsSkipBlankElokosaTransform = true;
    public static boolean mowziesmobsLeanLayerBoneScan = true;
    public static boolean mowziesmobsCacheEffectRenderTypes = true;
    public static boolean pickupnotifierSkipOpaqueSpriteBuffer = true;
    public static boolean placeboSkipEmptyEnchantmentEvent = true;

    public static boolean photonLeanParticleQuads = true;
    public static boolean photonLeanParticleLight = true;
    public static boolean photonLeanTrailVertices = true;
    public static boolean photonDropEmptyEffectCacheEntries = true;
    public static boolean integratedapiSkipEmptyBeardifier = true;
    public static boolean echelonCacheTierAttributeUuids = true;
    public static boolean elysiumapiMemoClimateSample = true;
    public static boolean elysiumapiSkipUnusedBiomeReplacerLookup = true;
    public static boolean enigmaticdiceFastCurioMiss = true;
    public static boolean balmMemoDynamicModelKeys = true;

    public static boolean dungeoncrawlSkipBlockEntityProbe = true;

    public static boolean moonlightSkipEmptyMapMarkerScan = true;

    public static boolean pehkuiLeanScaleTick = true;

    public static boolean tonsofenchantsSkipAbsentAttributeRemoval = true;
    public static boolean tonsofenchantsFrostbiteSkipClient = true;
    public static boolean tonsofenchantsLeanAttributeLookup = true;
    public static boolean tonsofenchantsSinglePhasePlayerTick = true;

    public static boolean subtleeffectsFireflyDarknessGate = true;
    public static boolean subtleeffectsCapBiomeParticleScan = true;
    public static boolean subtleeffectsLeanTickerRemoval = true;
    public static boolean subtleeffectsGeyserBlockPreFilter = true;

    public static boolean arsengSkipDeadRelayListeners = true;
    public static boolean arsengGateGenericInvWrapper = true;

    public static boolean perceptionShareDefaultTrailData = true;

    public static boolean quarkSkipPigLitterTagChurn = true;

    public static boolean zetaLeanStructureReplacement = true;
    public static boolean zetaShareEventWrappers = true;

    public static boolean goetyCacheCapabilityOptional = true;
    public static boolean goetySkipCapabilityFallback = true;
    public static boolean goetyMemoAttributeModifiers = true;
    public static boolean goetyFastEmptyAllyCheck = true;
    public static boolean goetyFastCurioItemMiss = true;
    public static boolean goetySkipBossMusicTargetLookup = true;
    public static boolean goetyCacheFogWightScan = true;

    public static boolean mythsandlegendsCacheFogBossScan = true;
    public static boolean mythsandlegendsCacheShakeScan = true;

    public static boolean ambientsoundsMemoBiomeMatch = true;
    public static int arsnouveauSkyTextureInterval = 1;
    public static boolean pehkuiMemoInteractionBoxScales = true;

    public static boolean goetyrevelationCacheHaloLookup = true;
    public static boolean revelationfixSkipMobFluidStandScan = true;
    public static boolean revelationfixSkipNonSpiderHurtByTargetEvents = true;

    public static boolean macabreSkipForeignEntityAnimations = true;
    public static boolean macabreSkipItemAnimationCopies = true;
    public static boolean macabreCoalesceVariableSync = true;

    public static boolean alexsmobsSkipCreeperAvoidGoals = true;
    public static int alexsmobsSpiderFlyScanInterval = 10;
    public static boolean alexsmobsReleaseLevelMaps = true;

    public static boolean alexscavesMemoRareBiomeQuads = true;
    public static boolean alexscavesMemoClimateSample = true;
    public static boolean alexscavesCacheShakeScan = true;

    public static boolean adastraMemoPlanetDefaults = true;

    public static int itemEntityRenderCap = 1;
    public static boolean vanillaFasterStructureLocation = true;
    public static boolean vanillaFixBoatFallDamage = false;
    public static boolean vanillaPredictableItemDrops = false;
    public static boolean vanillaLeanTrackerSectionPos = true;
    public static boolean vanillaLeanSuffocationScan = true;
    public static boolean vanillaLeanMenuBroadcast = true;
    public static boolean vanillaLeanTrackerDelta = true;
    public static boolean vanillaCacheBiomeQuartLookups = true;
    public static boolean vanillaMemoCameraFluid = true;

    private final ForgeConfigSpec.BooleanValue masterEnabledValue;

    private final ForgeConfigSpec.BooleanValue curiosSkipSlotlessEntitiesValue;
    private final ForgeConfigSpec.BooleanValue curiosSkipClientTickOnNonPlayersValue;
    private final ForgeConfigSpec.BooleanValue curiosSkipNonPlayerRenderLayerValue;
    private final ForgeConfigSpec.BooleanValue curiosCacheEntitySlotLookupValue;
    private final ForgeConfigSpec.BooleanValue curiosFastEquippedItemMissValue;

    private final ForgeConfigSpec.BooleanValue artifactsSkipClientTickOnNonPlayersValue;
    private final ForgeConfigSpec.BooleanValue artifactsFastPathKittySlippersValue;
    private final ForgeConfigSpec.BooleanValue artifactsFastPathUmbrellaValue;

    private final ForgeConfigSpec.BooleanValue caelusSkipGroundedNonPlayersValue;

    private final ForgeConfigSpec.BooleanValue blockswapPaletteFilteredRetroGenValue;
    private final ForgeConfigSpec.BooleanValue justdirethingsAvoidChunkTicketsValue;
    private final ForgeConfigSpec.IntValue goetydelightCakeScanIntervalValue;

    private final ForgeConfigSpec.BooleanValue geckolibReuseRenderVectorsValue;
    private final ForgeConfigSpec.BooleanValue geckolibCacheBoneLookupValue;

    private final ForgeConfigSpec.BooleanValue saintsdragonsSkipRedundantBoneTrackingValue;
    private final ForgeConfigSpec.BooleanValue saintsdragonsCacheShakeScanValue;

    private final ForgeConfigSpec.BooleanValue immediatelyfastSingleBufferLookupValue;

    private final ForgeConfigSpec.IntValue fancymenuSeamlessCaptureIntervalValue;

    private final ForgeConfigSpec.BooleanValue emfDropZeroAngerEntriesValue;

    private final ForgeConfigSpec.BooleanValue etfFastValidPathValue;

    private final ForgeConfigSpec.BooleanValue oculusSkipSignTextInShadowPassValue;
    private final ForgeConfigSpec.BooleanValue oculusSkipGlintInShadowPassValue;
    private final ForgeConfigSpec.BooleanValue oculusSkipNameTagsInShadowPassValue;
    private final ForgeConfigSpec.BooleanValue oculusSkipBannerPatternsInShadowPassValue;

    private final ForgeConfigSpec.BooleanValue lootrSkipIdleTileTickerValue;
    private final ForgeConfigSpec.IntValue lootrTileTickerBudgetValue;

    private final ForgeConfigSpec.BooleanValue naturesauraFastAuraChunkSweepValue;

    private final ForgeConfigSpec.IntValue xaeroMinimapRenderFpsCapValue;
    private final ForgeConfigSpec.BooleanValue w2w2DeferWaypointSaveValue;

    private final ForgeConfigSpec.BooleanValue terrablenderCacheNamespaceRuleValue;

    private final ForgeConfigSpec.BooleanValue terramitySkipItemAnimationCopiesValue;
    private final ForgeConfigSpec.BooleanValue terramitySkipForeignEntityAnimationsValue;
    private final ForgeConfigSpec.BooleanValue terramityMemoizeProcedureRaycastsValue;
    private final ForgeConfigSpec.BooleanValue terramitySkipClientCurioScansValue;
    private final ForgeConfigSpec.BooleanValue terramitySkipArmorAnimationScanValue;
    private final ForgeConfigSpec.BooleanValue terramityFixPhasingShaderStompValue;

    private final ForgeConfigSpec.BooleanValue armageddonSkipForeignEntityAnimationsValue;
    private final ForgeConfigSpec.BooleanValue armageddonCacheProgressionIdsValue;

    private final ForgeConfigSpec.BooleanValue borninchaosSkipItemAnimationCopiesValue;
    private final ForgeConfigSpec.BooleanValue borninchaosSkipForeignEntityAnimationsValue;
    private final ForgeConfigSpec.BooleanValue borninchaosSkipRedundantDimensionRefreshValue;
    private final ForgeConfigSpec.BooleanValue borninchaosNarrowMinionScansValue;

    private final ForgeConfigSpec.BooleanValue bloodmagicCacheArcRecipeListValue;
    private final ForgeConfigSpec.BooleanValue bloodmagicCacheArcFurnaceRecipeValue;
    private final ForgeConfigSpec.BooleanValue bloodmagicFastRoutingConnectivityValue;

    private final ForgeConfigSpec.BooleanValue animusCacheEquivalencyPreviewValue;

    private final ForgeConfigSpec.BooleanValue patchouliCacheBookItemLookupValue;

    private final ForgeConfigSpec.BooleanValue structurifyFastStructureSetLookupValue;
    private final ForgeConfigSpec.BooleanValue structurifySkipDisabledStructureChecksValue;
    private final ForgeConfigSpec.BooleanValue structurifyLeanHeightCacheValue;
    private final ForgeConfigSpec.BooleanValue structurifyLeanOverlapSectionsValue;
    private final ForgeConfigSpec.BooleanValue structurifyCacheStructureSetEntriesValue;
    private final ForgeConfigSpec.BooleanValue structurifySkipStartCheckWrapValue;

    private final ForgeConfigSpec.BooleanValue bossesriseNarrowCinematicScanValue;
    private final ForgeConfigSpec.BooleanValue soulsweaponsLeanDespawnTimerValue;
    private final ForgeConfigSpec.BooleanValue konweaponSkipItemAnimationCopiesValue;
    private final ForgeConfigSpec.BooleanValue immersiveaircraftBatchOverlayValue;
    private final ForgeConfigSpec.BooleanValue ftbchunksSkipHiddenMinimapWorkValue;
    private final ForgeConfigSpec.BooleanValue ftbchunksFastRegionWriteValue;
    private final ForgeConfigSpec.BooleanValue punchyCacheResourceStackMissesValue;
    private final ForgeConfigSpec.BooleanValue l2hostilitySkipTraitlessCapLookupValue;
    private final ForgeConfigSpec.BooleanValue iceandfireFastEntityDataLookupValue;
    private final ForgeConfigSpec.BooleanValue iceandfireSkipPathDebugRenderValue;
    private final ForgeConfigSpec.BooleanValue iceandfireSkipEmptyArmorLayerValue;
    private final ForgeConfigSpec.BooleanValue iceandfireCacheDragonTextureValue;
    private final ForgeConfigSpec.BooleanValue iceandfireSkipEmptyDragonLayersValue;
    private final ForgeConfigSpec.BooleanValue iceandfireLeanMultipartTickValue;
    private final ForgeConfigSpec.IntValue iceandfireDragonTargetSearchHeightValue;
    private final ForgeConfigSpec.BooleanValue iafdragonfixStructureDensValue;
    private final ForgeConfigSpec.IntValue iafdragonfixRoostSpawnDistanceValue;
    private final ForgeConfigSpec.IntValue iafdragonfixCaveSpawnDistanceValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsFastCapabilityLookupValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsDedupeCapabilityAttachValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsCacheCameraShakeScanValue;
    private final ForgeConfigSpec.IntValue mowziesmobsBossMusicPacketIntervalValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsLeanBoneLookupValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsHoistChainRenderMatrixValue;
    private final ForgeConfigSpec.IntValue mowziesmobsDynamicChainSubstepCapValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsCacheUmvuthanaLeaderValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsLeanModelBoxVectorsValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsSkipBlankElokosaTransformValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsLeanLayerBoneScanValue;
    private final ForgeConfigSpec.BooleanValue mowziesmobsCacheEffectRenderTypesValue;
    private final ForgeConfigSpec.BooleanValue pickupnotifierSkipOpaqueSpriteBufferValue;
    private final ForgeConfigSpec.BooleanValue placeboSkipEmptyEnchantmentEventValue;
    private final ForgeConfigSpec.BooleanValue photonLeanParticleQuadsValue;
    private final ForgeConfigSpec.BooleanValue photonLeanParticleLightValue;
    private final ForgeConfigSpec.BooleanValue photonLeanTrailVerticesValue;
    private final ForgeConfigSpec.BooleanValue photonDropEmptyEffectCacheEntriesValue;
    private final ForgeConfigSpec.BooleanValue integratedapiSkipEmptyBeardifierValue;
    private final ForgeConfigSpec.BooleanValue echelonCacheTierAttributeUuidsValue;
    private final ForgeConfigSpec.BooleanValue elysiumapiMemoClimateSampleValue;
    private final ForgeConfigSpec.BooleanValue elysiumapiSkipUnusedBiomeReplacerLookupValue;
    private final ForgeConfigSpec.BooleanValue enigmaticdiceFastCurioMissValue;
    private final ForgeConfigSpec.BooleanValue balmMemoDynamicModelKeysValue;

    private final ForgeConfigSpec.BooleanValue dungeoncrawlSkipBlockEntityProbeValue;

    private final ForgeConfigSpec.BooleanValue moonlightSkipEmptyMapMarkerScanValue;

    private final ForgeConfigSpec.BooleanValue pehkuiLeanScaleTickValue;

    private final ForgeConfigSpec.BooleanValue tonsofenchantsSkipAbsentAttributeRemovalValue;
    private final ForgeConfigSpec.BooleanValue tonsofenchantsFrostbiteSkipClientValue;
    private final ForgeConfigSpec.BooleanValue tonsofenchantsLeanAttributeLookupValue;
    private final ForgeConfigSpec.BooleanValue tonsofenchantsSinglePhasePlayerTickValue;

    private final ForgeConfigSpec.BooleanValue subtleeffectsFireflyDarknessGateValue;
    private final ForgeConfigSpec.BooleanValue subtleeffectsCapBiomeParticleScanValue;
    private final ForgeConfigSpec.BooleanValue subtleeffectsLeanTickerRemovalValue;
    private final ForgeConfigSpec.BooleanValue subtleeffectsGeyserBlockPreFilterValue;

    private final ForgeConfigSpec.BooleanValue arsengSkipDeadRelayListenersValue;
    private final ForgeConfigSpec.BooleanValue arsengGateGenericInvWrapperValue;

    private final ForgeConfigSpec.BooleanValue perceptionShareDefaultTrailDataValue;

    private final ForgeConfigSpec.BooleanValue quarkSkipPigLitterTagChurnValue;

    private final ForgeConfigSpec.BooleanValue zetaLeanStructureReplacementValue;
    private final ForgeConfigSpec.BooleanValue zetaShareEventWrappersValue;

    private final ForgeConfigSpec.BooleanValue goetyCacheCapabilityOptionalValue;
    private final ForgeConfigSpec.BooleanValue goetySkipCapabilityFallbackValue;
    private final ForgeConfigSpec.BooleanValue goetyMemoAttributeModifiersValue;
    private final ForgeConfigSpec.BooleanValue goetyFastEmptyAllyCheckValue;
    private final ForgeConfigSpec.BooleanValue goetyFastCurioItemMissValue;
    private final ForgeConfigSpec.BooleanValue goetySkipBossMusicTargetLookupValue;
    private final ForgeConfigSpec.BooleanValue goetyCacheFogWightScanValue;

    private final ForgeConfigSpec.BooleanValue mythsandlegendsCacheFogBossScanValue;
    private final ForgeConfigSpec.BooleanValue mythsandlegendsCacheShakeScanValue;

    private final ForgeConfigSpec.BooleanValue ambientsoundsMemoBiomeMatchValue;
    private final ForgeConfigSpec.IntValue arsnouveauSkyTextureIntervalValue;
    private final ForgeConfigSpec.BooleanValue pehkuiMemoInteractionBoxScalesValue;

    private final ForgeConfigSpec.BooleanValue goetyrevelationCacheHaloLookupValue;
    private final ForgeConfigSpec.BooleanValue revelationfixSkipMobFluidStandScanValue;
    private final ForgeConfigSpec.BooleanValue revelationfixSkipNonSpiderHurtByTargetEventsValue;

    private final ForgeConfigSpec.BooleanValue macabreSkipForeignEntityAnimationsValue;
    private final ForgeConfigSpec.BooleanValue macabreSkipItemAnimationCopiesValue;
    private final ForgeConfigSpec.BooleanValue macabreCoalesceVariableSyncValue;

    private final ForgeConfigSpec.BooleanValue alexsmobsSkipCreeperAvoidGoalsValue;
    private final ForgeConfigSpec.IntValue alexsmobsSpiderFlyScanIntervalValue;
    private final ForgeConfigSpec.BooleanValue alexsmobsReleaseLevelMapsValue;

    private final ForgeConfigSpec.BooleanValue alexscavesMemoRareBiomeQuadsValue;
    private final ForgeConfigSpec.BooleanValue alexscavesMemoClimateSampleValue;
    private final ForgeConfigSpec.BooleanValue alexscavesCacheShakeScanValue;

    private final ForgeConfigSpec.BooleanValue adastraMemoPlanetDefaultsValue;

    private final ForgeConfigSpec.IntValue itemEntityRenderCapValue;
    private final ForgeConfigSpec.BooleanValue vanillaFasterStructureLocationValue;
    private final ForgeConfigSpec.BooleanValue vanillaFixBoatFallDamageValue;
    private final ForgeConfigSpec.BooleanValue vanillaPredictableItemDropsValue;
    private final ForgeConfigSpec.BooleanValue vanillaLeanTrackerSectionPosValue;
    private final ForgeConfigSpec.BooleanValue vanillaLeanSuffocationScanValue;
    private final ForgeConfigSpec.BooleanValue vanillaLeanMenuBroadcastValue;
    private final ForgeConfigSpec.BooleanValue vanillaLeanTrackerDeltaValue;
    private final ForgeConfigSpec.BooleanValue vanillaCacheBiomeQuartLookupsValue;
    private final ForgeConfigSpec.BooleanValue vanillaMemoCameraFluidValue;

    static {
        Pair<CoOConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CoOConfig::new);
        VALUES = pair.getLeft();
        SPEC = pair.getRight();
    }

    private CoOConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Master switch for every patch in this mod. Useful for A/B testing.").push("general");
        this.masterEnabledValue = builder.define("enabled", true);
        builder.pop();

        builder.comment("Curios API patches.").push("curios");
        this.curiosSkipSlotlessEntitiesValue = builder
                .comment("Skip the per-tick curios handler for entity types that have no curio slots at all.")
                .define("skipSlotlessEntities", true);
        this.curiosSkipClientTickOnNonPlayersValue = builder
                .comment("Skip the client-side curios tick for non-player entities.")
                .define("skipClientTickOnNonPlayers", true);
        this.curiosSkipNonPlayerRenderLayerValue = builder
                .comment("Never attach the Curios render layer to non-player entity renderers.")
                .define("skipNonPlayerRenderLayer", true);
        this.curiosCacheEntitySlotLookupValue = builder
                .comment("Cache the per-entity curio slot lookup on the entity itself.")
                .define("cacheEntitySlotLookup", true);
        this.curiosFastEquippedItemMissValue = builder
                .comment("Answer 'this entity is not wearing that item' from a per-tick set instead of a full curios inventory walk.")
                .define("fastEquippedItemMiss", true);
        builder.pop();

        builder.comment("Artifacts patches.").push("artifacts");
        this.artifactsSkipClientTickOnNonPlayersValue = builder
                .comment("Skip the client-side Artifacts living tick for non-player entities.")
                .define("skipClientTickOnNonPlayers", true);
        this.artifactsFastPathKittySlippersValue = builder
                .comment("Skip the kitty slippers curios scan when the entity has no last-hurt-by mob.")
                .define("fastPathKittySlippers", true);
        this.artifactsFastPathUmbrellaValue = builder
                .comment("Skip the charm of sinking curios scan when the umbrella glide check cannot pass anyway.")
                .define("fastPathUmbrella", true);
        builder.pop();

        builder.comment("Caelus patches.").push("caelus");
        this.caelusSkipGroundedNonPlayersValue = builder
                .comment("Skip the flight attribute lookup for non-player entities that are not already fall-flying.")
                .define("skipGroundedNonPlayers", true);
        builder.pop();

        builder.comment("Block Swap patches.").push("blockswap");
        this.blockswapPaletteFilteredRetroGenValue = builder
                .comment("Filter the retro-gen chunk sweep through each section's block state palette.")
                .define("paletteFilteredRetroGen", true);
        builder.pop();

        builder.comment("Just Dire Things patches.").push("justdirethings");
        this.justdirethingsAvoidChunkTicketsValue = builder
                .comment("Read the item entity's block state through getChunkNow instead of the loading getChunk.")
                .define("avoidChunkTickets", true);
        builder.pop();

        builder.comment("Goety's Delight patches.").push("goetydelight");
        this.goetydelightCakeScanIntervalValue = builder
                .comment("Run the cherry blossom cake entity sweep once every N server ticks instead of every tick.")
                .defineInRange("cakeScanInterval", 4, 1, 100);
        builder.pop();

        builder.comment("GeckoLib patches.").push("geckolib");
        this.geckolibReuseRenderVectorsValue = builder
                .comment("Reuse the scratch vectors GeckoLib allocates while writing model geometry.")
                .define("reuseRenderVectors", true);
        this.geckolibCacheBoneLookupValue = builder
                .comment("Memoise BakedGeoModel#getBone(String) per baked model.")
                .define("cacheBoneLookup", true);
        builder.pop();

        builder.comment("Saint's Dragons patches.").push("saintsdragons");
        this.saintsdragonsSkipRedundantBoneTrackingValue = builder
                .comment("Enable matrix tracking on a dragon's bones once per model instead of every pass.")
                .define("skipRedundantBoneTracking", true);
        this.saintsdragonsCacheShakeScanValue = builder
                .comment("Look for screen shaking dragons once per tick instead of once per frame.")
                .define("cacheShakeScan", true);
        builder.pop();

        builder.comment("ImmediatelyFast patches.").push("immediatelyfast");
        this.immediatelyfastSingleBufferLookupValue = builder
                .comment("Find a render layer's buffer with one map lookup instead of a contains check plus a get.")
                .define("singleBufferLookup", true);
        builder.pop();

        builder.comment("FancyMenu patches.").push("fancymenu");
        this.fancymenuSeamlessCaptureIntervalValue = builder
                .comment("Seconds between seamless world loading screenshots. FancyMenu reads back the whole framebuffer each time, which stalls the render thread. Stock behaviour is 1.")
                .defineInRange("seamlessCaptureInterval", 30, 1, 600);
        builder.pop();

        builder.comment("Entity Model Features patches.").push("emf");
        this.emfDropZeroAngerEntriesValue = builder
                .comment("Stop the anger time map growing one entry per neutral mob ever rendered.")
                .define("dropZeroAngerEntries", true);
        builder.pop();

        builder.comment("Entity Texture Features patches.").push("etf");
        this.etfFastValidPathValue = builder
                .comment("Answer ResourceLocation#isValidPath at HEAD when the path is valid.")
                .define("fastValidPath", true);
        builder.pop();

        builder.comment("Oculus / Iris shadow pass patches.").push("oculus");
        this.oculusSkipSignTextInShadowPassValue = builder
                .comment("Skip sign text while rendering the shadow map.")
                .define("skipSignTextInShadowPass", true);
        this.oculusSkipGlintInShadowPassValue = builder
                .comment("Report items as having no enchantment glint while rendering the shadow map.")
                .define("skipGlintInShadowPass", true);
        this.oculusSkipNameTagsInShadowPassValue = builder
                .comment("Skip entity name tags while rendering the shadow map.")
                .define("skipNameTagsInShadowPass", true);
        this.oculusSkipBannerPatternsInShadowPassValue = builder
                .comment("Skip banner pattern layers while rendering the shadow map, keeping the base cloth.")
                .define("skipBannerPatternsInShadowPass", true);
        builder.pop();

        builder.comment("Lootr patches.").push("lootr");
        this.lootrSkipIdleTileTickerValue = builder
                .comment("Skip Lootr's per-server-tick container conversion pass while its queues are empty.")
                .define("skipIdleTileTicker", true);
        this.lootrTileTickerBudgetValue = builder
                .comment("Maximum container conversion candidates Lootr may examine per server tick.")
                .defineInRange("tileTickerBudget", 512, 0, 65536);
        builder.pop();

        builder.comment("Nature's Aura patches.").push("naturesaura");
        this.naturesauraFastAuraChunkSweepValue = builder
                .comment("Keep each chunk's Nature's Aura capability handle instead of walking the capability dispatcher once per loaded chunk per second.")
                .define("fastAuraChunkSweep", true);
        builder.pop();

        builder.comment("Xaero's Minimap patches.").push("xaerominimap");
        this.xaeroMinimapRenderFpsCapValue = builder
                .comment("Maximum times per second the minimap redraws its map contents.")
                .defineInRange("renderFpsCap", 30, 0, 260);
        builder.pop();

        builder.comment("Xaero's + Waystones compatibility patches.").push("w2w2");
        this.w2w2DeferWaypointSaveValue = builder
                .comment("Collapse the waypoint file writes this mod does when waystone data arrives.")
                .define("deferWaypointSave", true);
        builder.pop();

        builder.comment("TerraBlender patches.").push("terrablender");
        this.terrablenderCacheNamespaceRuleValue = builder
                .comment("Cache TerraBlender's per-block namespace lookup for the duration of a biome.")
                .define("cacheNamespaceRule", true);
        builder.pop();

        builder.comment("Terramity patches.").push("terramity");
        this.terramitySkipItemAnimationCopiesValue = builder
                .comment("Bail out of Terramity's held item animation handler before it copies your hands.")
                .define("skipItemAnimationCopies", true);
        this.terramitySkipForeignEntityAnimationsValue = builder
                .comment("Skip Terramity's entity animation handler for entities that are not Terramity's.")
                .define("skipForeignEntityAnimations", true);
        this.terramityMemoizeProcedureRaycastsValue = builder
                .comment("Reuse the answer when a Terramity procedure raytraces the same ray twice in a row.")
                .define("memoizeProcedureRaycasts", true);
        this.terramitySkipClientCurioScansValue = builder
                .comment("Skip the four Terramity accessory tick procedures on the client, where they do nothing.")
                .define("skipClientCurioScans", true);
        this.terramitySkipArmorAnimationScanValue = builder
                .comment("Bail out of Terramity's armour animation handler before it re-reads your equipment.")
                .define("skipArmorAnimationScan", true);
        this.terramityFixPhasingShaderStompValue = builder
                .comment("Keep Terramity's screen shaders to your own player and to shaders Terramity loaded.")
                .define("fixPhasingShaderStomp", true);
        builder.pop();

        builder.comment("Armageddon patches.").push("armageddon");
        this.armageddonSkipForeignEntityAnimationsValue = builder
                .comment("Skip Armageddon's entity animation handler for entities that are not Armageddon's.")
                .define("skipForeignEntityAnimations", true);
        this.armageddonCacheProgressionIdsValue = builder
                .comment("Stop Armageddon's progression gate from re-parsing the same identifiers every tick.")
                .define("cacheProgressionIds", true);
        builder.pop();

        builder.comment("Born in Chaos patches.").push("borninchaos");
        this.borninchaosSkipItemAnimationCopiesValue = builder
                .comment("Bail out of Born in Chaos' held item animation handler before it copies your hands.")
                .define("skipItemAnimationCopies", true);
        this.borninchaosSkipForeignEntityAnimationsValue = builder
                .comment("Skip Born in Chaos' entity animation handler for entities that are not its own.")
                .define("skipForeignEntityAnimations", true);
        this.borninchaosSkipRedundantDimensionRefreshValue = builder
                .comment("Stop 83 Born in Chaos mobs from resizing themselves once per tick for no reason.")
                .define("skipRedundantDimensionRefresh", true);
        this.borninchaosNarrowMinionScansValue = builder
                .comment("Narrow the four Born in Chaos minion claim scans to the mod's own entities.")
                .define("narrowMinionScans", true);
        builder.pop();

        builder.comment("Blood Magic patches.").push("bloodmagic");
        this.bloodmagicCacheArcRecipeListValue = builder
                .comment("Stop rebuilding the whole ARC recipe list on every tick of every ARC.")
                .define("cacheArcRecipeList", true);
        this.bloodmagicCacheArcFurnaceRecipeValue = builder
                .comment("Give the ARC's furnace mode the recipe cache vanilla furnaces already have.")
                .define("cacheArcFurnaceRecipe", true);
        this.bloodmagicFastRoutingConnectivityValue = builder
                .comment("Give the item routing network's connectivity search a visited set that is actually a set.")
                .define("fastRoutingConnectivity", true);
        builder.pop();

        builder.comment("Animus patches.").push("animus");
        this.animusCacheEquivalencyPreviewValue = builder
                .comment("Stop rebuilding the equivalency sigil's block outline every single frame.")
                .define("cacheEquivalencyPreview", true);
        builder.pop();

        builder.comment("Patchouli patches.").push("patchouli");
        this.patchouliCacheBookItemLookupValue = builder
                .comment("Answer 'is this item one of the guide books' from a per item cache.")
                .define("cacheBookItemLookup", true);
        builder.pop();

        builder.comment("Structurify patches.").push("structurify");
        this.structurifyFastStructureSetLookupValue = builder
                .comment("Resolve Structurify's per structure set config lookup in one hash lookup.")
                .define("fastStructureSetLookup", true);
        this.structurifySkipDisabledStructureChecksValue = builder
                .comment("Skip Structurify's structure check bookkeeping when none of its checks are enabled.")
                .define("skipDisabledStructureChecks", true);
        this.structurifyLeanHeightCacheValue = builder
                .comment("Replace Structurify's terrain height cache with a primitive keyed one.")
                .define("leanHeightCache", true);
        this.structurifyLeanOverlapSectionsValue = builder
                .comment("Collect overlap check section keys without boxing them.")
                .define("leanOverlapSections", true);
        this.structurifyCacheStructureSetEntriesValue = builder
                .comment("Stop rebuilding a structure set's entry list on every read.")
                .define("cacheStructureSetEntries", true);
        this.structurifySkipStartCheckWrapValue = builder
                .comment("Restore vanilla's plain getStartForStructure while Structurify's checks are idle.")
                .define("skipStartCheckWrap", true);
        builder.pop();

        builder.comment("Bosses' Rises patches.").push("bossesrise");
        this.bossesriseNarrowCinematicScanValue = builder
                .comment("Only collect Bosses' Rises entities in its per player cinematic scan.")
                .define("narrowCinematicScan", true);
        builder.pop();

        builder.comment("Marium's Soulslike Weaponry patches.").push("soulsweapons");
        this.soulsweaponsLeanDespawnTimerValue = builder
                .comment("Read the mod's despawn timer without writing to every entity that does not have one.")
                .define("leanDespawnTimer", true);
        builder.pop();

        builder.comment("Kind of Nice Weapon patches.").push("konweapon");
        this.konweaponSkipItemAnimationCopiesValue = builder
                .comment("Bail out of the held item animation handler before it copies your hands.")
                .define("skipItemAnimationCopies", true);
        builder.pop();

        builder.comment("Immersive Aircraft patches.").push("immersiveaircraft");
        this.immersiveaircraftBatchOverlayValue = builder
                .comment("Batch the aircraft HUD instead of flushing the GUI buffer after every primitive.")
                .define("batchOverlay", true);
        builder.pop();

        builder.comment("FTB Chunks patches.").push("ftbchunks");
        this.ftbchunksSkipHiddenMinimapWorkValue = builder
                .comment("Stop building the minimap texture when the minimap is not being shown.")
                .define("skipHiddenMinimapWork", true);
        this.ftbchunksFastRegionWriteValue = builder
                .comment("Copy a map region's five images straight into their pixel arrays instead of making 1.3 million per pixel setRGB calls on the calling thread.")
                .define("fastRegionWrite", true);
        builder.pop();

        builder.comment("Punchy patches.").push("punchy");
        this.punchyCacheResourceStackMissesValue = builder
                .comment("Remember which resource paths no pack contains, so a repeated miss stops rescanning every loaded pack.")
                .define("cacheResourceStackMisses", true);
        builder.pop();

        builder.comment("L2 Hostility patches.").push("l2hostility");
        this.l2hostilitySkipTraitlessCapLookupValue = builder
                .comment("Stop re-asking entities that cannot have traits for their trait capability.")
                .define("skipTraitlessCapLookup", true);
        builder.pop();

        builder.comment("Ice and Fire patches.").push("iceandfire");
        this.iceandfireFastEntityDataLookupValue = builder
                .comment("Keep Ice and Fire's EntityData capability handle on the entity instead of in a boxed map.")
                .define("fastEntityDataLookup", true);
        this.iceandfireSkipPathDebugRenderValue = builder
                .comment("Stop rebuilding the pathfinding debug render context on every render stage.")
                .define("skipPathDebugRender", true);
        this.iceandfireSkipEmptyArmorLayerValue = builder
                .comment("Test dragon armour with the four slot ordinals instead of two built strings.")
                .define("skipEmptyArmorLayer", true);
        this.iceandfireCacheDragonTextureValue = builder
                .comment("Key the dragon layered-texture cache on a packed int instead of a built string.")
                .define("cacheDragonTexture", true);
        this.iceandfireSkipEmptyDragonLayersValue = builder
                .comment("Return early from the dragon banner and rider layers when they would draw nothing.")
                .define("skipEmptyDragonLayers", true);
        this.iceandfireLeanMultipartTickValue = builder
                .comment("Trim the per-tick work of dragon, sea serpent and death worm body parts.")
                .define("leanMultipartTick", true);
        this.iceandfireDragonTargetSearchHeightValue = builder
                .comment("Vertical half-extent, in blocks, of the dragon target search box. -1 keeps stock.")
                .defineInRange("dragonTargetSearchHeight", 32, -1, 2048);
        builder.pop();

        builder.comment("Ice and Fire dragon dens as structures. Ported from IAF Dragon Fix (MIT).").push("iafdragonfix");
        this.iafdragonfixStructureDensValue = builder
                .comment("Generate dragon roosts and caves as structures instead of as decoration features.")
                .define("structureDens", true);
        this.iafdragonfixRoostSpawnDistanceValue = builder
                .comment("Minimum distance in blocks from world spawn before a dragon roost will generate.")
                .defineInRange("roostSpawnDistance", 800, 0, 100000);
        this.iafdragonfixCaveSpawnDistanceValue = builder
                .comment("Minimum distance in blocks from world spawn before a dragon cave will generate.")
                .defineInRange("caveSpawnDistance", 800, 0, 100000);
        builder.pop();

        builder.comment("Mowzie's Mobs patches.").push("mowziesmobs");
        this.mowziesmobsFastCapabilityLookupValue = builder
                .comment("Keep Mowzie's Mobs' four capability handles on the entity instead of re-resolving them.")
                .define("fastCapabilityLookup", true);
        this.mowziesmobsDedupeCapabilityAttachValue = builder
                .comment("Stop Mowzie's Mobs attaching its capabilities to every entity twice.")
                .define("dedupeCapabilityAttach", true);
        this.mowziesmobsCacheCameraShakeScanValue = builder
                .comment("Reuse the camera shake entity scan within a client tick instead of once per frame.")
                .define("cacheCameraShakeScan", true);
        this.mowziesmobsBossMusicPacketIntervalValue = builder
                .comment("Ticks between boss music state packets. 1 keeps stock behaviour.")
                .defineInRange("bossMusicPacketInterval", 5, 1, 100);
        this.mowziesmobsLeanBoneLookupValue = builder
                .comment("Drop the throwaway Optional from Mowzie's per-frame bone lookups.")
                .define("leanBoneLookup", true);
        this.mowziesmobsHoistChainRenderMatrixValue = builder
                .comment("Compute the dynamic chain's render matrix once per chain instead of once per bone.")
                .define("hoistChainRenderMatrix", true);
        this.mowziesmobsDynamicChainSubstepCapValue = builder
                .comment("Maximum physics substeps per frame for dynamic chains. 0 disables the clamp.")
                .defineInRange("dynamicChainSubstepCap", 4, 0, 64);
        this.mowziesmobsCacheUmvuthanaLeaderValue = builder
                .comment("Stop every Umvuthana follower re-scanning a 64x64x64 region for its leader every tick.")
                .define("cacheUmvuthanaLeader", true);
        this.mowziesmobsLeanModelBoxVectorsValue = builder
                .comment("Reuse one vector pair per cube in Mowzie's llibrary model renderer instead of allocating 30 per cube per frame.")
                .define("leanModelBoxVectors", true);
        this.mowziesmobsSkipBlankElokosaTransformValue = builder
                .comment("Skip the Elokosa transformation layer's full model re-render while it is fully transparent.")
                .define("skipBlankElokosaTransform", true);
        this.mowziesmobsLeanLayerBoneScanValue = builder
                .comment("Test the bone name before pushing a matrix in the Umvuthana and Umvuthi render layers.")
                .define("leanLayerBoneScan", true);
        this.mowziesmobsCacheEffectRenderTypesValue = builder
                .comment("Build the sunstrike, solar beam and solar flare render types once instead of once per entity per frame.")
                .define("cacheEffectRenderTypes", true);
        builder.pop();

        builder.comment("Pick Up Notifier patches.").push("pickupnotifier");
        this.pickupnotifierSkipOpaqueSpriteBufferValue = builder
                .comment("Draw fully opaque pick-up sprites straight to the screen instead of routing every one through a window sized off-screen render target and a full screen blit.")
                .define("skipOpaqueSpriteBuffer", true);
        builder.pop();

        builder.comment("Placebo patches.").push("placebo");
        this.placeboSkipEmptyEnchantmentEventValue = builder
                .comment("Skip the HashMap, event object and event bus dispatch Placebo builds on every single ItemStack enchantment level lookup while nothing is listening to GetEnchantmentLevelEvent.")
                .define("skipEmptyEnchantmentEvent", true);
        builder.pop();

        builder.comment("Photon patches.").push("photon");
        this.photonLeanParticleQuadsValue = builder
                .comment("Emit billboard particle vertices from reusable scratch vectors instead of allocating around ten JOML objects per particle per frame.")
                .define("leanParticleQuads", true);
        this.photonLeanParticleLightValue = builder
                .comment("Reuse the particle's block position for the per tick light lookup while it stays inside the same block.")
                .define("leanParticleLight", true);
        this.photonLeanTrailVerticesValue = builder
                .comment("Build trail ribbons from float locals instead of allocating about ten vectors per trail segment per frame.")
                .define("leanTrailVertices", true);
        this.photonDropEmptyEffectCacheEntriesValue = builder
                .comment("Drop the emptied block effect cache entry instead of leaving one map entry and one empty list per block position for the rest of the session.")
                .define("dropEmptyEffectCacheEntries", true);
        builder.pop();

        builder.comment("Integrated API patches.").push("integratedapi");
        this.integratedapiSkipEmptyBeardifierValue = builder
                .comment("Return early from the enhanced terrain adaptation pass when there is none in range.")
                .define("skipEmptyBeardifier", true);
        builder.pop();

        builder.comment("Echelon patches.").push("echelon");
        this.echelonCacheTierAttributeUuidsValue = builder
                .comment("Stop recomputing an MD5 for every tier attribute modifier.")
                .define("cacheTierAttributeUuids", true);
        builder.pop();

        builder.comment("Elysium API patches.").push("elysiumapi");
        this.elysiumapiMemoClimateSampleValue = builder
                .comment("Answer a repeated climate sample for the same position from a one entry cache.")
                .define("memoClimateSample", true);
        this.elysiumapiSkipUnusedBiomeReplacerLookupValue = builder
                .comment("Skip Elysium API's duplicate biome resolution entirely when no biome replacer exists.")
                .define("skipUnusedBiomeReplacerLookup", true);
        builder.pop();

        builder.comment("Enigmatic Dice patches.").push("enigmaticdice");
        this.enigmaticdiceFastCurioMissValue = builder
                .comment("Answer the isWearing checks for the Moai Charm, Ring of Agility and Divine Shield from the per-tick curio set.")
                .define("fastCurioMiss", true);
        builder.pop();

        builder.comment("Balm patches.").push("balm");
        this.balmMemoDynamicModelKeysValue = builder
                .comment("Stop rebuilding a block state's toString on every quad request.")
                .define("memoDynamicModelKeys", true);
        builder.pop();

        builder.comment("Moonlight Lib patches.").push("moonlight");
        this.moonlightSkipEmptyMapMarkerScanValue = builder
                .comment("Skip Moonlight's custom map marker refresh on maps that have no markers.")
                .define("skipEmptyMapMarkerScan", true);
        builder.pop();

        builder.comment("Pehkui patches.").push("pehkui");
        this.pehkuiLeanScaleTickValue = builder
                .comment("Stop allocating two throwaway lambdas per scale type per entity per tick.")
                .define("leanScaleTick", true);
        this.pehkuiMemoInteractionBoxScalesValue = builder
                .comment("Work out an entity's interaction box scales once a tick instead of twice for every entity walked by every AABB query. A scale set part way through a tick is seen on the next one.")
                .define("memoInteractionBoxScales", true);
        builder.pop();

        builder.comment("Tons Of Enchants patches.").push("tonsofenchants");
        this.tonsofenchantsSkipAbsentAttributeRemovalValue = builder
                .comment("Stop every player broadcasting a pointless attribute sync packet every tick.")
                .define("skipAbsentAttributeRemoval", true);
        this.tonsofenchantsFrostbiteSkipClientValue = builder
                .comment("Skip the Frostbite entity scan on the client, where it cannot do anything.")
                .define("frostbiteSkipClient", true);
        this.tonsofenchantsLeanAttributeLookupValue = builder
                .comment("Look the attribute up once instead of three times.")
                .define("leanAttributeLookup", true);
        this.tonsofenchantsSinglePhasePlayerTickValue = builder
                .comment("Run each of the seventeen PlayerTickEvent listeners once a tick instead of twice.")
                .define("singlePhasePlayerTick", true);
        builder.pop();

        builder.comment("Subtle Effects patches.").push("subtleeffects");
        this.subtleeffectsFireflyDarknessGateValue = builder
                .comment("Test the firefly light condition before the biome lookup instead of after it.")
                .define("fireflyDarknessGate", true);
        this.subtleeffectsCapBiomeParticleScanValue = builder
                .comment("Stop scanning biome particle positions that no setting can spawn at.")
                .define("capBiomeParticleScan", true);
        this.subtleeffectsLeanTickerRemovalValue = builder
                .comment("Drain the ticker removal queue in one pass instead of one ArrayList#remove each.")
                .define("leanTickerRemoval", true);
        this.subtleeffectsGeyserBlockPreFilterValue = builder
                .comment("Skip the geyser scan on blocks no geyser type can spawn on.")
                .define("geyserBlockPreFilter", true);
        builder.pop();

        builder.comment("Ars Energistique patches.").push("arseng");
        this.arsengSkipDeadRelayListenersValue = builder
                .comment("Stop registering a capability listener that can never be observed.")
                .define("skipDeadRelayListeners", true);
        this.arsengGateGenericInvWrapperValue = builder
                .comment("Stop attaching the SOURCE_TILE wrapper to block entities that cannot back it.")
                .define("gateGenericInvWrapper", true);
        builder.pop();

        builder.comment("Perception patches.").push("perception");
        this.perceptionShareDefaultTrailDataValue = builder
                .comment("Stop allocating a throwaway trail config for every entity and every particle.")
                .define("shareDefaultTrailData", true);
        builder.pop();

        builder.comment("Quark patches.").push("quark");
        this.quarkSkipPigLitterTagChurnValue = builder
                .comment("Stop attaching a persistent data compound to every animal in the world.")
                .define("skipPigLitterTagChurn", true);
        builder.pop();

        builder.comment("Zeta patches. Zeta is Quark's module and event framework.").push("zeta");
        this.zetaLeanStructureReplacementValue = builder
                .comment("Stop allocating an iterator for every block every structure places.")
                .define("leanStructureReplacement", true);
        this.zetaShareEventWrappersValue = builder
                .comment("Build one Zeta event wrapper per dispatch instead of one per listener.")
                .define("shareEventWrappers", true);
        builder.pop();

        builder.comment("Dungeon Crawl patches.").push("dungeoncrawl");
        this.dungeoncrawlSkipBlockEntityProbeValue = builder
                .comment("Stop asking for a block entity after placing a block that cannot have one.")
                .define("skipBlockEntityProbe", true);
        builder.pop();

        builder.comment("Goety patches.").push("goety");
        this.goetyCacheCapabilityOptionalValue = builder
                .comment("Reuse the LazyOptional Goety's capability providers build.")
                .define("cacheCapabilityOptional", true);
        this.goetySkipCapabilityFallbackValue = builder
                .comment("Stop building the throwaway capability fallback on every query.")
                .define("skipCapabilityFallback", true);
        this.goetyMemoAttributeModifiersValue = builder
                .comment("Memoise the constant attribute modifiers Goety rebuilds every tick.")
                .define("memoAttributeModifiers", true);
        this.goetyFastEmptyAllyCheckValue = builder
                .comment("Answer SEHelper#isAlly without resolving anything when the player has no allies.")
                .define("fastEmptyAllyCheck", true);
        this.goetyFastCurioItemMissValue = builder
                .comment("Answer CuriosFinder#findCurio(LivingEntity, Item) misses from the per-tick curio set.")
                .define("fastCurioItemMiss", true);
        this.goetySkipBossMusicTargetLookupValue = builder
                .comment("Skip the boss music target lookup for entities that have no boss music. Client.")
                .define("skipBossMusicTargetLookup", true);
        this.goetyCacheFogWightScanValue = builder
                .comment("Run the fog listener's Wight#findWight scan once per tick instead of once per posted RenderFog event. Client.")
                .define("cacheFogWightScan", true);
        builder.pop();

        builder.comment("Myths and Legends patches.").push("mythsandlegends");
        this.mythsandlegendsCacheFogBossScanValue = builder
                .comment("Run the boss scan behind the fog and fog colour listeners once per tick instead of once per posted event. Client.")
                .define("cacheFogBossScan", true);
        this.mythsandlegendsCacheShakeScanValue = builder
                .comment("Run the screen shake entity scan once per tick instead of once per frame. Client.")
                .define("cacheShakeScan", true);
        builder.pop();

        builder.comment("AmbientSounds patches.").push("ambientsounds");
        this.ambientsoundsMemoBiomeMatchValue = builder
                .comment("Work out whether a biome matches a region's biome patterns once instead of running the regex every client tick. Client.")
                .define("memoBiomeMatch", true);
        builder.pop();

        builder.comment("Ars Nouveau patches.").push("arsnouveau");
        this.arsnouveauSkyTextureIntervalValue = builder
                .comment("Ticks between refreshes of the offscreen sky texture, which costs a second full sky, cloud and weather pass plus a fog event post every frame. 1 refreshes once a tick, 0 restores the stock every frame behaviour. Client.")
                .defineInRange("skyTextureInterval", 1, 0, 200);
        builder.pop();

        builder.comment("Goety Revelation patches.").push("goetyrevelation");
        this.goetyrevelationCacheHaloLookupValue = builder
                .comment("Answer ATAHelper#hasHalo and #hasBrokenHalo from the per-tick curio set.")
                .define("cacheHaloLookup", true);
        builder.pop();

        builder.comment("RevelationFix patches. RevelationFix ships jar-in-jar inside Goety Revelation.")
                .push("revelationfix");
        this.revelationfixSkipMobFluidStandScanValue = builder
                .comment("Skip the walk-on-fluid probe for entities that can never walk on fluid.")
                .define("skipMobFluidStandScan", true);
        this.revelationfixSkipNonSpiderHurtByTargetEventsValue = builder
                .comment("Stop posting RevelationFix's hurt-by-target events for mobs that ignore them.")
                .define("skipNonSpiderHurtByTargetEvents", true);
        builder.pop();

        builder.comment("Macabre patches.").push("macabre");
        this.macabreSkipForeignEntityAnimationsValue = builder
                .comment("Skip Macabre's entity animation handler for entities that are not Macabre's.")
                .define("skipForeignEntityAnimations", true);
        this.macabreSkipItemAnimationCopiesValue = builder
                .comment("Bail out of Macabre's held item animation handler before it copies your hands.")
                .define("skipItemAnimationCopies", true);
        this.macabreCoalesceVariableSyncValue = builder
                .comment("Send Macabre's variable sync packets once per tick instead of once per assignment.")
                .define("coalesceVariableSync", true);
        builder.pop();

        builder.comment("Alex's Mobs patches.").push("alexsmobs");
        this.alexsmobsSkipCreeperAvoidGoalsValue = builder
                .comment("Stop attaching the snow leopard and tiger avoidance goals to every creeper.")
                .define("skipCreeperAvoidGoals", true);
        this.alexsmobsSpiderFlyScanIntervalValue = builder
                .comment("How often a vanilla spider may scan for Alex's Mobs flies, in goal evaluations.")
                .defineInRange("spiderFlyScanInterval", 10, 1, 200);
        this.alexsmobsReleaseLevelMapsValue = builder
                .comment("Release the level keyed maps Alex's Mobs never clears, when a server stops.")
                .define("releaseLevelMaps", true);
        builder.pop();

        builder.comment("Alex's Caves patches.").push("alexscaves");
        this.alexscavesMemoRareBiomeQuadsValue = builder
                .comment("Stop recomputing Alex's Caves' cave biome placement 96 times per column.")
                .define("memoRareBiomeQuads", true);
        this.alexscavesMemoClimateSampleValue = builder
                .comment("Answer Alex's Caves' duplicate climate sample from a one entry cache.")
                .define("memoClimateSample", true);
        this.alexscavesCacheShakeScanValue = builder
                .comment("Look for screen shaking mobs once per tick instead of once per frame.")
                .define("cacheShakeScan", true);
        builder.pop();

        builder.comment("Ad Astra patches.").push("adastra");
        this.adastraMemoPlanetDefaultsValue = builder
                .comment("Memoise Ad Astra's per-dimension gravity and temperature constants.")
                .define("memoPlanetDefaults", true);
        builder.pop();

        builder.comment("Vanilla patches. These are the only patches here that are not aimed at a specific mod.").push("vanilla");
        this.itemEntityRenderCapValue = builder
                .comment("Maximum times a dropped item stack's model is drawn.")
                .defineInRange("itemEntityRenderCap", 1, 0, 5);
        this.vanillaFasterStructureLocationValue = builder
                .comment("Makes /locate a lot faster, especially for rare structures.")
                .define("fasterStructureLocation", true);
        this.vanillaFixBoatFallDamageValue = builder
                .comment("Stops boats breaking into planks when you ride them off a drop.")
                .define("fixBoatFallDamage", false);
        this.vanillaPredictableItemDropsValue = builder
                .comment("Drops from broken blocks land dead centre instead of scattering.")
                .define("predictableItemDrops", false);
        this.vanillaLeanTrackerSectionPosValue = builder
                .comment("Stop the entity tracker allocating a SectionPos for every tracked entity every tick.")
                .define("leanTrackerSectionPos", true);
        this.vanillaLeanSuffocationScanValue = builder
                .comment("Do the suffocation check with a loop instead of a Java stream.")
                .define("leanSuffocationScan", true);
        this.vanillaLeanMenuBroadcastValue = builder
                .comment("Stop the container sync allocating a memoizing supplier for every slot every tick.")
                .define("leanMenuBroadcast", true);
        this.vanillaLeanTrackerDeltaValue = builder
                .comment("Stop the entity tracker allocating a movement vector for entities that did not move.")
                .define("leanTrackerDelta", true);
        this.vanillaCacheBiomeQuartLookupsValue = builder
                .comment("Answer repeated biome lookups on the render thread from a small per tick cache. Fog and sky colour sample 27 biomes per call, several times a frame.")
                .define("cacheBiomeQuartLookups", true);
        this.vanillaMemoCameraFluidValue = builder
                .comment("Work out what fluid the camera is in once per camera position instead of once per caller.")
                .define("memoCameraFluid", true);
        builder.pop();
    }

    public static void onLoad(ModConfigEvent.Loading event) {
        bake();
    }

    public static void onReload(ModConfigEvent.Reloading event) {
        bake();
    }

    private static void bake() {
        if (!SPEC.isLoaded()) {
            return;
        }
        masterEnabled = VALUES.masterEnabledValue.get();
        curiosSkipSlotlessEntities = masterEnabled && VALUES.curiosSkipSlotlessEntitiesValue.get();
        curiosSkipClientTickOnNonPlayers = masterEnabled && VALUES.curiosSkipClientTickOnNonPlayersValue.get();
        curiosSkipNonPlayerRenderLayer = masterEnabled && VALUES.curiosSkipNonPlayerRenderLayerValue.get();
        curiosCacheEntitySlotLookup = masterEnabled && VALUES.curiosCacheEntitySlotLookupValue.get();
        curiosFastEquippedItemMiss = masterEnabled && VALUES.curiosFastEquippedItemMissValue.get();
        artifactsSkipClientTickOnNonPlayers = masterEnabled && VALUES.artifactsSkipClientTickOnNonPlayersValue.get();
        artifactsFastPathKittySlippers = masterEnabled && VALUES.artifactsFastPathKittySlippersValue.get();
        artifactsFastPathUmbrella = masterEnabled && VALUES.artifactsFastPathUmbrellaValue.get();
        caelusSkipGroundedNonPlayers = masterEnabled && VALUES.caelusSkipGroundedNonPlayersValue.get();
        blockswapPaletteFilteredRetroGen = masterEnabled && VALUES.blockswapPaletteFilteredRetroGenValue.get();
        justdirethingsAvoidChunkTickets = masterEnabled && VALUES.justdirethingsAvoidChunkTicketsValue.get();
        goetydelightCakeScanInterval = masterEnabled ? VALUES.goetydelightCakeScanIntervalValue.get() : 1;
        geckolibReuseRenderVectors = masterEnabled && VALUES.geckolibReuseRenderVectorsValue.get();
        geckolibCacheBoneLookup = masterEnabled && VALUES.geckolibCacheBoneLookupValue.get();
        saintsdragonsSkipRedundantBoneTracking = masterEnabled && VALUES.saintsdragonsSkipRedundantBoneTrackingValue.get();
        saintsdragonsCacheShakeScan = masterEnabled && VALUES.saintsdragonsCacheShakeScanValue.get();
        immediatelyfastSingleBufferLookup = masterEnabled && VALUES.immediatelyfastSingleBufferLookupValue.get();
        fancymenuSeamlessCaptureInterval = masterEnabled ? VALUES.fancymenuSeamlessCaptureIntervalValue.get() : 1;
        emfDropZeroAngerEntries = masterEnabled && VALUES.emfDropZeroAngerEntriesValue.get();
        etfFastValidPath = masterEnabled && VALUES.etfFastValidPathValue.get();
        oculusSkipSignTextInShadowPass = masterEnabled && VALUES.oculusSkipSignTextInShadowPassValue.get();
        oculusSkipGlintInShadowPass = masterEnabled && VALUES.oculusSkipGlintInShadowPassValue.get();
        oculusSkipNameTagsInShadowPass = masterEnabled && VALUES.oculusSkipNameTagsInShadowPassValue.get();
        oculusSkipBannerPatternsInShadowPass = masterEnabled && VALUES.oculusSkipBannerPatternsInShadowPassValue.get();
        lootrSkipIdleTileTicker = masterEnabled && VALUES.lootrSkipIdleTileTickerValue.get();
        lootrTileTickerBudget = masterEnabled ? VALUES.lootrTileTickerBudgetValue.get() : 0;
        naturesauraFastAuraChunkSweep = masterEnabled && VALUES.naturesauraFastAuraChunkSweepValue.get();
        xaeroMinimapRenderFpsCap = masterEnabled ? VALUES.xaeroMinimapRenderFpsCapValue.get() : 0;
        w2w2DeferWaypointSave = masterEnabled && VALUES.w2w2DeferWaypointSaveValue.get();
        terrablenderCacheNamespaceRule = masterEnabled && VALUES.terrablenderCacheNamespaceRuleValue.get();
        terramitySkipItemAnimationCopies = masterEnabled && VALUES.terramitySkipItemAnimationCopiesValue.get();
        terramitySkipForeignEntityAnimations = masterEnabled && VALUES.terramitySkipForeignEntityAnimationsValue.get();
        terramityMemoizeProcedureRaycasts = masterEnabled && VALUES.terramityMemoizeProcedureRaycastsValue.get();
        terramitySkipClientCurioScans = masterEnabled && VALUES.terramitySkipClientCurioScansValue.get();
        terramitySkipArmorAnimationScan = masterEnabled && VALUES.terramitySkipArmorAnimationScanValue.get();
        terramityFixPhasingShaderStomp = masterEnabled && VALUES.terramityFixPhasingShaderStompValue.get();
        armageddonSkipForeignEntityAnimations = masterEnabled && VALUES.armageddonSkipForeignEntityAnimationsValue.get();
        armageddonCacheProgressionIds = masterEnabled && VALUES.armageddonCacheProgressionIdsValue.get();
        borninchaosSkipItemAnimationCopies = masterEnabled && VALUES.borninchaosSkipItemAnimationCopiesValue.get();
        borninchaosSkipForeignEntityAnimations = masterEnabled && VALUES.borninchaosSkipForeignEntityAnimationsValue.get();
        borninchaosSkipRedundantDimensionRefresh = masterEnabled && VALUES.borninchaosSkipRedundantDimensionRefreshValue.get();
        borninchaosNarrowMinionScans = masterEnabled && VALUES.borninchaosNarrowMinionScansValue.get();
        bloodmagicCacheArcRecipeList = masterEnabled && VALUES.bloodmagicCacheArcRecipeListValue.get();
        bloodmagicCacheArcFurnaceRecipe = masterEnabled && VALUES.bloodmagicCacheArcFurnaceRecipeValue.get();
        bloodmagicFastRoutingConnectivity = masterEnabled && VALUES.bloodmagicFastRoutingConnectivityValue.get();
        animusCacheEquivalencyPreview = masterEnabled && VALUES.animusCacheEquivalencyPreviewValue.get();
        patchouliCacheBookItemLookup = masterEnabled && VALUES.patchouliCacheBookItemLookupValue.get();
        structurifyFastStructureSetLookup = masterEnabled && VALUES.structurifyFastStructureSetLookupValue.get();
        structurifySkipDisabledStructureChecks = masterEnabled && VALUES.structurifySkipDisabledStructureChecksValue.get();
        structurifyLeanHeightCache = masterEnabled && VALUES.structurifyLeanHeightCacheValue.get();
        structurifyLeanOverlapSections = masterEnabled && VALUES.structurifyLeanOverlapSectionsValue.get();
        structurifyCacheStructureSetEntries = masterEnabled && VALUES.structurifyCacheStructureSetEntriesValue.get();
        structurifySkipStartCheckWrap = masterEnabled && VALUES.structurifySkipStartCheckWrapValue.get();
        bossesriseNarrowCinematicScan = masterEnabled && VALUES.bossesriseNarrowCinematicScanValue.get();
        soulsweaponsLeanDespawnTimer = masterEnabled && VALUES.soulsweaponsLeanDespawnTimerValue.get();
        konweaponSkipItemAnimationCopies = masterEnabled && VALUES.konweaponSkipItemAnimationCopiesValue.get();
        immersiveaircraftBatchOverlay = masterEnabled && VALUES.immersiveaircraftBatchOverlayValue.get();
        ftbchunksSkipHiddenMinimapWork = masterEnabled && VALUES.ftbchunksSkipHiddenMinimapWorkValue.get();
        ftbchunksFastRegionWrite = masterEnabled && VALUES.ftbchunksFastRegionWriteValue.get();
        punchyCacheResourceStackMisses = masterEnabled && VALUES.punchyCacheResourceStackMissesValue.get();
        l2hostilitySkipTraitlessCapLookup = masterEnabled && VALUES.l2hostilitySkipTraitlessCapLookupValue.get();
        iceandfireFastEntityDataLookup = masterEnabled && VALUES.iceandfireFastEntityDataLookupValue.get();
        iceandfireSkipPathDebugRender = masterEnabled && VALUES.iceandfireSkipPathDebugRenderValue.get();
        iceandfireSkipEmptyArmorLayer = masterEnabled && VALUES.iceandfireSkipEmptyArmorLayerValue.get();
        iceandfireCacheDragonTexture = masterEnabled && VALUES.iceandfireCacheDragonTextureValue.get();
        iceandfireSkipEmptyDragonLayers = masterEnabled && VALUES.iceandfireSkipEmptyDragonLayersValue.get();
        iceandfireLeanMultipartTick = masterEnabled && VALUES.iceandfireLeanMultipartTickValue.get();
        iceandfireDragonTargetSearchHeight = masterEnabled ? VALUES.iceandfireDragonTargetSearchHeightValue.get() : -1;
        iafdragonfixStructureDens = masterEnabled && VALUES.iafdragonfixStructureDensValue.get();
        iafdragonfixRoostSpawnDistance = VALUES.iafdragonfixRoostSpawnDistanceValue.get();
        iafdragonfixCaveSpawnDistance = VALUES.iafdragonfixCaveSpawnDistanceValue.get();
        mowziesmobsFastCapabilityLookup = masterEnabled && VALUES.mowziesmobsFastCapabilityLookupValue.get();
        mowziesmobsDedupeCapabilityAttach = masterEnabled && VALUES.mowziesmobsDedupeCapabilityAttachValue.get();
        mowziesmobsCacheCameraShakeScan = masterEnabled && VALUES.mowziesmobsCacheCameraShakeScanValue.get();
        mowziesmobsBossMusicPacketInterval = masterEnabled ? VALUES.mowziesmobsBossMusicPacketIntervalValue.get() : 1;
        mowziesmobsLeanBoneLookup = masterEnabled && VALUES.mowziesmobsLeanBoneLookupValue.get();
        mowziesmobsHoistChainRenderMatrix = masterEnabled && VALUES.mowziesmobsHoistChainRenderMatrixValue.get();
        mowziesmobsDynamicChainSubstepCap = masterEnabled ? VALUES.mowziesmobsDynamicChainSubstepCapValue.get() : 0;
        mowziesmobsCacheUmvuthanaLeader = masterEnabled && VALUES.mowziesmobsCacheUmvuthanaLeaderValue.get();
        mowziesmobsLeanModelBoxVectors = masterEnabled && VALUES.mowziesmobsLeanModelBoxVectorsValue.get();
        mowziesmobsSkipBlankElokosaTransform = masterEnabled && VALUES.mowziesmobsSkipBlankElokosaTransformValue.get();
        mowziesmobsLeanLayerBoneScan = masterEnabled && VALUES.mowziesmobsLeanLayerBoneScanValue.get();
        mowziesmobsCacheEffectRenderTypes = masterEnabled && VALUES.mowziesmobsCacheEffectRenderTypesValue.get();
        pickupnotifierSkipOpaqueSpriteBuffer = masterEnabled && VALUES.pickupnotifierSkipOpaqueSpriteBufferValue.get();
        placeboSkipEmptyEnchantmentEvent = masterEnabled && VALUES.placeboSkipEmptyEnchantmentEventValue.get();
        photonLeanParticleQuads = masterEnabled && VALUES.photonLeanParticleQuadsValue.get();
        photonLeanParticleLight = masterEnabled && VALUES.photonLeanParticleLightValue.get();
        photonLeanTrailVertices = masterEnabled && VALUES.photonLeanTrailVerticesValue.get();
        photonDropEmptyEffectCacheEntries = masterEnabled && VALUES.photonDropEmptyEffectCacheEntriesValue.get();
        integratedapiSkipEmptyBeardifier = masterEnabled && VALUES.integratedapiSkipEmptyBeardifierValue.get();
        echelonCacheTierAttributeUuids = masterEnabled && VALUES.echelonCacheTierAttributeUuidsValue.get();
        elysiumapiMemoClimateSample = masterEnabled && VALUES.elysiumapiMemoClimateSampleValue.get();
        elysiumapiSkipUnusedBiomeReplacerLookup = masterEnabled && VALUES.elysiumapiSkipUnusedBiomeReplacerLookupValue.get();
        enigmaticdiceFastCurioMiss = masterEnabled && VALUES.enigmaticdiceFastCurioMissValue.get();
        balmMemoDynamicModelKeys = masterEnabled && VALUES.balmMemoDynamicModelKeysValue.get();
        dungeoncrawlSkipBlockEntityProbe = masterEnabled && VALUES.dungeoncrawlSkipBlockEntityProbeValue.get();
        moonlightSkipEmptyMapMarkerScan = masterEnabled && VALUES.moonlightSkipEmptyMapMarkerScanValue.get();
        pehkuiLeanScaleTick = masterEnabled && VALUES.pehkuiLeanScaleTickValue.get();
        tonsofenchantsSkipAbsentAttributeRemoval = masterEnabled && VALUES.tonsofenchantsSkipAbsentAttributeRemovalValue.get();
        tonsofenchantsFrostbiteSkipClient = masterEnabled && VALUES.tonsofenchantsFrostbiteSkipClientValue.get();
        tonsofenchantsLeanAttributeLookup = masterEnabled && VALUES.tonsofenchantsLeanAttributeLookupValue.get();
        tonsofenchantsSinglePhasePlayerTick = masterEnabled && VALUES.tonsofenchantsSinglePhasePlayerTickValue.get();
        subtleeffectsFireflyDarknessGate = masterEnabled && VALUES.subtleeffectsFireflyDarknessGateValue.get();
        subtleeffectsCapBiomeParticleScan = masterEnabled && VALUES.subtleeffectsCapBiomeParticleScanValue.get();
        subtleeffectsLeanTickerRemoval = masterEnabled && VALUES.subtleeffectsLeanTickerRemovalValue.get();
        subtleeffectsGeyserBlockPreFilter = masterEnabled && VALUES.subtleeffectsGeyserBlockPreFilterValue.get();
        arsengSkipDeadRelayListeners = masterEnabled && VALUES.arsengSkipDeadRelayListenersValue.get();
        arsengGateGenericInvWrapper = masterEnabled && VALUES.arsengGateGenericInvWrapperValue.get();

        perceptionShareDefaultTrailData = masterEnabled && VALUES.perceptionShareDefaultTrailDataValue.get();
        quarkSkipPigLitterTagChurn = masterEnabled && VALUES.quarkSkipPigLitterTagChurnValue.get();
        zetaLeanStructureReplacement = masterEnabled && VALUES.zetaLeanStructureReplacementValue.get();
        zetaShareEventWrappers = masterEnabled && VALUES.zetaShareEventWrappersValue.get();
        goetyCacheCapabilityOptional = masterEnabled && VALUES.goetyCacheCapabilityOptionalValue.get();
        goetySkipCapabilityFallback = masterEnabled && VALUES.goetySkipCapabilityFallbackValue.get();
        goetyMemoAttributeModifiers = masterEnabled && VALUES.goetyMemoAttributeModifiersValue.get();
        goetyFastEmptyAllyCheck = masterEnabled && VALUES.goetyFastEmptyAllyCheckValue.get();
        goetyFastCurioItemMiss = masterEnabled && VALUES.goetyFastCurioItemMissValue.get();
        goetySkipBossMusicTargetLookup = masterEnabled && VALUES.goetySkipBossMusicTargetLookupValue.get();
        goetyCacheFogWightScan = masterEnabled && VALUES.goetyCacheFogWightScanValue.get();
        mythsandlegendsCacheFogBossScan = masterEnabled && VALUES.mythsandlegendsCacheFogBossScanValue.get();
        mythsandlegendsCacheShakeScan = masterEnabled && VALUES.mythsandlegendsCacheShakeScanValue.get();
        ambientsoundsMemoBiomeMatch = masterEnabled && VALUES.ambientsoundsMemoBiomeMatchValue.get();
        arsnouveauSkyTextureInterval = masterEnabled ? VALUES.arsnouveauSkyTextureIntervalValue.get() : 0;
        pehkuiMemoInteractionBoxScales = masterEnabled && VALUES.pehkuiMemoInteractionBoxScalesValue.get();
        goetyrevelationCacheHaloLookup = masterEnabled && VALUES.goetyrevelationCacheHaloLookupValue.get();
        revelationfixSkipMobFluidStandScan = masterEnabled && VALUES.revelationfixSkipMobFluidStandScanValue.get();
        revelationfixSkipNonSpiderHurtByTargetEvents = masterEnabled && VALUES.revelationfixSkipNonSpiderHurtByTargetEventsValue.get();
        macabreSkipForeignEntityAnimations = masterEnabled && VALUES.macabreSkipForeignEntityAnimationsValue.get();
        macabreSkipItemAnimationCopies = masterEnabled && VALUES.macabreSkipItemAnimationCopiesValue.get();
        macabreCoalesceVariableSync = masterEnabled && VALUES.macabreCoalesceVariableSyncValue.get();
        alexsmobsSkipCreeperAvoidGoals = masterEnabled && VALUES.alexsmobsSkipCreeperAvoidGoalsValue.get();
        alexsmobsSpiderFlyScanInterval = masterEnabled ? VALUES.alexsmobsSpiderFlyScanIntervalValue.get() : 1;
        alexsmobsReleaseLevelMaps = masterEnabled && VALUES.alexsmobsReleaseLevelMapsValue.get();
        alexscavesMemoRareBiomeQuads = masterEnabled && VALUES.alexscavesMemoRareBiomeQuadsValue.get();
        alexscavesMemoClimateSample = masterEnabled && VALUES.alexscavesMemoClimateSampleValue.get();
        alexscavesCacheShakeScan = masterEnabled && VALUES.alexscavesCacheShakeScanValue.get();
        adastraMemoPlanetDefaults = masterEnabled && VALUES.adastraMemoPlanetDefaultsValue.get();
        itemEntityRenderCap = masterEnabled ? VALUES.itemEntityRenderCapValue.get() : 0;
        vanillaFasterStructureLocation = masterEnabled && VALUES.vanillaFasterStructureLocationValue.get();
        vanillaFixBoatFallDamage = masterEnabled && VALUES.vanillaFixBoatFallDamageValue.get();
        vanillaPredictableItemDrops = masterEnabled && VALUES.vanillaPredictableItemDropsValue.get();
        vanillaLeanTrackerSectionPos = masterEnabled && VALUES.vanillaLeanTrackerSectionPosValue.get();
        vanillaLeanSuffocationScan = masterEnabled && VALUES.vanillaLeanSuffocationScanValue.get();
        vanillaLeanMenuBroadcast = masterEnabled && VALUES.vanillaLeanMenuBroadcastValue.get();
        vanillaLeanTrackerDelta = masterEnabled && VALUES.vanillaLeanTrackerDeltaValue.get();
        vanillaCacheBiomeQuartLookups = masterEnabled && VALUES.vanillaCacheBiomeQuartLookupsValue.get();
        vanillaMemoCameraFluid = masterEnabled && VALUES.vanillaMemoCameraFluidValue.get();
    }
}
