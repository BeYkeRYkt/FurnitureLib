package de.Ste3et_C0st.FurnitureLib.implementation.furniture;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurniture;

public abstract class Furniture implements IFurniture {

    private String displayName;
    private String id;
    private List<IFakeArmorStand> list;

    public Furniture(String displayNameForItemStack, String id) {
        this.displayName = displayNameForItemStack;
        this.id = id;
        this.list = new CopyOnWriteArrayList<IFakeArmorStand>();
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getId() {
        return id;
    }
}