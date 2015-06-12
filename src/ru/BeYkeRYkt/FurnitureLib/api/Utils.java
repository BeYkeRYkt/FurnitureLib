package ru.BeYkeRYkt.FurnitureLib.api;

import java.lang.reflect.Constructor;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Utils {

    private final static BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    // private List<BlockFace> axisList = Arrays.asList(BlockFace.NORTH,
    // BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
    private final static BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };
    private static Random rand = new Random();

    // Return the all BlockFaces with 45 degress
    public static BlockFace yawToFaceRadial(float yaw) {
        return radial[Math.round(yaw / 45f) & 0x7];
    }

    // Return the all BlockFaces with 90 degress
    public static BlockFace yawToFace(float yaw) {
        return axis[Math.round(yaw / 90f) & 0x3];
    }

    public static BlockFace yawToFace(float yaw, float pitch) {
        if (pitch < -80) {
            return BlockFace.UP;
        } else if (pitch > 80) {
            return BlockFace.DOWN;
        }
        return axis[Math.round(yaw / 90f) & 0x3];
    }

    public static EulerAngle degresstoRad(EulerAngle degressAngle) {
        return new EulerAngle(degressAngle.getX() * Math.PI / 180, degressAngle.getY() * Math.PI / 180, degressAngle.getZ() * Math.PI / 180);
    }

    public static EulerAngle Radtodegress(EulerAngle degressAngle) {
        return new EulerAngle(degressAngle.getX() * 180 / Math.PI, degressAngle.getY() * 180 / Math.PI, degressAngle.getZ() * 180 / Math.PI);
    }

    public static int FaceToYaw(final BlockFace face) {
        switch (face) {
            case NORTH:
                return 0;
            case NORTH_EAST:
                return 45;
            case EAST:
                return 90;
            case SOUTH_EAST:
                return 135;
            case SOUTH:
                return 180;
            case SOUTH_WEST:
                return 225;
            case WEST:
                return 270;
            case NORTH_WEST:
                return 315;
            default:
                return 0;
        }
    }

    public static boolean isDay(World w) {
        long time = w.getTime();

        if (time > 0 && time < 12300) {
            return true;
        } else {
            return false;
        }
    }

    public Vector getRelativ(Vector v1, Double x, BlockFace bf) {
        switch (bf) {
            case NORTH:
                v1.add(new Vector(0, 0, x));
                break;
            case EAST:
                v1.add(new Vector(x, 0, 0));
                break;
            case SOUTH:
                v1.add(new Vector(0, 0, -x));
                break;
            case WEST:
                v1.add(new Vector(x, 0, 0));
                break;
            case DOWN:
                v1.add(new Vector(0, -x, 0));
                break;
            case UP:
                v1.add(new Vector(0, x, 0));
                break;
            default:
                v1.add(new Vector(x, 0, 0));
                break;
        }

        return v1;
    }

    public static BlockFace StringToFace(final String face) {
        switch (face) {
            case "NORTH":
                return BlockFace.NORTH;
            case "EAST":
                return BlockFace.EAST;
            case "SOUTH":
                return BlockFace.SOUTH;
            case "WEST":
                return BlockFace.WEST;
            case "UP":
                return BlockFace.UP;
            case "DOWN":
                return BlockFace.DOWN;
            case "NORTH_NORTH_EAST":
                return BlockFace.NORTH_NORTH_EAST;
            case "NORTH_NORTH_WEST":
                return BlockFace.NORTH_NORTH_WEST;
            case "NORTH_WEST":
                return BlockFace.NORTH_WEST;
            case "EAST_NORTH_EAST":
                return BlockFace.EAST_NORTH_EAST;
            case "EAST_SOUTH_EAST":
                return BlockFace.EAST_SOUTH_EAST;
            case "SOUTH_EAST":
                return BlockFace.SOUTH_EAST;
            case "SOUTH_SOUTH_EAST":
                return BlockFace.SOUTH_SOUTH_EAST;
            case "SOUTH_SOUTH_WEST":
                return BlockFace.SOUTH_SOUTH_WEST;
            case "SOUTH_WEST":
                return BlockFace.SOUTH_WEST;
            case "WEST_NORTH_WEST":
                return BlockFace.WEST_NORTH_WEST;
            case "WEST_SOUTH_WEST":
                return BlockFace.WEST_SOUTH_WEST;
            default:
                return BlockFace.NORTH;
        }
    }

    @SuppressWarnings("deprecation")
    public static Block setSign(BlockFace face, Location l) {
        l.getBlock().setType(Material.AIR);
        l.getBlock().setType(Material.WALL_SIGN);
        Block block = l.getBlock();
        BlockState state = l.getBlock().getState();
        state.setRawData(getFacebyte(yawToFace(FaceToYaw(face.getOppositeFace()) - 90)));
        state.update(false);
        return block;
    }

    public static byte getFacebyte(BlockFace b) {
        switch (b) {
            case NORTH:
                return 0x4;
            case EAST:
                return 0x2;
            case SOUTH:
                return 0x5;
            case WEST:
                return 0x3;
            default:
                return 0x5;
        }
    }

    @SuppressWarnings("deprecation")
    public static void setBed(BlockFace face, Location l) {
        if (face == BlockFace.NORTH) {
            l.getBlock().setType(Material.AIR);
            l.getBlock().setType(Material.BED_BLOCK);
            Block block = l.getBlock();
            BlockState bedFoot = block.getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.SOUTH).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 0);
            bedHead.setRawData((byte) 8);
            bedFoot.update(true, false);
            bedHead.update(true, true);
        } else if (face == BlockFace.EAST) {
            l.getBlock().setType(Material.AIR);
            l.getBlock().setType(Material.BED_BLOCK);
            Block block = l.getBlock();
            BlockState bedFoot = block.getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.WEST).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 1);
            bedHead.setRawData((byte) 9);
            bedFoot.update(true, false);
            bedHead.update(true, true);
        } else if (face == BlockFace.SOUTH) {
            l.getBlock().setType(Material.AIR);
            l.getBlock().setType(Material.BED_BLOCK);
            Block block = l.getBlock();
            BlockState bedFoot = block.getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.NORTH).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 2);
            bedHead.setRawData((byte) 10);
            bedFoot.update(true, false);
            bedHead.update(true, true);
        } else if (face == BlockFace.WEST) {
            l.getBlock().setType(Material.AIR);
            l.getBlock().setType(Material.BED_BLOCK);
            Block block = l.getBlock();
            BlockState bedFoot = block.getState();
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.EAST).getState();
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 3);
            bedHead.setRawData((byte) 11);
            bedFoot.update(true, false);
            bedHead.update(true, true);
        }
    }

    @SuppressWarnings("deprecation")
    public static Block setHalfBed(BlockFace face, Location l) {
        if (face == BlockFace.NORTH) {
            Block block = l.getBlock();
            BlockState bedHead = block.getState();
            bedHead.setType(Material.BED_BLOCK);
            bedHead.setRawData((byte) 9);
            bedHead.update(true, false);
            return block;
        } else if (face == BlockFace.EAST) {
            Block block = l.getBlock();
            BlockState bedHead = block.getState();
            bedHead.setType(Material.BED_BLOCK);
            bedHead.setRawData((byte) 10);
            bedHead.update(true, false);
            return block;
        } else if (face == BlockFace.SOUTH) {
            Block block = l.getBlock();
            BlockState bedHead = block.getState();
            bedHead.setType(Material.BED_BLOCK);
            bedHead.setRawData((byte) 11);
            bedHead.update(true, false);
            return block;
        } else if (face == BlockFace.WEST) {
            Block block = l.getBlock();
            BlockState bedHead = block.getState();
            bedHead.setType(Material.BED_BLOCK);
            bedHead.setRawData((byte) 8);
            bedHead.update(true, false);
            return block;
        }
        return null;
    }

    public static Location getRelative(Location loc, BlockFace b, Double z, Double x) {
        Location l = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        l.setYaw(FaceToYaw(b));
        switch (b) {
            case NORTH:
                l.add(x, 0, z);
                break;
            case SOUTH:
                l.add(-x, 0, -z);
                break;
            case WEST:
                l.add(z, 0, -x);
                break;
            case EAST:
                l.add(-z, 0, x);
                break;
            case NORTH_EAST:
                l.add(x, 0, z);
                l.add(-z, 0, x);
                break;
            case NORTH_NORTH_EAST:
                l.add(x, 0, z);
                l.add(x, 0, z);
                l.add(-z, 0, x);
                break;
            case NORTH_NORTH_WEST:
                l.add(x, 0, z);
                l.add(x, 0, z);
                l.add(z, 0, -x);
                break;
            case NORTH_WEST:
                l.add(x, 0, z);
                l.add(z, 0, -x);
                break;
            case EAST_NORTH_EAST:
                l.add(-z, 0, x);
                l.add(x, 0, z);
                l.add(-z, 0, x);
                break;
            case EAST_SOUTH_EAST:
                l.add(-z, 0, x);
                l.add(-x, 0, -z);
                l.add(-z, 0, x);
                break;
            case SOUTH_EAST:
                l.add(-x, 0, -z);
                l.add(-z, 0, x);
                break;
            case SOUTH_SOUTH_EAST:
                l.add(-x, 0, -z);
                l.add(-x, 0, -z);
                l.add(-z, 0, x);
                break;
            case SOUTH_SOUTH_WEST:
                l.add(-x, 0, -z);
                l.add(-x, 0, -z);
                l.add(z, 0, -x);
                break;
            case SOUTH_WEST:
                l.add(-x, 0, -z);
                l.add(z, 0, -x);
                break;
            case WEST_NORTH_WEST:
                l.add(z, 0, -x);
                l.add(x, 0, z);
                l.add(z, 0, -x);
                break;
            case WEST_SOUTH_WEST:
                l.add(z, 0, -x);
                l.add(-x, 0, -z);
                l.add(z, 0, -x);
                break;
            case DOWN:
                l.add(0, -z, 0);
                break;
            case UP:
                l.add(0, z, 0);
            default:
                l.add(x, 0, z);
                break;
        }
        return l;
    }

    public static int randInt(int min, int max) {
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static EulerAngle FaceEuler(final BlockFace face, Double x, Double y, Double z) {
        return new EulerAngle(x, y, z);
    }

    public static Location getLocationCopy(Location l) {
        return new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
    }

    public static Location getNew(Location loc, BlockFace b, Double z, Double x) {
        Location l = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        if (b.equals(BlockFace.NORTH)) {
            l.add(x, 0, z);
        }

        if (b.equals(BlockFace.SOUTH)) {
            l.add(-x, 0, -z);
        }
        if (b.equals(BlockFace.EAST)) {
            l.add(-z, 0, x);
        }
        if (b.equals(BlockFace.WEST)) {
            l.add(z, 0, -x);
        }

        if (b.equals(BlockFace.NORTH_EAST)) {
            l.add(x, 0, z);
            l.add(-z, 0, x);
        }

        if (b.equals(BlockFace.NORTH_WEST)) {
            l.add(x, 0, z);
            l.add(z, 0, -x);
        }

        if (b.equals(BlockFace.SOUTH_EAST)) {
            l.add(-x, 0, -z);
            l.add(-z, 0, x);
        }

        if (b.equals(BlockFace.SOUTH_WEST)) {
            l.add(-x, 0, -z);
            l.add(z, 0, -x);
        }

        if (b.equals(BlockFace.UP)) {
            l.add(0, z + x, 0);
        }

        if (b.equals(BlockFace.DOWN)) {
            l.add(0, -z - x, 0);
        }

        return l;
    }

    public static Location getCenter(Location loc, boolean needCenterY) {
        double x = getRelativeCoord(loc.getBlockX());
        double y = loc.getBlockY();
        if (needCenterY) {
            y = getRelativeCoord(loc.getBlockY());
        }
        double z = getRelativeCoord(loc.getBlockZ());

        Location location = new Location(loc.getWorld(), x, y, z);
        location.setYaw(loc.getYaw());
        location.setPitch(loc.getPitch());
        return location;
    }

    private static double getRelativeCoord(int i) {
        double d = i;
        if (d < 0) {
            d += .5;
        } else {
            d += .5;
        }
        return d;
    }

    public static int getFixedPoint(Double d) {
        return (int) (d * 32D);
    }

    public static int getCompressedAngle(float value) {
        return (int) (value * 256.0F / 360.0F);
    }

    public static String getBukkitVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static Object getVectorf3(EulerAngle angle) {
        try {
            Class<?> Vector3f = Class.forName("net.minecraft.server." + getBukkitVersion() + ".Vector3f");
            Constructor<?> ctor = Vector3f.getConstructors()[0];
            // return ctor.newInstance((float) Math.toDegrees(angle.getX()),
            // (float) Math.toDegrees(angle.getY()), (float)
            // Math.toDegrees(angle.getZ()));
            return ctor.newInstance((float) angle.getX(), (float) angle.getY(), (float) angle.getZ());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}