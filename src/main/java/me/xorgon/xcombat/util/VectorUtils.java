package me.xorgon.xcombat.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class with utils pertaining to vectors.
 */
public class VectorUtils {

    private static Random rand = new Random();

    /**
     * Method used to get a vector with a changed pitch from the original vector.
     * @param vector the original vector.
     * @param offset the change in pitch.
     * @return vector with changed pitch.
     */
    public static Vector changePitch(Vector vector, Double offset) {
        double length = vector.length();
        //Changing from Pitch into vector.
        Double oldPitch = Math.asin(vector.getY() / length);
        Double newPitch = oldPitch + offset;
        //Changing from vector into Pitch.
        return new Vector(vector.getX(), length * Math.sin(Math.toRadians(newPitch)), vector.getZ());
    }

    /**
     * Method used to get a vector with a changed yaw from the original vector.
     * @param vector the original vector.
     * @param offset the change in yaw.
     * @return vector with changed yaw.
     */
    public static Vector changeYaw(Vector vector, Double offset) {
        double length = vector.length();
        double x = vector.getX();
        double z = vector.getZ();
        //Changing from vector into Yaw.
        double oldYaw;
        if(z == 0){
            oldYaw = 90.0;
        } else {
            oldYaw = Math.atan(-x / z);
        }
        double newYaw = oldYaw + offset;
        //Changing from Yaw into vector.
        double newX = -(length * Math.cos(Math.toRadians(newYaw)));
        double newZ = length * Math.sin(Math.toRadians(newYaw));
        return new Vector(newX, vector.getY(), newZ);
    }

    /**
     * Method to get a list of random vectors with a specified spread.
     * @param vect the base vector from which the other vectors are generated.
     * @param maxAngle the maximum spread of the vectors.
     * @param k the number of vectors to generate.
     * @return list of random vectors.
     */
    public static List<Vector> getVectorSpread(Vector vect, Double maxAngle, Integer k) {

        List<Vector> vectors = new ArrayList<>();

        for (int n = 1; n <= k; n++) {
            //Gets a new yaw offset within limits and applies it to the vector.
            Double yawOff = (rand.nextDouble() * 2 - 1) * maxAngle;
            Vector yawedVect = changeYaw(vect, yawOff);
            //Gets a new pitch offset within limits and applies it to the vector.
            Double pitchOff = (rand.nextDouble() * 2 - 1 ) * maxAngle;
            Vector newVect = changePitch(yawedVect, pitchOff);
            vectors.add(newVect);
        }

        return vectors;
    }

}
