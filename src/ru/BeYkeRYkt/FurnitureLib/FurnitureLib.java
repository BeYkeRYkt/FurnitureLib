package ru.BeYkeRYkt.FurnitureLib;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;
import ru.BeYkeRYkt.FurnitureLib.implementation.FurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.implementation.furniture.FurnitureUnknownId;
import ru.BeYkeRYkt.FurnitureLib.test.FurnitureTest;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

@SuppressWarnings("deprecation")
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

        getFurnitureManager().registerFurniture(new FurnitureUnknownId());
        getFurnitureManager().registerFurniture(new FurnitureTest());
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
    public void onPlayerChat(PlayerChatEvent event) {
        Random r = new Random();
        int c = r.nextInt(2);
        if (c == 0) {
            IFurnitureObject object = getFurnitureManager().spawnFurniture(event.getPlayer().getLocation(), "test1", event.getPlayer());
        } else {
            // for call unknown...
            IFurnitureObject object1 = getFurnitureManager().spawnFurniture(event.getPlayer().getLocation(), "devTest", event.getPlayer());
        }
    }
}