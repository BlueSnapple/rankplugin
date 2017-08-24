package me.bluesnapple.rankplugin.events;

import me.bluesnapple.rankplugin.config.RankConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

/**
 * Created by Coding on 8/23/17.
 */
public class ChatManager implements Listener{

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event){
        String gray = ChatColor.GRAY.toString();
        String white = ChatColor.WHITE.toString();
        FileConfiguration config = RankConfig.config;
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (config.get(uuid) != null) {
            String rank = config.getString(uuid + ".Rank");
            String message = event.getMessage().replaceAll("%", "%%");

            if (!rank.equalsIgnoreCase("none")) {
                event.setFormat(player.getDisplayName() + white + ": " + message);
            } else {
                event.setFormat(player.getDisplayName() + gray + ": " + message);
            }
        }
    }

    public static void setDisplayName(Player player){
        String green = ChatColor.GREEN.toString();
        String aqua = ChatColor.AQUA.toString();
        String red = ChatColor.RED.toString();
        String blue = ChatColor.BLUE.toString();
        String darkGreen = ChatColor.DARK_GREEN.toString();
        String gray = ChatColor.GRAY.toString();
        String gold = ChatColor.GOLD.toString();
        FileConfiguration config = RankConfig.config;
        String uuid = player.getUniqueId().toString();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        if (board.getTeam(player.getName()) == null){
            Team team = board.registerNewTeam(player.getName());
            team.addEntry(player.getName());
        }else{
            board.getTeam(player.getName()).unregister();
            Team team = board.registerNewTeam(player.getName());
            team.addEntry(player.getName());
        }

        if (config.get(uuid) != null){
            String rank = config.getString(uuid + ".Rank");

            if (rank.equalsIgnoreCase("admin")){
                String prefix = red + "[ADMIN] " + player.getName();
                String onlyPrefix = red + "[ADMIN] ";
                player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                player.setDisplayName(prefix);
            }else if (rank.equalsIgnoreCase("moderator")){
                String prefix = darkGreen + "[MOD] " + player.getName();
                String onlyPrefix = darkGreen + "[MOD] ";
                player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                player.setDisplayName(prefix);
            }else if (rank.equalsIgnoreCase("helper")){
                String prefix = blue + "[HELPER] " + player.getName();
                String onlyPrefix = blue + "[HELPER] ";
                player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                player.setDisplayName(prefix);
            }else if (rank.equalsIgnoreCase("youtuber")){
                String prefix = gold + "[YT] " + player.getName();
                String onlyPrefix = gold + "[YT] ";
                player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                player.setDisplayName(prefix);
            }else if (rank.equalsIgnoreCase("mvp_plus")){
                if (config.get(uuid + ".rankPlusColor") != null){
                    String rankPlusColor = convertColor(config.getString(uuid + ".rankPlusColor")).toString();
                    String prefix = aqua + "[MVP" + rankPlusColor + "+" + aqua + "] " + player.getName();
                    String onlyPrefix = aqua + "[MVP" + rankPlusColor + "+" + aqua + "] ";
                    player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                    player.setDisplayName(prefix);
                }else{
                    String prefix = aqua + "[MVP" + red + "+" + aqua + "] " + player.getName();
                    String onlyPrefix = aqua + "[MVP" + red + "+" + aqua + "] ";
                    player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                    player.setDisplayName(prefix);
                }
            }else if (rank.equalsIgnoreCase("mvp")){
                String prefix = aqua + "[MVP] " + player.getName();
                String onlyPrefix = aqua + "[MVP] ";
                player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                player.setDisplayName(prefix);
            }else if (rank.equalsIgnoreCase("vip_plus")){
                String prefix = green + "[VIP" + gold + "+" + green + "] " + player.getName();
                String onlyPrefix = green + "[VIP" + gold + "+" + green + "] ";
                player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                player.setDisplayName(prefix);
            }else if (rank.equalsIgnoreCase("vip")){
                String prefix = green + "[VIP] " + player.getName();
                String onlyPrefix = green + "[VIP] ";
                player.getScoreboard().getTeam(player.getName()).setPrefix(onlyPrefix);
                player.setDisplayName(prefix);
            }else if (rank.equalsIgnoreCase("none")){
                String prefix = gray + player.getName();
                player.getScoreboard().getTeam(player.getName()).setPrefix(gray);
                player.setDisplayName(prefix);
            }
        }
        player.getScoreboard().getTeam(player.getName()).setSuffix(gray + getPlayerSuffix(player));
    }

    public static String getPlayerSuffix(Player player){
        UUID uuid = player.getUniqueId();
        FileConfiguration config = RankConfig.config;

        if (config.getBoolean(uuid + ".isInGuild")){
            if (config.getString(uuid + ".guildRank").equalsIgnoreCase("GUILDMASTER")){
                return " [Master]";
            }else if (config.getString(uuid + ".guildRank").equalsIgnoreCase("OFFICER")){
                return " [Officer]";
            }else if (config.getString(uuid + ".guildRank").equalsIgnoreCase("MEMBER")){
                return " [Member]";
            }
        }
        return "";
    }

    public static ChatColor convertColor(String color){
        ChatColor rankPlusColor;

        if (color.equalsIgnoreCase("aqua")){
            rankPlusColor = ChatColor.AQUA;
        }else if (color.equalsIgnoreCase("black")){
            rankPlusColor = ChatColor.BLACK;
        }else if (color.equalsIgnoreCase("blue")){
            rankPlusColor = ChatColor.BLUE;
        }else if (color.equalsIgnoreCase("dark_aqua")){
            rankPlusColor = ChatColor.DARK_AQUA;
        }else if (color.equalsIgnoreCase("dark_blue")){
            rankPlusColor = ChatColor.DARK_BLUE;
        }else if (color.equalsIgnoreCase("dark_gray")){
            rankPlusColor = ChatColor.DARK_GRAY;
        }else if (color.equalsIgnoreCase("dark_green")){
            rankPlusColor = ChatColor.DARK_GREEN;
        }else if (color.equalsIgnoreCase("dark_purple")){
            rankPlusColor = ChatColor.DARK_PURPLE;
        }else if (color.equalsIgnoreCase("dark_red")){
            rankPlusColor = ChatColor.DARK_RED;
        }else if (color.equalsIgnoreCase("gold")){
            rankPlusColor = ChatColor.GOLD;
        }else if (color.equalsIgnoreCase("gray")){
            rankPlusColor = ChatColor.GRAY;
        }else if (color.equalsIgnoreCase("green")){
            rankPlusColor = ChatColor.GREEN;
        }else if (color.equalsIgnoreCase("light_purple")){
            rankPlusColor = ChatColor.LIGHT_PURPLE;
        }else if (color.equalsIgnoreCase("red")){
            rankPlusColor = ChatColor.RED;
        }else if (color.equalsIgnoreCase("white")){
            rankPlusColor = ChatColor.WHITE;
        }else if (color.equalsIgnoreCase("yellow")){
            rankPlusColor = ChatColor.YELLOW;
        }else{
            rankPlusColor = ChatColor.MAGIC;
        }

        return rankPlusColor;
    }

}
