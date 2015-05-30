package de.Ste3et_C0st.FurnitureLib;

import org.bukkit.plugin.java.JavaPlugin;

import de.Ste3et_C0st.FurnitureLib.api.IFurnitureManager;
import de.Ste3et_C0st.FurnitureLib.implementation.FurnitureManager;

public class FurnitureLib extends JavaPlugin {

    private static IFurnitureManager manager;

    @Override
    public void onEnable() {
        FurnitureLib.manager = new FurnitureManager();
    }

    public static IFurnitureManager getFurnitureManager() {
        return manager;
    }
}