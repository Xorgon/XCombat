package me.xorgon.xcombat.listeners;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.LineEffect;
import me.xorgon.xcombat.XCManager;
import me.xorgon.xcombat.XCombat;
import me.xorgon.xcombat.effects.BloodSpurtEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

/**
 * Class with listeners for arrows going through targets.
 */
public class ArrowThruListeners implements Listener {

    private XCombat plugin;
    private XCManager manager;
    private EffectManager effectManager;

    public ArrowThruListeners(XCombat plugin) {
        this.plugin = plugin;
        this.manager = plugin.getManager();
        this.effectManager = plugin.getEffectManager();
    }

    @EventHandler()
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.ARROW) {
            if (event.getDamage() >= 10) {
                Arrow arrow = (Arrow) event.getDamager();
                ProjectileSource pSShooter = arrow.getShooter();
                if (pSShooter instanceof LivingEntity) {
                    LivingEntity shooter = (LivingEntity) pSShooter;
                    Double dmg = event.getDamage();
                    event.setCancelled(true);
                    LivingEntity entity = (LivingEntity) event.getEntity();
                    entity.damage(dmg);
                    World world = entity.getWorld();
                    Location arrowLoc = arrow.getLocation();
                    Vector arrowDir = arrowLoc.getDirection();
                    Location entityLoc = entity.getLocation();
                    Vector shooterLoc = shooter.getLocation().toVector();

                    Vector vect = entityLoc.clone().subtract(shooterLoc.getX(), entityLoc.getY(), shooterLoc.getZ()).toVector();
                    vect.multiply(1 / vect.length());

                    Location loc = entityLoc.clone().add(0,(arrowLoc.getY() - entityLoc.getY()),0);

                    /*
                    Arrow newArrow = (Arrow) world.spawnEntity(loc, EntityType.ARROW);
                    newArrow.setShooter(shooter);
                    newArrow.setVelocity(vect.clone().multiply(2));
                    */

                    Vector base = vect.clone().multiply(dmg / 10);

                    BloodSpurtEffect effect = new BloodSpurtEffect(effectManager, plugin, base, 10.0, (int) Math.round(dmg * 3));
                    effect.setLocation(loc);
                    effect.run();
                    arrow.remove();

                }
            }
        }
    }
}
