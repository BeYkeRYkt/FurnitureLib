package de.Ste3et_C0st.FurnitureLib.api.armorstands;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import de.Ste3et_C0st.FurnitureLib.main.ObjectID;

public interface IFakeArmorStand {

    public Location getLocation();
    
    public Chunk getChunk();
    
    public void spawn();
    
    public void despawn();

    public EulerAngle getAngle(ArmorBodyPart part);
    
    public void setAngle(ArmorBodyPart  part, EulerAngle angle);

    public String getDisplayName();
    
    public void setDisplayName(String name);

    public ObjectID getObjectId();

    public IArmorStandInventory getInventory();

    public int getEntityID();

    public boolean isFire();
    
    public void setFire(boolean flag);

    public boolean isNameVisible();
    
    public void setNameVisible(boolean flag);

    public boolean isInvisible();
    
    public void setInvisible(boolean flag);

    public boolean isMini();
    
    public void setMini(boolean flag);

    public boolean hasArms();
    
    public void setArms(boolean flag);

    public boolean hasBasePlate();
    
    public void setBasePlate(boolean flag);

    public boolean hasGravity();

    public void sasGravity(boolean flag);
    
    public boolean isInRange(Player player);
}