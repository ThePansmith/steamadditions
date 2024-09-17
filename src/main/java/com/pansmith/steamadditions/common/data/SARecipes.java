package com.pansmith.steamadditions.common.data;

import com.pansmith.steamadditions.data.recipe.*;
import net.minecraft.data.recipes.FinishedRecipe;
import com.pansmith.steamadditions.data.recipe.MiscRecipes;

import java.util.function.Consumer;

public class SARecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        MiscRecipes.init(provider);
    }
}
