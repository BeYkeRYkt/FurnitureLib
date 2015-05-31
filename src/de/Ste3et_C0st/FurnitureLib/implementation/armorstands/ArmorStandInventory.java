package de.Ste3et_C0st.FurnitureLib.implementation.armorstands;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import de.Ste3et_C0st.FurnitureLib.api.armorstands.IArmorStandInventory;

public class ArmorStandInventory implements IArmorStandInventory {

    private ItemStack[] items = new ItemStack[5];
    private FakeArmorStand stand;

    public ArmorStandInventory(FakeArmorStand stand) {
        this.stand = stand;
    }

    @Override
    public ItemStack getItemInHand() {
        return this.items[0];
    }

    @Override
    public ItemStack getBoots() {
        return this.items[1];
    }

    @Override
    public ItemStack getLeggings() {
        return this.items[2];
    }

    @Override
    public ItemStack getChestPlate() {
        return this.items[3];
    }

    @Override
    public ItemStack getHelmet() {
        return this.items[4];
    }

    @Override
    public void setItemInHand(ItemStack item) {
        this.setSlot(0, item);
    }

    @Override
    public void setBoots(ItemStack item) {
        this.setSlot(1, item);
    }

    @Override
    public void setLeggings(ItemStack item) {
        this.setSlot(2, item);
    }

    @Override
    public void setChestPlate(ItemStack item) {
        this.setSlot(3, item);
    }

    @Override
    public void setHelmet(ItemStack item) {
        this.setSlot(4, item);
    }

    @Override
    public ItemStack getSlot(int slot) {
        if (slot < 0 || slot >= this.items.length) {
            return null;
        }

        return this.items[slot];
    }

    @Override
    public void setSlot(int slot, ItemStack item) {
        if (item != null && item.getType() == Material.AIR) {
            item = null;
        }

        if (slot < 0 || slot >= this.items.length) {
            return;
        }

        this.items[slot] = item;
    }

    public List<PacketContainer> createPackets(int entityId) {
        List<PacketContainer> packetList = new ArrayList<PacketContainer>();
        for (int i = 0; i < 5; i++) {
            ItemStack stack = this.getSlot(i);

            PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
            packet.getIntegers().write(0, entityId);
            packet.getIntegers().write(1, i);
            packet.getItemModifier().write(0, stack);

            packetList.add(packet);
        }
        return packetList;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : this.items) {
            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(Collection<Player> list) {
        for (Player player : list) {
            if (stand.isInRange(player)) {
                update(player);
            }
        }
    }

    @Override
    public void update() {
        update(stand.getLocation().getWorld().getPlayers());
    }

    @Override
    public void update(Player player) {
        try {
            for (PacketContainer packet : createPackets(stand.getEntityID())) {
                stand.getProtocolManager().sendServerPacket(player, packet);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(items);
        result = prime * result + ((stand == null) ? 0 : stand.hashCode());
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
        if (!(obj instanceof ArmorStandInventory)) {
            return false;
        }
        ArmorStandInventory other = (ArmorStandInventory) obj;
        if (!Arrays.equals(items, other.items)) {
            return false;
        }
        if (stand == null) {
            if (other.stand != null) {
                return false;
            }
        } else if (!stand.equals(other.stand)) {
            return false;
        }
        return true;
    }
    
    
}