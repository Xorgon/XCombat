package me.xorgon.xcombat.effects;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.ParticleEffect;
import me.xorgon.xcombat.XCombat;
import me.xorgon.xcombat.util.VectorUtils;
import org.bukkit.Location;

import org.bukkit.util.Vector;

import java.util.List;

/**
 * Methods for the blood spurt from an arrow passing through the entity.
 */
public class BloodEffects {

    private EffectManager effectManager;

    public BloodEffects(XCombat plugin) {
        this.effectManager = plugin.getEffectManager();
    }

    /**
     * Method that makes a blood spurt.
     * @param location origin location of the blood spurt.
     * @param base the vector defining the centre line of the spurt.
     * @param sprayArcRadius the maximum angle from the base vector that individual spurts can be.
     * @param spurts the number of individual spurts.
     */
    public void bloodSpurtEffect(Location location, Vector base, Double sprayArcRadius, Integer spurts) {
        LineEffect line = new LineEffect(this.effectManager);
        line.isZigZag = false;
        line.setLocation(location);
        line.particles = 5;
        line.particle = ParticleEffect.REDSTONE;
        List<Vector> vectors =  VectorUtils.getVectorSpread(base, sprayArcRadius, spurts);

        //Running the spurt.
        for (Vector vector : vectors) {
            line.setTarget(vector.add(location.toVector()).toLocation(location.getWorld()));
            line.run();
        }
    }
}
