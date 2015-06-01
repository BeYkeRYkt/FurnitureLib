package ru.BeYkeRYkt.FurnitureLib.api;

public class Utils {

    public static int getFixedPoint(Double d) {
        return (int) (d * 32D);
    }

    public static int getCompressedAngle(float value) {
        return (int) (value * 256.0F / 360.0F);
    }

}