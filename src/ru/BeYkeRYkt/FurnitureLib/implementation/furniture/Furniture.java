package ru.BeYkeRYkt.FurnitureLib.implementation.furniture;

import ru.BeYkeRYkt.FurnitureLib.FurnitureLib;
import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurniture;

public abstract class Furniture implements IFurniture {

    private String displayName;
    private String id;

    public Furniture(String displayNameForItemStack, String id) {
        this.displayName = displayNameForItemStack;
        this.id = id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public IFurnitureManager getFurnitureManager() {
        return FurnitureLib.getFurnitureManager();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (!(obj instanceof Furniture)) {
            return false;
        }
        Furniture other = (Furniture) obj;
        if (displayName == null) {
            if (other.displayName != null) {
                return false;
            }
        } else if (!displayName.equals(other.displayName)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}