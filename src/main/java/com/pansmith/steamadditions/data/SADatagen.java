package com.pansmith.steamadditions.data;

import com.pansmith.steamadditions.api.registries.SARegistries;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.pansmith.steamadditions.data.lang.LangHandler;

public class SADatagen {
    public static void init() {

        SARegistries.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
