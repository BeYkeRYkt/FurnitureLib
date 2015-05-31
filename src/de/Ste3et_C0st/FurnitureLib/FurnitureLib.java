package de.Ste3et_C0st.FurnitureLib;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

import de.Ste3et_C0st.FurnitureLib.api.IFurnitureManager;
import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;
import de.Ste3et_C0st.FurnitureLib.api.furniture.IFurnitureObject;
import de.Ste3et_C0st.FurnitureLib.implementation.FurnitureManager;
import de.Ste3et_C0st.FurnitureLib.test.FurnitureTest;

@SuppressWarnings("deprecation")
public class FurnitureLib extends JavaPlugin implements Listener {

    private static IFurnitureManager manager;

    @Override
    public void onEnable() {
        FurnitureLib.manager = new FurnitureManager();
        getServer().getPluginManager().registerEvents(this, this);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {

            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
                    Integer PacketID = event.getPacket().getIntegers().read(0);
                    if (getFurnitureManager().isArmorStand(PacketID)) {
                        IFakeArmorStand stand = getFurnitureManager().getArmorStand(PacketID);
                        IFurnitureObject object = getFurnitureManager().getFurnitureFromArmorStand(stand);
                        Player p = event.getPlayer();
                        EntityUseAction action = event.getPacket().getEntityUseActions().read(0);
                        switch (action) {
                            case ATTACK:
                                object.getFurniture().onFurnitureDamage(p, object);
                                break;
                            case INTERACT_AT:
                                object.getFurniture().onFurnitureInteract(p, object);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        });
    }

    public static IFurnitureManager getFurnitureManager() {
        return manager;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        //final IFakeArmorStand stand = getFurnitureManager().createArmorStand(event.getPlayer().getLocation());
        //stand.setDisplayName(event.getPlayer().getName());
        //stand.setArms(true);
        //stand.setBasePlate(true);
        //stand.setGravity(true);
        //stand.getInventory().setItemInHand(event.getPlayer().getItemInHand());
        //stand.getInventory().setHelmet(event.getPlayer().getInventory().getHelmet());
        //stand.spawn();
        // stand.setNameVisible(true);
        //getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
        //    @Override
        //    public void run() {
        //        stand.setYaw(stand.getYaw() + 1);
        //        stand.update();
        //    }

        //}, 2, 2);
        
        IFurnitureObject object = getFurnitureManager().spawnFurniture(event.getPlayer().getLocation(), new FurnitureTest());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getWorld() == event.getTo().getWorld() && event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())
            return;

        Player player = event.getPlayer();
        if (player.getHealth() <= 0.0D)
            return;

        Chunk oldChunk = event.getFrom().getChunk();
        Chunk newChunk = event.getTo().getChunk();

        if (oldChunk.getWorld() != newChunk.getWorld() || oldChunk.getX() != newChunk.getX() || oldChunk.getZ() != newChunk.getZ()) {
            //FurnitureLib.getFurnitureManager().updateView(event.getPlayer());
        }
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        final Player player = event.getPlayer();

        getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
               getFurnitureManager().updateView(player);
            }
        }, 10L);
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                //getFurnitureManager().updateView(player);
            }
        }, 10L);
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        getFurnitureManager().removeView(player);
    }
    
    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                //getFurnitureManager().updateView(player);
            }
        }, 10L);
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                //getFurnitureManager().updateView(player);
            }
        }, 5L);
    }
}