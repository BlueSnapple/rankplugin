package me.bluesnapple.rankplugin.config;

import me.bluesnapple.rankplugin.Ranks;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Coding on 8/23/17.
 */
public class RankConfig{

    public static YamlConfiguration config;
    private static File file;
    static Ranks plugin;

    public RankConfig(Ranks ranks){
        plugin = ranks;
        file = new File(plugin.getDataFolder(), "Ranks.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void saveConfig(){
        try{
            config.save(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
