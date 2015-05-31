package de.Ste3et_C0st.FurnitureLib.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurniture;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurnitureObject;

public interface IFurnitureManager {

    //Furniture
    public boolean registerFurniture(IFurniture furniture);

    public IFurniture getFurniture(String id);

    public boolean unregisterFurniture(String id);

    public IFurnitureObject spawnFurniture(Location loc, IFurniture furniture);

    public IFurnitureObject spawnFurniture(Location loc, IFurniture furniture, Player player);

    public void destroyFurniture(IFurnitureObject furniture);
    
    public IFurnitureObject getFurnitureFromArmorStand(IFakeArmorStand stand);
    
    //Armor stand
    public IFakeArmorStand createArmorStand(Location loc);
    
    public boolean isArmorStand(int entityId);
    
    public IFakeArmorStand getArmorStand(int entityId);
    
    public void removeArmorStand(IFakeArmorStand stand);

    //etc.
    public void updateView(Player player);
    
    public void removeView(Player player);
}