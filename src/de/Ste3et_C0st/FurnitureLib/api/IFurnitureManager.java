package de.Ste3et_C0st.FurnitureLib.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurniture;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurnitureObject;

public interface IFurnitureManager {

    public boolean registerFurniture(IFurniture furniture);

    public IFurniture getFurniture(String id);

    public boolean unregisterFurniture(String id);

    public IFurnitureObject spawnFurniture(Location loc, IFurniture furniture);

    public IFurnitureObject spawnFurniture(Location loc, IFurniture furniture, Player player);

    // public void destroyFurniture(IFurniture furniture);

    public void updateFurnitures(Location loc);

    public IFakeArmorStand createArmorStand(Location loc);
}