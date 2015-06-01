package ru.BeYkeRYkt.FurnitureLib.implementation.furniture;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import ru.BeYkeRYkt.FurnitureLib.FurnitureLib;
import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurniture;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;

public class FurnitureObject implements IFurnitureObject {

    private Location loc;
    private IFurniture furniture;
    private List<IFakeArmorStand> list;
    private List<Player> players;
    // private List<Player> removePlayers;
    private boolean spawned;

    public FurnitureObject(Location centerLoc, IFurniture furniture) {
        this.loc = centerLoc;
        this.furniture = furniture;
        this.players = new CopyOnWriteArrayList<Player>();
        this.list = getFurniture().collectArmorStands(getCenterLocation());
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
    public World getWorld() {
        return getCenterLocation().getWorld();
    }

    @Override
    public Chunk getChunk() {
        return getCenterLocation().getChunk();
    }

    @Override
    public synchronized void spawnFurniture() {
        this.spawned = true;
        update();
    }

    @Override
    public synchronized void destroyFurniture() {
        this.spawned = false;
        update();
    }

    @Override
    public List<IFakeArmorStand> getArmorStands() {
        return list;
    }

    @Override
    public IFakeArmorStand getArmorStand(int entityId) {
        for (IFakeArmorStand stand : getArmorStands()) {
            if (stand.getEntityID() == entityId) {
                return stand;
            }
        }
        return null;
    }

    @Override
    public IFurnitureManager getFurnitureManager() {
        return FurnitureLib.getFurnitureManager();
    }

    @Override
    public boolean isInRange(Location loc) {
        return getCenterLocation().getWorld() == loc.getWorld() && (getCenterLocation().distance(loc) <= 48D);
    }

    @Override
    public boolean checkArmorStand(int entityId) {
        for (IFakeArmorStand stand : getArmorStands()) {
            if (stand.getEntityID() == entityId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public synchronized void update() {
        if (spawned) {
            // remove unvisible players
            // removePlayers.clear();
            for (Player player : getPlayers()) {
                if (!isInRange(player.getLocation()) || !player.isOnline()) {
                    // removePlayers.add(player);
                    if (player.isOnline()) {
                        destroyFurniture(player);
                    }
                    getPlayers().remove(player);
                }
            }

            // add players
            for (Player player : getWorld().getPlayers()) {
                if (isInRange(player.getLocation()) && player.isOnline()) {
                    if (!getPlayers().contains(player)) {
                        spawnFurniture(player);
                        getPlayers().add(player);
                    }
                }
            }

            // Update
            for (IFakeArmorStand stand : getArmorStands()) {
                stand.update(getPlayers());
            }
        } else {
            if (!getPlayers().isEmpty()) {
                for (Player player : getPlayers()) {
                    destroyFurniture(player);
                }
                getPlayers().clear();
            }
        }
    }

    public void spawnFurniture(Player player) {
        for (IFakeArmorStand stand : getArmorStands()) {
            stand.setFurnitureObject(this);
            // stand.setDisplayName(getFurniture().getId());
            stand.spawn(player);
        }
    }

    public void destroyFurniture(Player player) {
        for (IFakeArmorStand stand : getArmorStands()) {
            stand.despawn(player);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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