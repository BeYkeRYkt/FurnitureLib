package ru.BeYkeRYkt.FurnitureLib.implementation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurniture;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;
import ru.BeYkeRYkt.FurnitureLib.implementation.armorstands.FakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.implementation.furniture.FurnitureObject;

public class FurnitureManager implements IFurnitureManager {

    private Map<String, IFurniture> furnitures;
    private int entityId;
    private List<IFurnitureObject> objects;

    public FurnitureManager() {
        this.objects = new CopyOnWriteArrayList<IFurnitureObject>();
        // this.stands = new CopyOnWriteArrayList<IFakeArmorStand>();
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
        return furnitures.get("unknown");
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
    public IFurnitureObject spawnFurniture(Location loc, String id) {
        IFurniture furniture = getFurniture(id);
        FurnitureObject object = new FurnitureObject(loc, furniture);
        object.getFurniture().onFurnitureCreateByPlugin(object);
        object.spawnFurniture();
        objects.add(object);
        return object;
    }

    @Override
    public IFurnitureObject spawnFurniture(Location loc, String id, Player player) {
        IFurniture furniture = getFurniture(id);
        FurnitureObject object = new FurnitureObject(loc, furniture);
        object.getFurniture().onFurnitureCreateByPlayer(player, object);
        object.spawnFurniture();
        objects.add(object);
        return object;
    }

    @Override
    public IFakeArmorStand createArmorStand(Location loc) {
        entityId++;
        int id = entityId + 1000000;
        IFakeArmorStand stand = new FakeArmorStand(loc, id);
        // stands.add(stand);
        return stand;
    }

    @Override
    public void destroyFurniture(IFurnitureObject furniture) {
        furniture.destroyFurniture();
        objects.remove(furniture);
    }

    @Override
    public boolean isArmorStand(int entityId) {
        for (IFurnitureObject object : objects) {
            if (object.checkArmorStand(entityId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IFakeArmorStand getArmorStand(int entityId) {
        for (IFurnitureObject object : objects) {
            if (object.checkArmorStand(entityId)) {
                return object.getArmorStand(entityId);
            }
        }
        return null;
    }

    @Override
    public void updateFurnitures() {
        for (IFurnitureObject object : objects) {
            object.update();
        }
    }
}