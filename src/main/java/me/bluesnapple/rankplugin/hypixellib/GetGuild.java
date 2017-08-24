package me.bluesnapple.rankplugin.hypixellib;

import me.bluesnapple.rankplugin.config.RankConfig;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reply.GuildReply;
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
public class GetGuild{

    public static void main(Player player) {
        UUID uuid = player.getUniqueId();
        HypixelAPI.getInstance().setApiKey(Util.getApiKey());

        Request request = RequestBuilder.newBuilder(RequestType.GUILD)
                .addParam(RequestParam.GUILD_BY_ID, "57231d830cf295fe64d090f3")
                .createRequest();
        HypixelAPI.getInstance().getAsync(request, (Callback<GuildReply>) (failCause, result) -> {
            if (failCause != null) {
                failCause.printStackTrace();
            } else {
                FileConfiguration config = RankConfig.config;

                Boolean wasFound = false;

                for (GuildReply.Guild.Member m: result.getGuild().getMembers()){
                    if (m.getUuid().equals(uuid)){

                        config.set(uuid + ".isInGuild", true);
                        config.set(uuid + ".guildRank", m.getRank().toString());
                        RankConfig.saveConfig();

                        wasFound = true;
                    }
                }

                if (!wasFound){
                    config.set(uuid + ".isInGuild", false);
                    RankConfig.saveConfig();
                }

            }
            HypixelAPI.getInstance().finish();
        });
    }

}
