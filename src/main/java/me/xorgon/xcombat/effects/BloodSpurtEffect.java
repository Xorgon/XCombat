package me.xorgon.xcombat.effects;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.ParticleEffect;
import me.xorgon.xcombat.XCombat;
import me.xorgon.xcombat.util.VectorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;

import org.bukkit.util.Vector;

import java.util.List;

/**
 * Methods for the blood spurt from an arrow passing through the entity.
 */
public class BloodSpurtEffect extends LineEffect{

    private List<Vector> vectors;

    private Vector base;
    private Double sprayArcRadius;
    private Integer spurts;

    public BloodSpurtEffect(EffectManager effectManager, XCombat plugin, Vector base, Double sprayArcRadius, Integer spurts) {
        super(effectManager);
        this.base = base;
        this.sprayArcRadius = sprayArcRadius;
        this.spurts = spurts;
        vectors = VectorUtils.getVectorSpread(base, sprayArcRadius, spurts);
        isZigZag = false;
        particles = (int) Math.round(base.length() * 5);
        particle = ParticleEffect.REDSTONE;
        type = EffectType.REPEATING;
    }

    @Override
    public void onRun(){
        Location location = getLocation();
        double amount = particles / zigZags;
        if (vectors == null) {
            cancel();
            return;
        }

        for (Vector link : vectors) {
            float length = (float) link.length();
            link.normalize();
            float ratio = length / particles;
            Vector v = link.multiply(ratio);
            Location loc = location.clone().subtract(v);
            for (int i = 0; i < particles; i++) {
                if (isZigZag) {
                    if (zag)
                        loc.add(0, .1, 0);
                    else
                        loc.subtract(0, .1, 0);
                }
                if (step >= amount) {
                    if (zag)
                        zag = false;
                    else
                        zag = true;
                    step = 0;
                }
                step++;
                loc.add(v);
                display(particle, loc);
            }
        }
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    public void setVectors(List<Vector> vectors) {
        this.vectors = vectors;
    }

    public Vector getBase() {
        return base;
    }

    public void setBase(Vector base) {
        this.base = base;
    }

    public Double getSprayArcRadius() {
        return sprayArcRadius;
    }

    public void setSprayArcRadius(Double sprayArcRadius) {
        this.sprayArcRadius = sprayArcRadius;
    }

    public Integer getSpurts() {
        return spurts;
    }

    public void setSpurts(Integer spurts) {
        this.spurts = spurts;
        iterations = spurts;
        period = 0;
    }
}
