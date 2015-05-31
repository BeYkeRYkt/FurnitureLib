package de.Ste3et_C0st.FurnitureLib.api.armorstands;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IArmorStandInventory {

    public ItemStack getItemInHand();

    public ItemStack getBoots();

    public ItemStack getLeggings();

    public ItemStack getChestPlate();

    public ItemStack getHelmet();

    public void setItemInHand(ItemStack item);

    public void setBoots(ItemStack item);

    public void setLeggings(ItemStack item);

    public void setChestPlate(ItemStack item);

    public void setHelmet(ItemStack item);

    public ItemStack getSlot(int slot);

    public void setSlot(int slot, ItemStack item);

    public boolean isEmpty();

    public void update(Player player);

    public void update(Collection<Player> list);

    public void update();
}