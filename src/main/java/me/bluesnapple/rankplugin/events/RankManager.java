package me.bluesnapple.rankplugin.events;

import me.bluesnapple.rankplugin.Ranks;
import me.bluesnapple.rankplugin.config.RankConfig;
import me.bluesnapple.rankplugin.hypixellib.GetGuild;
import me.bluesnapple.rankplugin.hypixellib.GetPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.UUID;

/**
 * Created by Coding on 8/23/17.
 */
public class RankManager implements Listener{

    private Ranks plugin;
    public RankManager(Ranks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        FileConfiguration config = RankConfig.config;
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String yellow = ChatColor.YELLOW.toString();
        String aqua = ChatColor.AQUA.toString();
        String red = ChatColor.RED.toString();

        GetPlayer.main(player);
        GetGuild.main(player);

        event.setJoinMessage("");

        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p: Bukkit.getOnlinePlayers()){
                    p.sendMessage(player.getDisplayName() + yellow + " joined the game.");
                }

                if (config.get(uuid.toString()) != null){
                    if (config.getBoolean(uuid + ".isInGuild")){
                        if (config.getString(uuid + ".guildRank").equalsIgnoreCase("GUILDMASTER")){
                            player.sendMessage(aqua + "Welcome, Guild Master! Your people love you <3");
                        }else if (config.getString(uuid + ".guildRank").equalsIgnoreCase("OFFICER")){
                            player.sendMessage(aqua + "Welcome, fellow Officer! Thanks for being a role model to our members :)");
                        }else if (config.getString(uuid + ".guildRank").equalsIgnoreCase("MEMBER")){
                            player.sendMessage(aqua + "Welcome, Member of the CopyKatz guild! Thanks for being a loyal member and following all the rules. :D");
                        }
                    }else{
                        player.sendMessage(red + "You are not in our guild??? What are you doing here?" + "\n" + aqua + "Just kidding.. Welcome to our guild server ;)");
                    }
                }
            }
        }, 52L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String yellow = ChatColor.YELLOW.toString();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        if (board.getTeam(player.getName()) != null){
            board.getTeam(player.getName()).unregister();
        }

        event.setQuitMessage(player.getDisplayName() + yellow + " left the game.");
    }
}
