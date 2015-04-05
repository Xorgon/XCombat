package me.xorgon.xcombat.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class with utils pertaining to vectors.
 */
public class VectorUtils {

    public static Vector changePitch(Vector vector, Double offset) {
        double length = vector.length();
        //Changing from Pitch into vector.
        Double oldPitch = Math.asin(vector.getY() / length);
        Double newPitch = oldPitch + offset;
        //Changing from vector into Pitch.
        return new Vector(vector.getX(), length * Math.sin(newPitch), vector.getZ());
    }

    public static Vector changeYaw(Vector vector, Double offset) {
        double length = vector.length();
        double x = vector.getX();
        double z = vector.getZ();
        //Changing from vector into Yaw.
        Double oldYaw = Math.atan(-x / z);
        Double newYaw = oldYaw + offset;
        //Changing from Yaw into vector.
        double newX = -(length * Math.cos(Math.toRadians(newYaw)));
        double newZ = length * Math.sin(Math.toRadians(newYaw));
        return new Vector(newX, vector.getY(), newZ);
    }

    public static List<Vector> getVectorSpread(Vector vect, Double maxAngle, Integer k) {

        List<Vector> vectors = new ArrayList<>();

        Random rand = new Random();

        for (int n = 1; n <= k; n++) {
            //Gets a new yaw offset within limits and applies it to the vector.
            Double yawOff = rand.nextDouble() * maxAngle;
            Vector yawedVect = changeYaw(vect, yawOff);
            //Gets a new pitch offset within limits and applies it to the vector.
            Double pitchOff = rand.nextDouble() * maxAngle;
            Vector newVect = changePitch(yawedVect, pitchOff);
            vectors.add(newVect);
        }

        return vectors;
    }

}
