package ru.BeYkeRYkt.FurnitureLib.implementation.armorstands;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import ru.BeYkeRYkt.FurnitureLib.FurnitureLib;
import ru.BeYkeRYkt.FurnitureLib.api.IFurnitureManager;
import ru.BeYkeRYkt.FurnitureLib.api.Utils;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.ArmorBodyPart;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IArmorStandInventory;
import ru.BeYkeRYkt.FurnitureLib.api.armorstands.IFakeArmorStand;
import ru.BeYkeRYkt.FurnitureLib.api.furniture.IFurnitureObject;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

public class FakeArmorStand implements IFakeArmorStand {

    private String displayName;
    private Location location;
    private EulerAngle[] ea;
    private Integer entityId;
    private IArmorStandInventory inv;

    private ProtocolManager protocol;
    private WrappedDataWatcher watcher;
    private boolean mini;
    private boolean arms;
    private boolean nameVisible;
    private boolean invisible;
    private boolean fire;
    private boolean basePlate;
    private boolean gravity;
    // private float yaw;

    private boolean updateMetadata;
    private boolean updateRotation;
    private boolean setPassenger;
    private boolean leavePassenger;

    private IFurnitureObject object;
    private Player passenger;

    public FakeArmorStand(Location loc, int EntityId) {
        this.location = loc;
        this.ea = new EulerAngle[6];
        this.entityId = EntityId;
        this.protocol = ProtocolLibrary.getProtocolManager();
        this.watcher = getDefaultWatcher(loc.getWorld(), EntityType.ARMOR_STAND);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Chunk getChunk() {
        return getLocation().getChunk();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void spawn(Player player) {
        // if (isInRange(player)) {
        PacketContainer container = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        container.getIntegers().write(0, this.entityId).write(1, (int) EntityType.ARMOR_STAND.getTypeId()).write(2, Utils.getFixedPoint(this.location.getX())).write(3, Utils.getFixedPoint(this.location.getY())).write(4, Utils.getFixedPoint(this.location.getZ())).write(5, 0).write(6, 0).write(7, 0);
        container.getBytes().write(0, (byte) Utils.getCompressedAngle(getYaw())).write(1, (byte) Utils.getCompressedAngle(0)).write(2, (byte) Utils.getCompressedAngle(getYaw()));
        container.getDataWatcherModifier().write(0, this.watcher);
        sendPacket(player, container);
        // }
    }

    @Override
    public void despawn(Player player) {
        // if (isInRange(player)) {
        PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
        destroy.getIntegerArrays().write(0, new int[] { this.entityId });
        sendPacket(player, destroy);
        // }
    }

    @Override
    public EulerAngle getAngle(ArmorBodyPart part) {
        switch (part) {
            case HEAD:
                return ea[0];
            case BODY:
                return ea[1];
            case LEFT_LEG:
                return ea[2];
            case LEFT_ARM:
                return ea[3];
            case RIGHT_LEG:
                return ea[4];
            case RIGHT_ARM:
                return ea[5];
        }
        return null;
    }

    @Override
    public void setAngle(ArmorBodyPart part, EulerAngle angle) {
        switch (part) {
            case HEAD:
                this.ea[0] = angle;
            case BODY:
                this.ea[1] = angle;
            case LEFT_LEG:
                this.ea[2] = angle;
            case LEFT_ARM:
                this.ea[3] = angle;
            case RIGHT_LEG:
                this.ea[4] = angle;
            case RIGHT_ARM:
                this.ea[5] = angle;
        }
        this.watcher.setObject(part.getField(), Utils.getVectorf3(angle));
        if (!this.updateMetadata) {
            this.updateMetadata = true;
        }
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(String name) {
        this.watcher.setObject(2, name);
        this.displayName = name;
    }

    @Override
    public IArmorStandInventory getInventory() {
        if (inv == null) {
            inv = new ArmorStandInventory(this);
        }
        return inv;
    }

    @Override
    public int getEntityID() {
        return entityId;
    }

    @Override
    public boolean isFire() {
        return this.fire;
    }

    @Override
    public void setFire(boolean flag) {
        byte b0 = this.watcher.getByte(0);
        if (flag)
            b0 = (byte) (b0 | 0x01);
        else {
            b0 = (byte) (b0 & 0xFFFFFFFE);
        }
        this.watcher.setObject(0, Byte.valueOf(b0));
        this.fire = flag;
        if (!this.updateMetadata) {
            this.updateMetadata = true;
        }
    }

    @Override
    public boolean isNameVisible() {
        return this.nameVisible;
    }

    @Override
    public void setNameVisible(boolean flag) {
        this.watcher.setObject(3, (byte) 1);
        this.nameVisible = flag;
        if (!this.updateMetadata) {
            this.updateMetadata = true;
        }
    }

    @Override
    public boolean isInvisible() {
        return invisible;
    }

    @Override
    public void setInvisible(boolean flag) {
        byte b0 = this.watcher.getByte(0);

        if (flag)
            b0 = (byte) (b0 | 0x20);
        else {
            b0 = (byte) (b0 & 0xFFFFFFFB);
        }
        this.watcher.setObject(0, Byte.valueOf(b0));
        this.invisible = flag;
        if (!this.updateMetadata) {
            this.updateMetadata = true;
        }
    }

    @Override
    public boolean isSmall() {
        return this.mini;
    }

    @Override
    public void setSmall(boolean flag) {
        byte b0 = this.watcher.getByte(10);

        if (flag)
            b0 = (byte) (b0 | 0x1);
        else {
            b0 = (byte) (b0 & 0xFFFFFFFE);
        }
        this.watcher.setObject(10, Byte.valueOf(b0));
        this.mini = flag;
        if (!this.updateMetadata) {
            this.updateMetadata = true;
        }
    }

    @Override
    public boolean hasArms() {
        return this.arms;
    }

    @Override
    public void setArms(boolean flag) {
        byte b0 = this.watcher.getByte(10);

        if (flag)
            b0 = (byte) (b0 | 0x4);
        else {
            b0 = (byte) (b0 & 0xFFFFFFFB);
        }
        this.watcher.setObject(10, Byte.valueOf(b0));
        this.arms = flag;
        if (!this.updateMetadata) {
            this.updateMetadata = true;
        }
    }

    @Override
    public boolean hasBasePlate() {
        return basePlate;
    }

    @Override
    public void setBasePlate(boolean flag) {
        byte b0 = this.watcher.getByte(10);

        if (!flag)
            b0 = (byte) (b0 | 0x08);
        else {
            b0 = (byte) (b0 & 0xFFFFFFF7);
        }
        this.watcher.setObject(10, Byte.valueOf(b0));
        this.basePlate = flag;
        if (!this.updateMetadata) {
            this.updateMetadata = true;
        }
    }

    @Override
    public boolean hasGravity() {
        return gravity;
    }

    @Override
    public void setGravity(boolean flag) {
        byte b0 = this.watcher.getByte(10);

        if (flag)
            b0 = (byte) (b0 | 0x02);
        else {
            b0 = (byte) (b0 & 0xFFFFFFFD);
        }
        this.watcher.setObject(10, Byte.valueOf(b0));
        this.gravity = flag;
        if (!this.updateMetadata) {
            this.updateMetadata = true;
        }
    }

    public WrappedDataWatcher getDefaultWatcher(World world, EntityType type) {
        Entity entity = world.spawnEntity(new Location(world, 0, 256, 0), type);
        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();
        entity.remove();
        return watcher;
    }

    public ProtocolManager getProtocolManager() {
        return protocol;
    }

    @Override
    public void update(Collection<Player> list) {
        for (Player player : list) {
            if (player.isOnline()) {
                if (updateMetadata) {
                    updateMetadata(player);
                }

                if (updateRotation) {
                    updateRotation(player);
                }

                if (setPassenger) {
                    updatePassenger(player);
                }

                if (leavePassenger) {
                    PacketContainer packet = new PacketContainer(PacketType.Play.Server.ATTACH_ENTITY);
                    packet.getModifier().writeDefaults();
                    packet.getIntegers().write(0, 0).write(1, passenger.getEntityId()).write(2, -1);
                    sendPacket(player, packet);
                }
                getInventory().update(player);
            }
        }

        if (getPassenger() != null) {
            if (!getPassenger().isOnline()) {
                setPassenger(null);
            }
        }

        if (updateMetadata) {
            updateMetadata = false;
        }

        if (updateRotation) {
            updateRotation = false;
        }

        if (setPassenger) {
            setPassenger = false;
        }

        if (leavePassenger) {
            passenger = null;
            leavePassenger = false;
        }

    }

    @Override
    public void update() {
        update(getFurnitureObject().getPlayers());
    }

    @Override
    public void setYaw(float yaw) {
        this.getLocation().setYaw(yaw);
        if (!updateRotation) {
            this.updateRotation = true;
        }
    }

    @Override
    public float getYaw() {
        return getLocation().getYaw();
    }

    public void sendPacket(Player player, PacketContainer packet) {
        try {
            protocol.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IFurnitureManager getFurnitureManager() {
        return FurnitureLib.getFurnitureManager();
    }

    @Override
    public IFurnitureObject getFurnitureObject() {
        return object;
    }

    @Override
    public void setFurnitureObject(IFurnitureObject object) {
        this.object = object;
    }

    @Override
    public boolean hasFurnitureObject() {
        return object != null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
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
        if (!(obj instanceof FakeArmorStand)) {
            return false;
        }
        FakeArmorStand other = (FakeArmorStand) obj;
        if (displayName == null) {
            if (other.displayName != null) {
                return false;
            }
        } else if (!displayName.equals(other.displayName)) {
            return false;
        }
        if (entityId == null) {
            if (other.entityId != null) {
                return false;
            }
        } else if (!entityId.equals(other.entityId)) {
            return false;
        }
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        return true;
    }

    @Override
    public void setPassenger(Player entity) {
        if (entity == null && passenger != null) {
            this.leavePassenger = true;
        } else if (entity != null && passenger == null) {
            IFakeArmorStand stand = getFurnitureManager().getSitStand(entity);
            if (stand != null) {
                stand.setPassenger(null);
                return;
            }
            this.passenger = entity;
            this.setPassenger = true;
        }
    }

    @Override
    public Player getPassenger() {
        return passenger;
    }

    @Override
    public void updateRotation(Player player) {
        PacketContainer update = new PacketContainer(PacketType.Play.Server.ENTITY_MOVE_LOOK);
        update.getModifier().writeDefaults();
        update.getIntegers().write(0, getEntityID());
        update.getBytes().write(0, (byte) 0);
        update.getBytes().write(1, (byte) 0);
        update.getBytes().write(2, (byte) 0);
        update.getBytes().write(3, (byte) Utils.getCompressedAngle(getYaw()));
        update.getBytes().write(4, (byte) Utils.getCompressedAngle(0));// DEV
        sendPacket(player, update);
    }

    @Override
    public void updateMetadata(Player player) {
        PacketContainer update = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        update.getModifier().writeDefaults();
        update.getIntegers().write(0, getEntityID());
        update.getWatchableCollectionModifier().write(0, this.watcher.getWatchableObjects());
        sendPacket(player, update);
    }

    @Override
    public void updatePassenger(Player player) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ATTACH_ENTITY);
        packet.getModifier().writeDefaults();
        packet.getIntegers().write(0, 0).write(1, passenger.getEntityId()).write(2, getEntityID());
        sendPacket(player, packet);
    }

}