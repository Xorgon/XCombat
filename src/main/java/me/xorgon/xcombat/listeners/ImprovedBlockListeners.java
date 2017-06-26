package me.xorgon.xcombat.listeners;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.LineEffect;
import de.slikey.effectlib.util.ParticleEffect;
import me.xorgon.xcombat.XCManager;
import me.xorgon.xcombat.XCombat;
import me.xorgon.xcombat.effects.BloodSpurtEffect;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

/**
 * Class with listeners for improved blocking.
 */
public class ImprovedBlockListeners implements Listener {

    private XCombat plugin;
    private XCManager manager;
    private EffectManager effectManager;

    public ImprovedBlockListeners(XCombat plugin) {
        this.plugin = plugin;
        this.manager = plugin.getManager();
        this.effectManager = plugin.getEffectManager();
    }

    @EventHandler
    public void onBlock(PlayerInteractEvent event) {
        Material item = event.getPlayer().getItemInHand().getType();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK
                || event.getAction() == Action.RIGHT_CLICK_AIR)
                && (item == Material.DIAMOND_SWORD
                || item == Material.GOLD_SWORD
                || item == Material.IRON_SWORD
                || item == Material.STONE_SWORD
                || item == Material.WOOD_SWORD)) {
            Long time = System.currentTimeMillis();
            manager.addBlockTimer(event.getPlayer(), time);
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if ((event.getEntity() instanceof Player) && !(event.getDamager() instanceof Projectile)) {
            Player player = (Player) event.getEntity();
            if (player.isBlocking()) {
                long start = manager.getBlockTimer(player);
                long now = System.currentTimeMillis();
                long time = now - start;
                double damFact = -1 / ((((double) time) / 1000) + 1) + 1;
                double damage = event.getDamage() * damFact;
                if (damage <= 1){
                    player.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 100, 4);
                }
                event.setDamage(damage);
            }
        }
    }

    @EventHandler
    public void onPlayerUnblock(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (manager.containsBlockTimer(player) && !(player.isBlocking())) {
            manager.removeBlockTimer(player);
        }
    }
}
