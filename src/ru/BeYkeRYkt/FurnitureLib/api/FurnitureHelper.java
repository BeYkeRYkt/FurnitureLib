package ru.BeYkeRYkt.FurnitureLib.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import ru.BeYkeRYkt.FurnitureLib.FurnitureLib;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.ArmorBodyPart;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;

public class FurnitureHelper {

    public static IFakeArmorStand getBigFeel(Location feetLoc, boolean debug) {
        // feetLoc = Utils.getCenter(feetLoc, false);
        // feetLoc.add(-0.2, -1.4, -0.12);
        IFakeArmorStand stand = FurnitureLib.getFurnitureManager().createArmorStand(feetLoc);
        stand.setArms(true);
        stand.setAngle(ArmorBodyPart.RIGHT_ARM, new EulerAngle(-10, -90, -90));
        stand.getInventory().setItemInHand(new ItemStack(Material.STICK));
        if (!debug) {
            stand.setBasePlate(false);
            stand.setInvisible(true);
        }
        return stand;
    }

    public static IFakeArmorStand getSmallFeel(Location feetLoc, boolean debug) {
        IFakeArmorStand stand = FurnitureLib.getFurnitureManager().createArmorStand(feetLoc);
        stand.getInventory().setHelmet(new ItemStack(Material.LEVER));
        if (!debug) {
            stand.setBasePlate(false);
            stand.setInvisible(true);
        }
        return stand;
    }

    public static IFakeArmorStand getFlat(Location loc, ItemStack item, boolean debug) {
        IFakeArmorStand stand = FurnitureLib.getFurnitureManager().createArmorStand(loc);
        // stand.setAngle(ArmorBodyPart.HEAD, new EulerAngle(0, .0, .0));
        stand.getInventory().setHelmet(item);
        if (!debug) {
            stand.setBasePlate(false);
            stand.setInvisible(true);
        }
        return stand;
    }

}