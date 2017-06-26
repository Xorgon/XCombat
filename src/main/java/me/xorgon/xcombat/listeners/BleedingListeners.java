package me.xorgon.xcombat.listeners;

import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.BleedEntityEffect;
import de.slikey.effectlib.particle.ParticlePacket;
import de.slikey.effectlib.util.ParticleEffect;
import me.xorgon.xcombat.XCManager;
import me.xorgon.xcombat.XCombat;
import me.xorgon.xcombat.effects.BleedingEffect;
import me.xorgon.xcombat.effects.BloodSpurtEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

/**
 * Class with listeners for entity bleeding.
 */
public class BleedingListeners implements Listener {

    private XCombat plugin;
    private XCManager manager;

    private EffectManager effectManager;

    public BleedingListeners(XCombat plugin) {
        this.plugin = plugin;
        this.manager = plugin.getManager();
        this.effectManager = plugin.getEffectManager();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        LivingEntity entity = (LivingEntity) event.getEntity();
        Double damage = entity.getMaxHealth() - entity.getHealth() + event.getDamage();
        if (manager.containsEntity(entity)) {
            manager.getBleedEffect(entity).cancel(false);
            manager.removeBleedEffect(entity);
        }
        if (entity.isDead()){
            return;
        }
        BleedingEffect effect = new BleedingEffect(effectManager, damage);
        effect.setEntity(entity);
        manager.addBleedEffect(entity, effect);
        effect.start();
    }

    @EventHandler
    public void onEntityHeal(EntityRegainHealthEvent event) {
        LivingEntity entity = (LivingEntity) event.getEntity();
        Double damage = entity.getMaxHealth() - entity.getHealth();
        if (manager.containsEntity(entity)) {
            manager.getBleedEffect(entity).cancel(false);
            manager.removeBleedEffect(entity);
        }
        if (damage != 1.0) {
            BleedingEffect effect = new BleedingEffect(effectManager, damage);
            effect.setEntity(entity);
            manager.addBleedEffect(entity, effect);
            effect.start();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player entity = event.getPlayer();
        Double damage = entity.getMaxHealth() - entity.getHealth();
        if (manager.containsEntity(entity)) {
            manager.getBleedEffect(entity).cancel(false);
            manager.removeBleedEffect(entity);
        }
        if (damage != 1.0) {
            BleedingEffect effect = new BleedingEffect(effectManager, damage);
            effect.setEntity(entity);
            manager.addBleedEffect(entity, effect);
            effect.start();
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player entity = event.getPlayer();
        if (manager.containsEntity(entity)){
            manager.getBleedEffect(entity).cancel(false);
            manager.removeBleedEffect(entity);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (manager.containsEntity(entity)) {
            manager.getBleedEffect(entity).cancel(false);
            manager.removeBleedEffect(entity);
        }
    }
}
