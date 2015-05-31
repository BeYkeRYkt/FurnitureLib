package de.Ste3et_C0st.FurnitureLib.implementation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.Ste3et_C0st.FurnitureLib.api.IFurnitureManager;
import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurniture;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurnitureObject;
import de.Ste3et_C0st.FurnitureLib.implementation.armorstands.FakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.implementation.furniture.FurnitureObject;

public class FurnitureManager implements IFurnitureManager {

    private Map<String, IFurniture> furnitures;
    private int entityId;
    private List<IFurnitureObject> objects;
    private List<IFakeArmorStand> stands;

    public FurnitureManager() {
        this.objects = new CopyOnWriteArrayList<IFurnitureObject>();
        this.stands = new CopyOnWriteArrayList<IFakeArmorStand>();
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
        objects.add(object);
        return object;
    }

    @Override
    public IFurnitureObject spawnFurniture(Location loc, IFurniture furniture, Player player) {
        FurnitureObject object = new FurnitureObject(loc, furniture);
        object.getFurniture().onFurnitureCreateByPlayer(player, object);
        object.spawnFurniture();
        objects.add(object);
        return object;
    }

    @Override
    public IFakeArmorStand createArmorStand(Location loc) {
        entityId++;
        IFakeArmorStand stand = new FakeArmorStand(loc, entityId);
        stands.add(stand);
        return stand;
    }

    @Override
    public void updateView(Player player) {
        if(stands.isEmpty()) return;
        for(IFakeArmorStand stand: stands){
            if(stand.isInRange(player)){
                stand.spawn();
            }else{
                stand.despawn();
            }
        }
    }

    @Override
    public void destroyFurniture(IFurnitureObject furniture) {
        furniture.destroyFurniture();
        for(IFakeArmorStand stand: furniture.getArmorStands()){
            removeArmorStand(stand);
        }
        objects.remove(furniture);
    }

    @Override
    public IFurnitureObject getFurnitureFromArmorStand(IFakeArmorStand stand) {
        for(IFurnitureObject furniture: objects){
            if(furniture.getArmorStands().contains(stand)){
                return furniture;
            }
        }
        return null;
    }

    @Override
    public boolean isArmorStand(int entityId) {
        for(IFakeArmorStand stand: stands){
            if(stand.getEntityID() == entityId){
                return true;
            }
        }
        return false;
    }

    @Override
    public IFakeArmorStand getArmorStand(int entityId) {
        for(IFakeArmorStand stand: stands){
            if(stand.getEntityID() == entityId){
                return stand;
            }
        }
        return null;
    }

    @Override
    public void removeArmorStand(IFakeArmorStand stand) {
        stand.despawn();
        stands.remove(stand);
    }

    @Override
    public void removeView(Player player) {
        if(stands.isEmpty()) return;
        for(IFakeArmorStand stand: stands){
            if(stand.isInRange(player)){
                stand.despawn();
            }
        }
    }
}