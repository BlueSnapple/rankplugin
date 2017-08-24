package me.bluesnapple.rankplugin;

import me.bluesnapple.rankplugin.config.RankConfig;
import me.bluesnapple.rankplugin.events.ChatManager;
import me.bluesnapple.rankplugin.events.RankManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Coding on 8/23/17.
 */
public class Ranks extends JavaPlugin{

    @Override
    public void onEnable(){
        //Shows enabled message
        getLogger().info(getDescription().getName() + " was enabled!");

        //Enables config file
        new RankConfig(this);

        //Registers events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RankManager(this), this);
        pm.registerEvents(new ChatManager(), this);

    }

    @Override
    public void onDisable(){
        //Shows disable message
        getLogger().info(getDescription().getName() + " was disabled!");
    }

}
