package com.pansmith.steamadditions;

import com.pansmith.steamadditions.data.SADatagen;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.config.ConfigHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(steamadditions.MOD_ID)
public class steamadditions {
	public static final String
			MOD_ID = "steamadditions",
			NAME = "Steam Additions";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static MaterialRegistry MATERIAL_REGISTRY;

	public steamadditions() {
		steamadditions.init();
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.register(this);

		bus.addGenericListener(MachineDefinition.class, this::registerMachines);
	}

	public static void init() {
		ConfigHolder.init(); // Forcefully init GT config because fabric doesn't allow dependents to load after dependencies

		SADatagen.init();

	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	@SubscribeEvent
	public void registerMaterialRegistry(MaterialRegistryEvent event) {
		MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(steamadditions.MOD_ID);
	}
	@SubscribeEvent
	public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
		com.pansmith.steamadditions.common.data.SAMachines.init();
	}
}