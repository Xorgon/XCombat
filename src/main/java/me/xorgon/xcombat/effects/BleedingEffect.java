package me.xorgon.xcombat.effects;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * An effect that drops blood periodically.
 */
public class BleedingEffect extends Effect {

    ParticleEffect particle;
    Double damage;

    public BleedingEffect(EffectManager effectManager, Double damage) {
        super(effectManager);
        infinite();
        this.damage = damage;
        particle = ParticleEffect.DRIP_LAVA;
        period = (int) Math.round(50 / damage);
        callback = new Runnable() {
            @Override
            public void run() {
            }
        };
    }

    public void onRun() {
        Entity entity = getEntity();
        for (int n = 1; n <= damage; n++) {
            display(particle, entity.getLocation().add(0,0.1,0));
        }
    }
}
