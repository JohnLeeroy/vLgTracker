package vlg.jli.tracker.GameME;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerPlayer;
import vlg.jli.tracker.Model.User;

/**
 * Created by johnli on 12/25/14.
 */
public class GameMECache {
    private static GameMECache instance;
    String userCacheFileName = "main_user";
    Gson gson = new Gson();
    Context ctx;
    SettingsProvider settings;

    public static GameMECache getInstance(Context context)
    {
        if(instance == null) {
            instance = new GameMECache();
            instance.settings = new SettingsProvider(context);
            instance.loadUserPrefs();
        }
        instance.ctx = context;

        return instance;
    }

    public List<Server> servers;
    public User mainUser;

    public void savePlayerList(String address, List<ServerPlayer> playerList) {
        if (playerList == null || servers == null || playerList.size() == 0)
            return;

        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            if (address.equals(server.getFullAddress())) {
                server.playerList = playerList;
                Log.d("VLG", "Player list saved for " + address);
                return;
            }
        }
    }

    public void loadUserPrefs()
    {
        String json = settings.getUserPref();
        Log.d("vLg", "Loading user prefs: " + json);    //display stored json
        if(json == null || json.length() == 0)
            return;

        mainUser = gson.fromJson(json, User.class);
    }

    /**
     * Cache feed data to disk
     */
    public void saveUserPrefs(User user) throws JSONException {
        Log.d("vLg", "Saving user prefs");
        JSONObject json = new JSONObject(gson.toJson(user));
        settings.setUserPref(json.toString());

        loadUserPrefs();
    }
}
