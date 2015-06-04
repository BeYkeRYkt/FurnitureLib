package ru.BeYkeRYkt.FurnitureLib.test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import ru.BeYkeRYkt.FurnitureLib.api.armorstands.ArmorBodyPart;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;
import ru.BeYkeRYkt.FurnitureLib.implementation.furniture.Furniture;

public class FurnitureTest extends Furniture {

    public FurnitureTest() {
        super("Test Furniture #1", "test1");
    }

    @Override
    public List<IFakeArmorStand> collectArmorStands(Location centerLoc, IFurnitureObject object) {
        List<IFakeArmorStand> list = new CopyOnWriteArrayList<IFakeArmorStand>();

        Location loc = new Location(centerLoc.getWorld(), centerLoc.getX(), centerLoc.getY(), centerLoc.getZ());
        IFakeArmorStand stand = getFurnitureManager().createArmorStand(loc);
        stand.setDisplayName("FurnitureLibTest");
        stand.setAngle(ArmorBodyPart.LEFT_ARM, new EulerAngle(1, 1, 1));
        stand.setNameVisible(true);
        stand.setArms(true);
        stand.setBasePlate(false);
        stand.setFire(true);

        Location loc1 = new Location(centerLoc.getWorld(), centerLoc.getX() + 1, centerLoc.getY(), centerLoc.getZ());
        IFakeArmorStand stand1 = getFurnitureManager().createArmorStand(loc1);
        stand1.setDisplayName("FurnitureLibTest");
        stand1.setNameVisible(true);
        stand1.setArms(true);
        stand1.setBasePlate(false);
        stand1.setFire(true);

        Location loc2 = new Location(centerLoc.getWorld(), centerLoc.getX() - 1, centerLoc.getY(), centerLoc.getZ());
        IFakeArmorStand stand2 = getFurnitureManager().createArmorStand(loc2);
        stand2.setDisplayName("FurnitureLibTest");
        stand2.setNameVisible(true);
        stand2.setArms(true);
        stand2.setBasePlate(false);
        stand2.setFire(true);

        list.add(stand);
        list.add(stand1);
        list.add(stand2);
        return list;
    }

    @Override
    public boolean onPlaceCheck(Player player, Location location) {
        return true;
    }

    @Override
    public void onFurnitureCreateByPlugin(IFurnitureObject object) {
        // object.getCenterLocation().getWorld().createExplosion(object.getCenterLocation(),
        // 1);
    }

    @Override
    public void onFurnitureCreateByPlayer(Player player, IFurnitureObject object) {
        player.sendMessage(getDisplayName());
    }

    @Override
    public void onFurnitureInteract(Player player, IFurnitureObject object) {
        player.sendMessage(getDisplayName());
    }

    @Override
    public void onFurnitureDamage(Entity damager, IFurnitureObject object) {
        if (damager.getType() == EntityType.PLAYER) {
            Player player = (Player) damager;
            player.sendMessage("You kill me! NOOO!");
        }

        getFurnitureManager().destroyFurniture(object);
    }

}