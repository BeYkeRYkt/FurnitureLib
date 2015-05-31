package de.Ste3et_C0st.FurnitureLib.implementation.furniture;

import java.util.List;

import org.bukkit.Location;

import de.Ste3et_C0st.FurnitureLib.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.api.IFurnitureManager;
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
            stand.setDisplayName(getFurniture().getId());
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

    @Override
    public IFurnitureManager getFurnitureManager() {
        return FurnitureLib.getFurnitureManager();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((furniture == null) ? 0 : furniture.hashCode());
        result = prime * result + ((list == null) ? 0 : list.hashCode());
        result = prime * result + ((loc == null) ? 0 : loc.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FurnitureObject)) {
            return false;
        }
        FurnitureObject other = (FurnitureObject) obj;
        if (furniture == null) {
            if (other.furniture != null) {
                return false;
            }
        } else if (!furniture.equals(other.furniture)) {
            return false;
        }
        if (list == null) {
            if (other.list != null) {
                return false;
            }
        } else if (!list.equals(other.list)) {
            return false;
        }
        if (loc == null) {
            if (other.loc != null) {
                return false;
            }
        } else if (!loc.equals(other.loc)) {
            return false;
        }
        return true;
    }

}