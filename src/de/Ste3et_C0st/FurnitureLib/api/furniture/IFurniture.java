package de.Ste3et_C0st.FurnitureLib.api.furniture;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.Ste3et_C0st.FurnitureLib.api.IFurnitureManager;
import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;

public interface IFurniture {

    public String getDisplayName();

    public String getId();

    public IFurnitureManager getFurnitureManager();
    
    public boolean onPlaceCheck(Player player, IFurnitureObject object);

    public void onFurnitureCreateByPlugin(IFurnitureObject object);

    public void onFurnitureCreateByPlayer(Player player, IFurnitureObject object);

    public void onFurnitureInteract(Player player, IFurnitureObject object);

    public void onFurnitureDamage(Entity damager, IFurnitureObject object);

    public List<IFakeArmorStand> collectArmorStands(Location centerLoc);
    
}