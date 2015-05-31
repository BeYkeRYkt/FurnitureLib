package de.Ste3et_C0st.FurnitureLib.implementation.armorstands;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

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

import de.Ste3et_C0st.FurnitureLib.FurnitureLib;
import de.Ste3et_C0st.FurnitureLib.api.IFurnitureManager;
import de.Ste3et_C0st.FurnitureLib.api.Utils;
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
    private float yaw;

    private boolean updateMetadata;
    private boolean updateRotation;

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

    @Override
    public void spawn() {
        for (Player player : getLocation().getWorld().getPlayers()) {
            if (isInRange(player)) {
                PacketContainer container = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
                container.getIntegers().write(0, this.entityId).write(1, (int) EntityType.ARMOR_STAND.getTypeId()).write(2, Utils.getFixedPoint(this.location.getX())).write(3, Utils.getFixedPoint(this.location.getY())).write(4, Utils.getFixedPoint(this.location.getZ())).write(5, 0).write(6, 0).write(7, 0);
                container.getBytes().write(0, (byte) Utils.getCompressedAngle(getYaw())).write(1, (byte) Utils.getCompressedAngle(0)).write(2, (byte) Utils.getCompressedAngle(getYaw()));
                container.getDataWatcherModifier().write(0, this.watcher);
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
            //if (isInRange(player)) {
                PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
                destroy.getIntegerArrays().write(0, new int[] { this.entityId });
                try {
                    protocol.sendServerPacket(player, destroy);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            //}
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

    @Override
    public boolean isInRange(Player player) {
        return getLocation().getWorld() == player.getLocation().getWorld() && (getLocation().distance(player.getLocation()) <= 48D);
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
        for (Player player : getLocation().getWorld().getPlayers()) {
            if (isInRange(player)) {
                if (updateMetadata) {
                    PacketContainer update = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                    update.getIntegers().write(0, getEntityID());
                    update.getWatchableCollectionModifier().write(0, this.watcher.getWatchableObjects());
                    sendPacket(player, update);
                }

                if (updateRotation) {
                    PacketContainer update = new PacketContainer(PacketType.Play.Server.ENTITY_MOVE_LOOK);
                    update.getIntegers().write(0, getEntityID());
                    update.getBytes().write(0, (byte) 0);
                    update.getBytes().write(1, (byte) 0);
                    update.getBytes().write(2, (byte) 0);
                    update.getBytes().write(3, (byte) Utils.getCompressedAngle(getYaw()));
                    update.getBytes().write(4, (byte) Utils.getCompressedAngle(0));// DEV
                    sendPacket(player, update);
                }
                getInventory().update(player);
            }
        }
        updateMetadata = false;
        updateRotation = false;
    }

    @Override
    public void update() {
        update(getLocation().getWorld().getPlayers());
    }

    @Override
    public void setYaw(float yaw) {
        this.yaw = yaw;
        if (!updateRotation) {
            this.updateRotation = true;
        }
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    public void sendPacket(Player player, PacketContainer packet) {
        try {
            protocol.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (arms ? 1231 : 1237);
        result = prime * result + (basePlate ? 1231 : 1237);
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
        result = prime * result + (fire ? 1231 : 1237);
        result = prime * result + (gravity ? 1231 : 1237);
        result = prime * result + ((inv == null) ? 0 : inv.hashCode());
        result = prime * result + (invisible ? 1231 : 1237);
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + (mini ? 1231 : 1237);
        result = prime * result + (nameVisible ? 1231 : 1237);
        result = prime * result + Float.floatToIntBits(yaw);
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
        if (arms != other.arms) {
            return false;
        }
        if (basePlate != other.basePlate) {
            return false;
        }
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
        if (fire != other.fire) {
            return false;
        }
        if (gravity != other.gravity) {
            return false;
        }
        if (inv == null) {
            if (other.inv != null) {
                return false;
            }
        } else if (!inv.equals(other.inv)) {
            return false;
        }
        if (invisible != other.invisible) {
            return false;
        }
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        if (mini != other.mini) {
            return false;
        }
        if (nameVisible != other.nameVisible) {
            return false;
        }
        if (Float.floatToIntBits(yaw) != Float.floatToIntBits(other.yaw)) {
            return false;
        }
        return true;
    }

    @Override
    public IFurnitureManager getFurnitureManager() {
        return FurnitureLib.getFurnitureManager();
    }
}