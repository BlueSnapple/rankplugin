package me.bluesnapple.rankplugin.hypixellib;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Coding on 8/23/17.
 */
public class Util{

    private static Integer integer = 0;

    public static final UUID getApiKey(){
        ArrayList<UUID> apiKey = new ArrayList<>();

        if (integer > 4){
            integer++;
        }else{
            integer = 0;
        }

        //Add your own api key from doing /api new on the hypixel server
        apiKey.add(UUID.fromString(""));
        apiKey.add(UUID.fromString(""));
        apiKey.add(UUID.fromString(""));
        apiKey.add(UUID.fromString(""));

        return apiKey.get(integer);

    }

}
