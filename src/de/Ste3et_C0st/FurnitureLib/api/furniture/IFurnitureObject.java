package de.Ste3et_C0st.FurnitureLib.api.furniture;

import java.util.List;

import org.bukkit.Location;

import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;

public interface IFurnitureObject {

    public IFurniture getFurniture();

    public Location getCenterLocation();

    public void spawnFurniture();

    public void destroyFurniture();

    public List<IFakeArmorStand> getArmorStands();

}