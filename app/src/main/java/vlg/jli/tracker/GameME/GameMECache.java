package vlg.jli.tracker.GameME;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerPlayer;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.Service.SettingsProvider;

/**
 * Created by johnli on 12/25/14.
 */
public class GameMECache {
    private static GameMECache instance;
    String userCacheFileName = "main_user";
    Gson gson = new Gson();
    Context ctx;
    SettingsProvider settings;

    public List<Server> servers;
    public User mainUser;

    ArrayList<User> watchedUsers;

    Dictionary<String, String> cache;

    public static GameMECache getInstance(Context context)
    {
        if(instance == null) {
            instance = new GameMECache();
            instance.settings = new SettingsProvider(context);
            instance.loadCache();
        }
        instance.ctx = context;

        return instance;
    }

    public void saveCache()
    {
        Gson gson = new Gson();
        cache.put("user", gson.toJson(mainUser));
        cache.put("watchedUsers", gson.toJson(watchedUsers));
        settings.setPref(gson.toJson(cache));
    }

    public void loadCache()
    {
        Gson gson = new Gson();
        cache = gson.fromJson(settings.getPref(), Hashtable.class);
        if(cache == null)
            cache = new Hashtable<String, String>();

        Type type = new TypeToken<List<User>>(){}.getType();
        watchedUsers = gson.fromJson(cache.get("watchedUsers"), type);
        if(watchedUsers == null)
            watchedUsers = new ArrayList<User>();

        User serializedUser = gson.fromJson(cache.get("user"), User.class);
        mainUser = serializedUser;

    }

    public void setMainUser(User user)
    {
        mainUser = user;
        saveCache();
    }

    public void addWatchedUser(User user)
    {
        if(!watchedUsers.contains(user)) {
            watchedUsers.add(user);
            saveCache();
        }
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> type) {
        T[] arr = new Gson().fromJson(s, type);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

    public ArrayList<User> getWatchedUsers() { return watchedUsers; }
}
