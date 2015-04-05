package me.xorgon.xcombat;

import de.slikey.effectlib.EffectManager;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import java.io.IOException;

/**
 * Main class for XCombat
 */

@Getter
@Setter
public class XCombat extends JavaPlugin {
    private XCManager manager;
    private EffectManager effectManager;
    private static XCombat instance;

    public XCombat() {
        instance = this;
        effectManager = new EffectManager(this);
    }

    @Override
    public void onEnable() {

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

    }

}
