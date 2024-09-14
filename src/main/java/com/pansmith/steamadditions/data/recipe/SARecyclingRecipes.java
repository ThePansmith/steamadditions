import com.gregtechceu.gtceu.data.recipe.misc.RecyclingRecipes;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;

import com.pansmith.steamadditions.common.data.SAMachines;

public class SARecyclingRecipes {

  public static void init(Consumer<FinishedRecipe> provider) {
    ArrayList<MaterialStack> materialStacks = new ArrayList<>(new MaterialStack(GTMaterials.Bronze), new MaterialStack(GTMaterials.Brick));
    RecyclingRecipes.registerRecyclingRecipes‎(provider, SAMachines.STEAM_CENTRIFUGE.asStack(), materialStacks, false, null);
    RecyclingRecipes.registerRecyclingRecipes‎(provider, SAMachines.STEAM_ALLOY_SMELTER.asStack(), materialStacks, false, null);
  }
  
}
