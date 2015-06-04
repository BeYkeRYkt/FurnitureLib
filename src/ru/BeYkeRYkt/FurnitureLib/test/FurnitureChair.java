package ru.BeYkeRYkt.FurnitureLib.test;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import ru.BeYkeRYkt.FurnitureLib.api.Utils;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.ArmorBodyPart;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;
import ru.BeYkeRYkt.FurnitureLib.implementation.furniture.Furniture;

public class FurnitureChair extends Furniture {

    public FurnitureChair() {
        super("Chair #1", "chair");
    }

    @Override
    public boolean onPlaceCheck(Player player, Location location) {
        return location.getBlock().getType().isSolid();
    }

    @Override
    public void onFurnitureCreateByPlugin(IFurnitureObject object) {
    }

    @Override
    public void onFurnitureCreateByPlayer(Player player, IFurnitureObject object) {
        player.getWorld().playSound(object.getCenterLocation(), Sound.STEP_WOOD, 1, 1);
    }

    @Override
    public void onFurnitureInteract(Player player, IFurnitureObject object) {
        object.getSitStand().setPassenger(player);
    }

    @Override
    public void onFurnitureDamage(Entity damager, IFurnitureObject object) {
        getFurnitureManager().destroyFurniture(object);
        damager.getWorld().playSound(object.getCenterLocation(), Sound.DIG_WOOD, 1, 1);
    }

    @Override
    public List<IFakeArmorStand> collectArmorStands(Location centerLoc, IFurnitureObject object) {
        List<IFakeArmorStand> list = new ArrayList<IFakeArmorStand>();
        BlockFace b = Utils.yawToFace(centerLoc.getYaw()).getOppositeFace();
        Location center = centerLoc;
        Location sit = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
        Location feet1 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
        Location feet2 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
        Location feet3 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
        Location feet4 = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());

        Location lehne = Utils.getNew(center.add(0, -1.1, 0), b, -.25, .0);

        feet1.add(-.25, -1.8, -.25);
        feet2.add(.25, -1.8, -.25);
        feet3.add(.25, -1.8, .25);
        feet4.add(-.25, -1.8, .25);

        sit.add(0, -1.30, 0);

        sit.setYaw(Utils.FaceToYaw(b));
        lehne.setYaw(Utils.FaceToYaw(b));
        feet1.setYaw(Utils.FaceToYaw(b));
        feet2.setYaw(Utils.FaceToYaw(b));
        feet3.setYaw(Utils.FaceToYaw(b));
        feet4.setYaw(Utils.FaceToYaw(b));

        IFakeArmorStand stand1 = getFurnitureManager().createArmorStand(sit);
        stand1.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
        stand1.setBasePlate(false);
        stand1.setInvisible(true);
        object.setSitStand(stand1);

        IFakeArmorStand stand2 = getFurnitureManager().createArmorStand(lehne);
        stand2.setAngle(ArmorBodyPart.HEAD, new EulerAngle(1.57, .0, .0));
        stand2.getInventory().setHelmet(new ItemStack(Material.TRAP_DOOR));
        stand2.setBasePlate(false);
        stand2.setInvisible(true);

        IFakeArmorStand stand3 = getFurnitureManager().createArmorStand(feet1);
        stand3.getInventory().setHelmet(new ItemStack(Material.LEVER));
        stand3.setBasePlate(false);
        stand3.setInvisible(true);

        IFakeArmorStand stand4 = getFurnitureManager().createArmorStand(feet2);
        stand4.getInventory().setHelmet(new ItemStack(Material.LEVER));
        stand4.setBasePlate(false);
        stand4.setInvisible(true);

        IFakeArmorStand stand5 = getFurnitureManager().createArmorStand(feet3);
        stand5.getInventory().setHelmet(new ItemStack(Material.LEVER));
        stand5.setBasePlate(false);
        stand5.setInvisible(true);

        IFakeArmorStand stand6 = getFurnitureManager().createArmorStand(feet4);
        stand6.getInventory().setHelmet(new ItemStack(Material.LEVER));
        stand6.setBasePlate(false);
        stand6.setInvisible(true);

        IFakeArmorStand stand7 = getFurnitureManager().createArmorStand(sit.add(0, -.2, 0));
        stand7.setBasePlate(false);
        stand7.setInvisible(true);

        list.add(stand1);
        list.add(stand2);
        list.add(stand3);
        list.add(stand4);
        list.add(stand5);
        list.add(stand6);
        list.add(stand7);
        return list;
    }

}