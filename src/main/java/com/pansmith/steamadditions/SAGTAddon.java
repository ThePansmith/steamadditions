package com.pansmith.steamadditions;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.addon.events.MaterialCasingCollectionEvent;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.pansmith.steamadditions.common.data.SARecipes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class SAGTAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return steamadditions.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
    }

    @Override
    public String addonModId() {
        return steamadditions.MOD_ID;
    }
    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        SARecipes.init(provider);
    }
}