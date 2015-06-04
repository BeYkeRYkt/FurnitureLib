package ru.BeYkeRYkt.FurnitureLib.implementation.furniture;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;

public class FurnitureUnknownId extends Furniture {

    public FurnitureUnknownId() {
        super("UNKNOWN_ID", "unknown");
    }

    @Override
    public boolean onPlaceCheck(Player player, Location location) {
        return true;
    }

    @Override
    public void onFurnitureCreateByPlugin(IFurnitureObject object) {
    }

    @Override
    public void onFurnitureCreateByPlayer(Player player, IFurnitureObject object) {
    }

    @Override
    public void onFurnitureInteract(Player player, IFurnitureObject object) {
    }

    @Override
    public void onFurnitureDamage(Entity damager, IFurnitureObject object) {
        getFurnitureManager().destroyFurniture(object);
    }

    @Override
    public List<IFakeArmorStand> collectArmorStands(Location centerLoc, IFurnitureObject object) {
        List<IFakeArmorStand> list = new ArrayList<IFakeArmorStand>();
        IFakeArmorStand stand = getFurnitureManager().createArmorStand(centerLoc);
        stand.setDisplayName(getDisplayName());
        stand.setArms(true);
        stand.setNameVisible(true);
        list.add(stand);
        return list;
    }

}