package ru.BeYkeRYkt.FurnitureLib;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;
import ru.BeYkeRYkt.FurnitureLib.implementation.FurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.implementation.furniture.FurnitureUnknownId;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

public class FurnitureLib extends JavaPlugin {

    private static IFurnitureManager manager;

    @Override
    public void onEnable() {
        FurnitureLib.manager = new FurnitureManager();

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {

            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
                    Integer PacketID = event.getPacket().getIntegers().read(0);
                    if (getFurnitureManager().isArmorStand(PacketID)) {
                        IFakeArmorStand stand = getFurnitureManager().getArmorStand(PacketID);
                        Player p = event.getPlayer();
                        IFurnitureObject object = stand.getFurnitureObject();
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
                        IFakeArmorStand stand = getFurnitureManager().getSitStand(p);
                        if (stand != null) {
                            stand.setPassenger(null);
                        }
                    }
                }
            }
        });

        getFurnitureManager().registerFurniture(new FurnitureUnknownId());// for
                                                                          // unknown
                                                                          // furniture
                                                                          // id

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
}