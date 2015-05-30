package de.Ste3et_C0st.FurnitureLib.test;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurnitureObject;
import de.Ste3et_C0st.FurnitureLib.implementation.armorstands.FakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.implementation.furniture.Furniture;

public class FurnitureTest extends Furniture {

    public FurnitureTest() {
        super("Test Furniture #1", "test1");
    }

    @Override
    public List<IFakeArmorStand> collectArmorStands(Location centerLoc) {
        List<IFakeArmorStand> list = new ArrayList<IFakeArmorStand>();
        Location loc = new Location(centerLoc.getWorld(), centerLoc.getX(), centerLoc.getY(), centerLoc.getZ());
        IFakeArmorStand stand = new FakeArmorStand(loc, 1);
        list.add(stand);
        return list;
    }

    @Override
    public boolean onPlaceCheck(Player player, IFurnitureObject object) {
        return true;
    }

    @Override
    public void onFurnitureCreateByPlugin(IFurnitureObject object) {
        object.getCenterLocation().getWorld().createExplosion(object.getCenterLocation(), 1);
    }

    @Override
    public void onFurnitureCreateByPlayer(Player player, IFurnitureObject object) {
        player.sendMessage(getDisplayName());
    }

    @Override
    public void onFurnitureInteract(Player player, IFurnitureObject object, Action action) {
        player.sendMessage(getDisplayName());
    }

    @Override
    public void onFurnitureDamage(Entity damager, IFurnitureObject object) {
        if (damager.getType().isAlive()) {
            LivingEntity entity = (LivingEntity) damager;
            entity.damage(1000);
        }
    }

}