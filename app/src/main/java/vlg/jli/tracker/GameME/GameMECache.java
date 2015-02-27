package vlg.jli.tracker.GameME;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import vlg.jli.tracker.Model.Server;
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

    ArrayList<Server> watchedServers;

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
        cache.put("watchedServers", gson.toJson(watchedServers));
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

        Type serverType = new TypeToken<List<Server>>(){}.getType();
        watchedServers = gson.fromJson(cache.get("watchedServers"), serverType);
        if(watchedServers == null)
            watchedServers = new ArrayList<Server>();

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
        //TODO make sure there are no duplicates
        watchedUsers.add(user);
        saveCache();
    }

    public void addWatchedServer(Server server)
    {
        //TODO make sure there are no duplicates
        watchedServers.add(new Server(server));
        saveCache();
    }

    public ArrayList<Server> getWatchedServers()    { return watchedServers; }
    public ArrayList<User> getWatchedUsers()        { return watchedUsers; }
}
