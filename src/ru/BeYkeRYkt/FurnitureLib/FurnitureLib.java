package ru.BeYkeRYkt.FurnitureLib;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.Utils;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;
import ru.BeYkeRYkt.FurnitureLib.implementation.FurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.implementation.furniture.FurnitureUnknownId;
import ru.BeYkeRYkt.FurnitureLib.test.FurnitureChair;
import ru.BeYkeRYkt.FurnitureLib.test.FurnitureTest;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

public class FurnitureLib extends JavaPlugin implements Listener {

    private static IFurnitureManager manager;

    @Override
    public void onEnable() {
        FurnitureLib.manager = new FurnitureManager();
        getServer().getPluginManager().registerEvents(this, this);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
                    Integer PacketID = event.getPacket().getIntegers().read(0);
                    if (getFurnitureManager().isArmorStand(PacketID)) {
                        IFakeArmorStand stand = getFurnitureManager().getArmorStand(PacketID);
                        IFurnitureObject object = stand.getFurnitureObject();
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

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.STEER_VEHICLE) {
                    if (event.getPacket().getSpecificModifier(boolean.class).read(1)) {
                        Player p = event.getPlayer();
                        getFurnitureManager().getSitStand(p).setPassenger(null);
                    }
                }
            }
        });

        getFurnitureManager().registerFurniture(new FurnitureUnknownId());
        getFurnitureManager().registerFurniture(new FurnitureTest());
        getFurnitureManager().registerFurniture(new FurnitureChair());
        getServer().getScheduler().runTaskTimer(this, new Runnable() {

            @Override
            public void run() {
                getFurnitureManager().updateFurnitures();
            }
        }, 0, 5);
    }

    public static IFurnitureManager getFurnitureManager() {
        return manager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().getType() == Material.WOOD_HOE) {
                Location loc = event.getClickedBlock().getLocation().add(0, 1, 0);
                loc.setYaw(event.getPlayer().getLocation().getYaw());
                Random r = new Random();
                int c = r.nextInt(3);
                if (c == 0) {
                    loc = Utils.getCenter(loc, false);
                    getFurnitureManager().spawnFurniture(loc, "test1", event.getPlayer());
                } else if (c == 1) {
                    // for call unknown...
                    loc = Utils.getCenter(loc, false);
                    getFurnitureManager().spawnFurniture(loc, "devTest", event.getPlayer());
                } else {
                    loc = Utils.getCenter(loc, true);
                    getFurnitureManager().spawnFurniture(loc, "chair", event.getPlayer());
                }
            }
        }
    }

}