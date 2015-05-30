package de.Ste3et_C0st.FurnitureLib.implementation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import de.Ste3et_C0st.FurnitureLib.api.IFurnitureManager;
import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurniture;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurnitureObject;
import de.Ste3et_C0st.FurnitureLib.implementation.armorstands.FakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.implementation.furniture.FurnitureObject;

public class FurnitureManager implements IFurnitureManager {

    private Map<String, IFurniture> furnitures;
    private ProtocolManager protocol;
    private int entityId;

    public FurnitureManager() {
        this.protocol = ProtocolLibrary.getProtocolManager();
        this.furnitures = new ConcurrentHashMap<String, IFurniture>(); // for
                                                                       // multithread
    }

    @Override
    public boolean registerFurniture(IFurniture furniture) {
        if (furnitures.containsKey(furniture.getId())) {
            return false;
        }
        furnitures.put(furniture.getId(), furniture);
        return true;
    }

    @Override
    public IFurniture getFurniture(String id) {
        if (furnitures.containsKey(id)) {
            return furnitures.get(id);
        }
        return null;
    }

    @Override
    public boolean unregisterFurniture(String id) {
        if (!furnitures.containsKey(id)) {
            return false;
        }
        furnitures.remove(id);
        return true;
    }

    @Override
    public IFurnitureObject spawnFurniture(Location loc, IFurniture furniture) {
        FurnitureObject object = new FurnitureObject(loc, furniture);
        object.getFurniture().onFurnitureCreateByPlugin(object);
        object.spawnFurniture();
        return object;
    }

    @Override
    public IFurnitureObject spawnFurniture(Location loc, IFurniture furniture, Player player) {
        FurnitureObject object = new FurnitureObject(loc, furniture);
        object.getFurniture().onFurnitureCreateByPlayer(player, object);
        object.spawnFurniture();
        return object;
    }

    @Override
    public void updateFurnitures(Location loc) {
        // TODO:
    }

    @Override
    public IFakeArmorStand createArmorStand(Location loc) {
        entityId++;
        IFakeArmorStand stand = new FakeArmorStand(loc, entityId);
        return stand;
    }

}