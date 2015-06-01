package ru.BeYkeRYkt.FurnitureLib.api.furniture;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;

public interface IFurnitureObject {

    public IFurniture getFurniture();

    public Location getCenterLocation();

    public World getWorld();

    public Chunk getChunk();

    public void spawnFurniture();

    public void destroyFurniture();

    public List<IFakeArmorStand> getArmorStands();

    public boolean checkArmorStand(int entityId);

    public IFakeArmorStand getArmorStand(int entityId);

    public IFurnitureManager getFurnitureManager();

    public boolean isInRange(Location loc);

    public List<Player> getPlayers();

    public void update();
}