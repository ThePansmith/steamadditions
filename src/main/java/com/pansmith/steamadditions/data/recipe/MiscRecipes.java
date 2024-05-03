package com.pansmith.steamadditions.data.recipe;

import com.pansmith.steamadditions.steamadditions;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class MiscRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, steamadditions.id("steam"),
                com.pansmith.steamadditions.common.data.SAMachines.STEAM_CENTRIFUGE.asStack(1),
                "PGP", "PTP", "PGP",
                'P', GTBlocks.CASING_BRONZE_BRICKS,
                'G', new UnificationEntry(gear, Steel),
                'T', new UnificationEntry(rotor, GTMaterials.Tin));

        VanillaRecipeHelper.addShapedRecipe(provider, true, steamadditions.id("steam2"),
                com.pansmith.steamadditions.common.data.SAMachines.STEAM_ALLOY_SMELTER.asStack(1),
                "PGP", "PTP", "PGP",
                'P', GTBlocks.CASING_BRONZE_BRICKS,
                'G', new UnificationEntry(gear, Bronze),
                'T', GTMachines.STEAM_ALLOY_SMELTER.left().asStack());

}}
