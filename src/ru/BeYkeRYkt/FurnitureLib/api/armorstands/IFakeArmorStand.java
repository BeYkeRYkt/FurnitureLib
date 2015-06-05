package ru.BeYkeRYkt.FurnitureLib.api.armorstands;

import java.util.Collection;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;

public interface IFakeArmorStand {

    public Location getLocation();

    public Chunk getChunk();

    public void spawn(Player player);

    public void despawn(Player player);

    public EulerAngle getAngle(ArmorBodyPart part);

    public void setAngle(ArmorBodyPart part, EulerAngle angle);

    public String getDisplayName();

    public void setDisplayName(String name);

    public IArmorStandInventory getInventory();

    public int getEntityID();

    public boolean isFire();

    public void setFire(boolean flag);

    public boolean isNameVisible();

    public void setNameVisible(boolean flag);

    public boolean isInvisible();

    public void setInvisible(boolean flag);

    public boolean isSmall();

    public void setSmall(boolean flag);

    public boolean hasArms();

    public void setArms(boolean flag);

    public boolean hasBasePlate();

    public void setBasePlate(boolean flag);

    public boolean hasGravity();

    public void setGravity(boolean flag);

    public void setYaw(float yaw);

    public float getYaw();

    public IFurnitureManager getFurnitureManager();

    public IFurnitureObject getFurnitureObject();

    public void setFurnitureObject(IFurnitureObject object);

    public boolean hasFurnitureObject();

    public void setPassenger(Player entity);

    public Player getPassenger();

    // Updates
    public void update(Collection<Player> list);

    public void update();

    public void updateRotation(Player player);

    public void updateMetadata(Player player);

    public void updatePassenger(Player player);
}