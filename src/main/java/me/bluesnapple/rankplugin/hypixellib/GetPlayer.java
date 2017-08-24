package me.bluesnapple.rankplugin.hypixellib;

import me.bluesnapple.rankplugin.config.RankConfig;
import me.bluesnapple.rankplugin.events.ChatManager;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.PlayerReply;
import net.hypixel.api.request.Request;
import net.hypixel.api.request.RequestBuilder;
import net.hypixel.api.request.RequestParam;
import net.hypixel.api.request.RequestType;
import net.hypixel.api.util.Callback;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Coding on 8/23/17.
 */
public class GetPlayer {

    public static void main(Player player){
        UUID uuid = player.getUniqueId();
        HypixelAPI.getInstance().setApiKey(uuid);

        Request request = RequestBuilder.newBuilder(RequestType.PLAYER)
                .addParam(RequestParam.PLAYER_BY_UUID, uuid)
                .createRequest();
        HypixelAPI.getInstance().getAsync(request, (Callback<PlayerReply>) (failCause, result) -> {
            if (failCause != null){
                failCause.printStackTrace();
            }else{
                FileConfiguration config = RankConfig.config;

                if (result.getPlayer().get("rank") != null &&
                        !result.getPlayer().get("rank").getAsString().equalsIgnoreCase("normal")){
                    String rank = result.getPlayer().get("rank").getAsString();
                    String displayName = result.getPlayer().get("displayname").getAsString();
                    config.set(uuid + ".Name", displayName);
                    config.set(uuid + ".isStaff", true);
                    config.set(uuid + ".Rank", rank);
                    RankConfig.saveConfig();
                }else if (result.getPlayer().get("newPackageRank") != null){
                    String rank = result.getPlayer().get("newPackageRank").getAsString();
                    String displayName = result.getPlayer().get("displayname").getAsString();
                    config.set(uuid + ".Name", displayName);
                    config.set(uuid + ".isStaff", false);
                    config.set(uuid + ".Rank", rank);
                    RankConfig.saveConfig();
                }else if (result.getPlayer().get("packageRank") != null){
                String rank = result.getPlayer().get("packageRank").getAsString();
                String displayName = result.getPlayer().get("displayname").getAsString();
                config.set(uuid + ".Name", displayName);
                config.set(uuid + ".isStaff", false);
                config.set(uuid + ".Rank", rank);
                RankConfig.saveConfig();
                }else{
                    String rank = "NONE";
                    String displayName = result.getPlayer().get("displayname").getAsString();
                    config.set(uuid + ".Name", displayName);
                    config.set(uuid + ".isStaff", false);
                    config.set(uuid + ".Rank", rank);
                    RankConfig.saveConfig();
                }

                if(result.getPlayer().get("rankPlusColor") != null){
                    String rankPlusColor = result.getPlayer().get("rankPlusColor").getAsString();
                    config.set(uuid + ".rankPlusColor", rankPlusColor);
                    RankConfig.saveConfig();
                }

                ChatManager.setDisplayName(player);

            }

            HypixelAPI.getInstance().finish();

        });
    }

}
