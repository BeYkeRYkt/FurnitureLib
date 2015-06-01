package ru.BeYkeRYkt.FurnitureLib.api.furniture;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;

public interface IFurniture {

    public String getDisplayName();

    public String getId();

    public IFurnitureManager getFurnitureManager();

    public boolean onPlaceCheck(Player player, IFurnitureObject object);

    public void onFurnitureCreateByPlugin(IFurnitureObject object);

    public void onFurnitureCreateByPlayer(Player player, IFurnitureObject object);

    public void onFurnitureInteract(Player player, IFurnitureObject object);

    public void onFurnitureDamage(Entity damager, IFurnitureObject object);

    public List<IFakeArmorStand> collectArmorStands(Location centerLoc);

}