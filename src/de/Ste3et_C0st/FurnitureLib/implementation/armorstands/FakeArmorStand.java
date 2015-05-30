package de.Ste3et_C0st.FurnitureLib.implementation.armorstands;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import de.Ste3et_C0st.FurnitureLib.api.armorstands.ArmorBodyPart;
import de.Ste3et_C0st.FurnitureLib.api.armorstands.IArmorStandInventory;
import de.Ste3et_C0st.FurnitureLib.api.armorstands.IFakeArmorStand;

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

    public FakeArmorStand(Location loc, int EntityId) {
        this.location = loc;
        this.ea = new EulerAngle[6];
        this.entityId = EntityId;
        this.protocol = ProtocolLibrary.getProtocolManager();
        this.watcher = getDefaultWatcher(loc.getWorld(), EntityType.ARMOR_STAND);
        this.inv = new ArmorStandInventory(this);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Chunk getChunk() {
        return getLocation().getChunk();
    }

    private int getFixedPoint(Double d) {
        return (int) (d * 32D);
    }

    private int getCompressedAngle(float value) {
        return (int) (value * 256.0F / 360.0F);
    }

    @Override
    public void spawn() {
        for (Player player : getLocation().getWorld().getPlayers()) {
            if (isInRange(player)) {
                PacketContainer container = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
                container.getIntegers().write(0, this.entityId).write(1, (int) EntityType.ARMOR_STAND.getTypeId()).write(2, getFixedPoint(this.location.getX())).write(3, getFixedPoint(this.location.getY())).write(4, getFixedPoint(this.location.getZ())).write(5, getCompressedAngle(this.location.getYaw())).write(6, getCompressedAngle(this.location.getYaw())).write(7, 0);
                try {
                    protocol.sendServerPacket(player, container);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void despawn() {
        for (Player player : getLocation().getWorld().getPlayers()) {
            if (isInRange(player)) {
                PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
                destroy.getIntegerArrays().write(0, new int[] { this.entityId });
                try {
                    protocol.sendServerPacket(player, destroy);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
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
    }

    @Override
    public boolean isNameVisible() {
        return this.nameVisible;
    }

    @Override
    public void setNameVisible(boolean flag) {
        this.nameVisible = flag;
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
    }

    @Override
    public boolean isMini() {
        return this.mini;
    }

    @Override
    public void setMini(boolean flag) {
        byte b0 = this.watcher.getByte(10);

        if (flag)
            b0 = (byte) (b0 | 0x1);
        else {
            b0 = (byte) (b0 & 0xFFFFFFFE);
        }
        this.watcher.setObject(10, Byte.valueOf(b0));
        this.mini = flag;
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
    }

    @Override
    public boolean hasBasePlate() {
        return basePlate;
    }

    @Override
    public void setBasePlate(boolean flag) {
        byte b0 = this.watcher.getByte(10);

        if (flag)
            b0 = (byte) (b0 | 0x08);
        else {
            b0 = (byte) (b0 & 0xFFFFFFF7);
        }
        this.watcher.setObject(10, Byte.valueOf(b0));
        this.basePlate = flag;
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
    }

    @Override
    public boolean isInRange(Player player) {
        return getLocation().getWorld() == player.getLocation().getWorld() && (getLocation().distance(player.getLocation()) <= 48D);
    }

    @Override
    public void setYaw(Player player, double yaw) {
        try {
            PacketContainer packet = protocol.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
            packet.getIntegers().write(0, this.entityId);
            packet.getBytes().write(0, (byte) getCompressedAngle(getLocation().getYaw()));
            player.sendMessage(getCompressedAngle(getLocation().getYaw()) + "");
            protocol.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
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
    public void update(Player player) {
        try {
            PacketContainer update = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
            update.getIntegers().write(0, getEntityID());
            update.getWatchableCollectionModifier().write(0, this.watcher.getWatchableObjects());
            protocol.sendServerPacket(player, update);
            getInventory().update(player);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}