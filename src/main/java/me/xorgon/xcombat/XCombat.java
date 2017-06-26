package me.xorgon.xcombat;

import de.slikey.effectlib.EffectManager;
import me.xorgon.xcombat.listeners.ArrowThruListeners;
import me.xorgon.xcombat.listeners.BleedingListeners;
import me.xorgon.xcombat.listeners.BloodSpurtListeners;
import me.xorgon.xcombat.listeners.ImprovedBlockListeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import java.io.IOException;

/**
 * Main class for XCombat
 */

public class XCombat extends JavaPlugin {
    private XCManager manager;
    private EffectManager effectManager;
    private static XCombat instance;

    public XCombat() {
        instance = this;

    }

    @Override
    public void onEnable() {

        effectManager = new EffectManager(this);
        this.manager = new XCManager(this);
        manager.load();

        //Listener registration.
        if (manager.isImprovedBlocking()) {
            Bukkit.getPluginManager().registerEvents(new ImprovedBlockListeners(this), this);
        }
        if (manager.isBleeding()) {
            Bukkit.getPluginManager().registerEvents(new BleedingListeners(this), this);
        }
        if (manager.isArrowThru()) {
            Bukkit.getPluginManager().registerEvents(new ArrowThruListeners(this), this);
        }
        if (manager.isBloodSpurts()) {
            Bukkit.getPluginManager().registerEvents(new BloodSpurtListeners(this), this);
        }

        if (manager.isCollectStats()) {
            try {
                MetricsLite metrics = new MetricsLite(this);
                metrics.start();
            } catch (IOException e) {
                // Failed to submit the stats :-(
                System.out.println("MetricsLite has failed to submit stats.");
            }
        }
    }

    @Override
    public void onDisable() {
        effectManager.dispose();
    }

    public static XCombat getInstance() {
        return instance;
    }

    public XCManager getManager() {
        return manager;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }
}
