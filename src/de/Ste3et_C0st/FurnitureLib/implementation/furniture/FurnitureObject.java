package de.Ste3et_C0st.FurnitureLib.implementation.furniture;

import java.util.List;

import org.bukkit.Location;

import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurniture;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurnitureObject;

public class FurnitureObject implements IFurnitureObject {

    private Location loc;
    private IFurniture furniture;
    private List<IFakeArmorStand> list;

    public FurnitureObject(Location centerLoc, IFurniture furniture) {
        this.loc = centerLoc;
        this.furniture = furniture;
    }

    @Override
    public IFurniture getFurniture() {
        return furniture;
    }

    @Override
    public Location getCenterLocation() {
        return loc;
    }

    @Override
    public void spawnFurniture() {
        list = getFurniture().collectArmorStands(getCenterLocation());
        for (IFakeArmorStand stand : getArmorStands()) {
            stand.spawn();
        }
    }

    @Override
    public void destroyFurniture() {
        for (IFakeArmorStand stand : getArmorStands()) {
            stand.despawn();
        }
    }

    @Override
    public List<IFakeArmorStand> getArmorStands() {
        return list;
    }

}