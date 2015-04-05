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

    //Effect used for blood spurts.
    public void bloodSpurtEffect(Location location, Vector direction, Double sprayArcRadius, Integer spurts) {
        LineEffect line = new LineEffect(this.effectManager);
        line.isZigZag = false;
        line.setLocation(location);
        line.particles = 5;
        line.particle = ParticleEffect.REDSTONE;
        List<Vector> vectors =  VectorUtils.getVectorSpread(direction, sprayArcRadius, spurts);

        for (Vector vector : vectors) {
            line.setTarget(vector.add(location.toVector()).toLocation(location.getWorld()));
            line.run();
        }
    }

}
