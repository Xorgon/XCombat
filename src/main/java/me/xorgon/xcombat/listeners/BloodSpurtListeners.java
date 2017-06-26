package me.xorgon.xcombat.listeners;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.ParticleEffect;
import me.xorgon.xcombat.XCManager;
import me.xorgon.xcombat.XCombat;
import me.xorgon.xcombat.effects.BloodSpurtEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

/**
 * Class with listeners for blood spurts.
 */
public class BloodSpurtListeners implements Listener {

    private XCombat plugin;
    private XCManager manager;

    private EffectManager effectManager;

    public BloodSpurtListeners(XCombat plugin) {
        this.plugin = plugin;
        this.manager = plugin.getManager();
        this.effectManager = plugin.getEffectManager();
    }

    //For Blood Spurts
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.SKELETON){
            entity.getWorld().playSound(entity.getLocation(), Sound.SKELETON_HURT, 100, 4);
        } else {
            entity.getWorld().playSound(entity.getLocation(), Sound.FALL_BIG, 100, 4);
        }
        if(event.isCancelled()){
            return;
        }
        EntityType eType = event.getEntity().getType();
        if (event.getDamager().getType() == EntityType.ARROW) {
            Arrow arrow = (Arrow) event.getDamager();
            Location arrowLoc = arrow.getLocation();
            Vector base = arrowLoc.getDirection();
            double damage = event.getDamage();
            base.multiply(damage / 10);
            BloodSpurtEffect effect = new BloodSpurtEffect(effectManager, plugin, base, 20.0, (int) Math.round(damage * 3));
            effect.setLocation(arrowLoc);
            if (eType == EntityType.SKELETON) {
                effect.particle = ParticleEffect.CLOUD;
            }
            if (eType == EntityType.CREEPER) {
                effect.color = Color.GREEN;
            }
            if (eType == EntityType.ENDER_DRAGON || eType == EntityType.ENDERMAN) {
                effect.color = Color.PURPLE;
            }
            if (eType == EntityType.SLIME){
                effect.color = Color.LIME;
            }
            if (eType == EntityType.WITHER){
                effect.color = Color.GRAY;
            }
            effect.run();
        } else {
            if (event.getDamager() instanceof LivingEntity) {
                LivingEntity damager = (LivingEntity) event.getDamager();
                Entity damaged = event.getEntity();
                //Calculating how far along the direction vector the target is.
                Vector dirVect = damager.getLocation().getDirection();
                Location rLoc = damager.getLocation();
                Location dLoc = damaged.getLocation();
                Double distL = dLoc.toVector().distance(rLoc.toVector());
                Double distD = distL / (Math.cos(Math.atan(dirVect.getY() / Math.sqrt(Math.pow(dirVect.getX(), 2) + Math.pow(dirVect.getZ(), 2)))));
                //Finding the approximate location of the hit.
                Location hitLoc = damager.getEyeLocation().add(dirVect.multiply(distD));
                Vector locDif = rLoc.toVector().subtract(dLoc.toVector());
                Vector base = (locDif.multiply(1 / locDif.length()));
                base = new Vector(base.getX(), base.getY() - 0.5, base.getZ());
                double damage = event.getDamage();
                base.multiply(damage / 10);
                BloodSpurtEffect effect = new BloodSpurtEffect(effectManager, plugin, base, 20.0, (int) Math.round(damage * 3));
                effect.setLocation(hitLoc);
                if (eType == EntityType.SKELETON) {
                    effect.particle = ParticleEffect.CLOUD;
                }
                if (eType == EntityType.CREEPER) {
                    effect.color = Color.GREEN;
                }
                if (eType == EntityType.ENDER_DRAGON || eType == EntityType.ENDERMAN) {
                    effect.color = Color.PURPLE;
                }
                if (eType == EntityType.SLIME){
                    effect.color = Color.LIME;
                }
                if (eType == EntityType.WITHER){
                    effect.color = Color.GRAY;
                }
                effect.run();
            }
        }
    }
}
