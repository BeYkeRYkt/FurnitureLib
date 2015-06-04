package ru.BeYkeRYkt.FurnitureLib.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurniture;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;

public interface IFurnitureManager {

    // Furniture
    public boolean registerFurniture(IFurniture furniture);

    public IFurniture getFurniture(String id);

    public boolean unregisterFurniture(String id);

    public IFurnitureObject spawnFurniture(Location loc, String id);

    public IFurnitureObject spawnFurniture(Location loc, String id, Player creator);

    public void destroyFurniture(IFurnitureObject furniture);

    // Armor stand
    public IFakeArmorStand createArmorStand(Location loc);

    public boolean isArmorStand(int entityId);

    public IFakeArmorStand getArmorStand(int entityId);

    public IFakeArmorStand getSitStand(Player player);

    // etc.
    public void updateFurnitures();

}