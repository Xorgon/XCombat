package me.xorgon.xcombat;

import me.xorgon.xcombat.effects.BleedingEffect;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for XCombat
 */
public class XCManager {

    private boolean collectStats;

    private boolean arrowThru;
    private boolean bleeding;
    private boolean bloodSpurts;
    private boolean improvedBlocking;

    private Map<Entity, BleedingEffect> bleedEffects;
    private Map<Player, Long> blockTimer;

    private YamlConfiguration config;
    private File file;
    private XCombat plugin;

    public XCManager(XCombat plugin) {
        this.plugin = plugin;
        bleedEffects = new HashMap<>();
        blockTimer = new HashMap<>();
        file = new File(plugin.getDataFolder(), "config.yml");
    }

    public void load(){
        config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()){
            collectStats = true;
            arrowThru = true;
            bleeding = true;
            bloodSpurts = true;
            improvedBlocking = true;
        }
        if(config.get("collectStats") == null){
            config.set("collectStats", true);
            collectStats = true;
        } else {
            collectStats = config.getBoolean("collectStats");
        }
        if(config.get("arrowThru") == null){
            config.set("arrowThru", true);
            arrowThru = true;
        } else {
            arrowThru = config.getBoolean("arrowThru");
        }
        if(config.get("bleeding") == null){
            config.set("bleeding", true);
            bleeding = true;
        } else {
            bleeding = config.getBoolean("bleeding");
        }
        if(config.get("bloodSpurts") == null){
            config.set("bloodSpurts", true);
            bloodSpurts = true;
        } else {
            bloodSpurts = config.getBoolean("bloodSpurts");
        }
        if(config.get("improvedBlocking") == null){
            config.set("improvedBlocking", true);
            improvedBlocking = true;
        } else {
            improvedBlocking = config.getBoolean("improvedBlocking");
        }
        try {
            config.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isCollectStats() {
        return collectStats;
    }

    public void addBleedEffect(Entity entity, BleedingEffect effect){
        bleedEffects.put(entity, effect);
    }

    public void removeBleedEffect(Entity entity){
        bleedEffects.remove(entity);
    }

    public BleedingEffect getBleedEffect(Entity entity){
        return bleedEffects.get(entity);
    }

    public boolean containsEntity(Entity entity){
        return bleedEffects.containsKey(entity);
    }

    public void addBlockTimer (Player player, Long init){
        blockTimer.put(player, init);
    }

    public void removeBlockTimer (Player player){
        blockTimer.remove(player);
    }

    public Long getBlockTimer (Player player){
        return blockTimer.get(player);
    }

    public boolean containsBlockTimer(Player player){
        return blockTimer.containsKey(player);
    }

    public boolean isArrowThru() {
        return arrowThru;
    }

    public boolean isBleeding() {
        return bleeding;
    }

    public boolean isBloodSpurts() {
        return bloodSpurts;
    }

    public boolean isImprovedBlocking() {
        return improvedBlocking;
    }
}
