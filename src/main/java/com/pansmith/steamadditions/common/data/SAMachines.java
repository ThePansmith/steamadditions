package com.pansmith.steamadditions.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.renderer.machine.LargeBoilerRenderer;
import com.gregtechceu.gtceu.common.block.BoilerFireboxType;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.SteamParallelMultiblockMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_BRONZE_BRICKS;
import static com.gregtechceu.gtceu.common.data.GTBlocks.FIREBOX_BRONZE;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;
import static com.pansmith.steamadditions.api.registries.SARegistries.REGISTRATE;

@SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded", "unused", "DataFlowIssue"})
public class SAMachines {
    public final static int[] ELECTRIC_TIERS = GTValues.tiersBetween(LV, GTCEuAPI.isHighTier() ? OpV : UV);
    public final static int[] LOW_TIERS = GTValues.tiersBetween(LV, EV);
    public final static int[] HIGH_TIERS = GTValues.tiersBetween(IV, GTCEuAPI.isHighTier() ? OpV : UHV);
    public static final Int2IntFunction defaultTankSizeFunction = tier -> (tier <= GTValues.LV ? 8 : tier == GTValues.MV ? 12 : tier == GTValues.HV ? 16 : tier == GTValues.EV ? 32 : 64) * FluidType.BUCKET_VOLUME;

    public static final MachineDefinition STEAM_CENTRIFUGE = REGISTRATE.multiblock("steam_separator", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.CENTRIFUGE_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
//            .addOutputLimit(ItemRecipeCapability.CAP, 1) Balance is for cowards
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX", "XXX", " X ")
                    .aisle("XXX", "X#X", "XXX")
                    .aisle("XXX", "XSX", " X ")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where('#', Predicates.air())
                    .where(' ', Predicates.any())
                    .where('X', blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(6)
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"),

                    GTCEu.id("block/machines/centrifuge"), false)
            .tooltips(Component.translatable("block.steamadditions.steam_separator.tooltip"))
            .register();

    public static final MachineDefinition STEAM_ALLOY_SMELTER = REGISTRATE.multiblock("steam_foundry", SteamParallelMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(CASING_BRONZE_BRICKS)
            .recipeType(GTRecipeTypes.ALLOY_SMELTER_RECIPES)
            .recipeModifier(SteamParallelMultiblockMachine::recipeModifier, true)
            .addOutputLimit(ItemRecipeCapability.CAP, 1)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("FFF", "XXX", "XXX")
                    .aisle("FFF", "X#X", "XXX")
                    .aisle("FFF", "XSX", "XXX")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where('F', blocks(FIREBOX_BRONZE.get())
                        .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1)))
                    .where('#', Predicates.air())
                    .where(' ', Predicates.any())
                    .where('X', blocks(CASING_BRONZE_BRICKS.get()).setMinGlobalLimited(6)
                            .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setPreviewCount(1)))
                    .build())
            .renderer(() -> new LargeBoilerRenderer(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"), BoilerFireboxType.BRONZE_FIREBOX,
                    GTCEu.id("block/machines/alloy_smelter")))
            .tooltips(Component.translatable("block.steamadditions.steam_foundry.tooltip"))
            .register();


    public static MachineDefinition[] registerSimpleMachines(String name,
                                                             GTRecipeType recipeType,
                                                             Int2IntFunction tankScalingFunction,
                                                             int... tiers) {
        return registerTieredMachines(name, (holder, tier) -> new SimpleTieredMachine(holder, tier, tankScalingFunction), (tier, builder) -> builder
                .langValue("%s %s %s".formatted(VLVH[tier], toEnglishName(name), VLVT[tier]))
                .editableUI(SimpleTieredMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id(name), recipeType))
                .rotationState(RotationState.NON_Y_AXIS)
                .recipeType(recipeType)
                .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
                .workableTieredHullRenderer(GTCEu.id("block/machines/" + name))
                .tooltips(workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, recipeType, tankScalingFunction.apply(tier), true))
                .compassNode(name)
                .register(), tiers);
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType, Int2IntFunction tankScalingFunction) {
        return registerSimpleMachines(name, recipeType, tankScalingFunction, ELECTRIC_TIERS);
    }

    public static MachineDefinition[] registerSimpleMachines(String name, GTRecipeType recipeType) {
        return registerSimpleMachines(name, recipeType, defaultTankSizeFunction);
    }
    
    public static MachineDefinition[] registerTieredMachines(String name,
                                                             BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder,
                                                             int... tiers) {
        MachineDefinition[] definitions = new MachineDefinition[tiers.length];
        for (int i = 0; i < tiers.length; i++) {
            int tier = tiers[i];
            var register =  REGISTRATE.machine(GTValues.VN[tier].toLowerCase() + "_" + name, holder -> factory.apply(holder, tier))
                    .tier(tier);
            definitions[i] = builder.apply(tier, register);
        }
        return definitions;
    }

    public static Component explosion() {
        if (ConfigHolder.INSTANCE.machines.doTerrainExplosion)
            return Component.translatable("gtceu.universal.tooltip.terrain_resist");
        return null;
    }

    public static Component[] workableTiered(int tier, long voltage, long energyCapacity, GTRecipeType recipeType, long tankCapacity, boolean input) {
        List<Component> tooltipComponents = new ArrayList<>();
        tooltipComponents.add(input ? Component.translatable("gtceu.universal.tooltip.voltage_in", voltage, GTValues.VNF[tier]) :
                Component.translatable("gtceu.universal.tooltip.voltage_out", voltage, GTValues.VNF[tier]));
        tooltipComponents.add(Component.translatable("gtceu.universal.tooltip.energy_storage_capacity", energyCapacity));
        if (recipeType.getMaxInputs(FluidRecipeCapability.CAP) > 0 || recipeType.getMaxOutputs(FluidRecipeCapability.CAP) > 0)
            tooltipComponents.add(Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity", tankCapacity));
        return tooltipComponents.toArray(Component[]::new);
    }

    public static void init() {

    }
}
