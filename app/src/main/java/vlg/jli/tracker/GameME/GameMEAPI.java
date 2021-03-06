package vlg.jli.tracker.GameME;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerPlayer;
import vlg.jli.tracker.Model.User;

/**
 * Created by johnli on 12/25/14.
 * Interface for getting data from the GameME API
 */
public class GameMEAPI {

    Context ctx;
    public GameMEAPI(Context context)
    {
        ctx = context;
    }
    public void getDefaultSearch(AsyncListener getUserSearchResultListener)
    {
        new DownloadUserSearchXmlTask("vlg").execute(getUserSearchResultListener);
    }

    public void getUser(String steamId, AsyncListener getUserListener)
    {
        new DownloadUserXmlTask(steamId).execute(getUserListener);
    }

//Search
    public void getUserSearch(String query, AsyncListener getUserSearchResultListener)
    {
        new DownloadUserSearchXmlTask(query).execute(getUserSearchResultListener);
    }

    public void getServerSearch(String query, AsyncListener getServerSearchResultListener)
    {
        Log.d("gmtracker", "Search Server Task");
        new DownloadServerSearchXmlTask(query).execute(getServerSearchResultListener);
    }

//Server
    public void getServers(AsyncListener getServerListener)
    {
        new DownloadServerXmlTask().execute(getServerListener);
    }

    public void getServerPlayerList(String addr, AsyncListener getServerInfoListener)
    {
        new DownloadServerInfoXmlTask(addr).execute(getServerInfoListener);
    }

    public  void getGlobalServerList(AsyncListener getServerListListener)
    {
        new DownloadGlobalServerXmlTask().execute(getServerListListener);
    }

    public class DownloadUserXmlTask extends AsyncTask<AsyncListener, Void, User> {
        AsyncListener onFinish;
        String steamId;

        public DownloadUserXmlTask(String SteamId)
        {
            steamId = SteamId;
        }
        @Override
        protected User doInBackground(AsyncListener ... listener) {

            Log.d("gmtracker", "Start downloadUserXML");
            onFinish = listener[0];
            User user;
            try {
                InputStream stream = null;
                GameMEXmlParser api = new GameMEXmlParser();
                try {
                    stream = downloadUrl("http://vlgsite.gameme.com/api/playerinfo/csgo2/" + steamId + "/weapons");
                    //stream = downloadUrl("http://api.gameme.com/api/playerinfo/csgo/" + steamId);
                    user = api.parseForUser(stream);

                    Log.d("gmtracker", "End downloadUserXML");
                } finally {
                    if (stream != null) {
                        stream.close();
                    }
                }
            } catch (IOException e) {
                return null;//"Connection Error";
            } catch (XmlPullParserException e) {
                return null;//"XML Error";
            }
            return user;
        }

        @Override
        protected void onPostExecute(User result) {
            onFinish.onResult(result, true);
        }
    }

    //http://vlgsite.gameme.com/api/playerinfo/csgo2/STEAM_0:0:13338494/weapons
    public class DownloadUserWeaponStatsXMLTask extends AsyncTask<AsyncListener, Void, List<User>> {
        AsyncListener onFinish;
        String query;

        public DownloadUserWeaponStatsXMLTask(String sQuery)
        {
            query = sQuery;
        }
        @Override
        protected List<User> doInBackground(AsyncListener ... listener) {
            onFinish = listener[0];
            try {
                return loadUserList("http://vlgsite.gameme.com/api/playerlist/csgo2/name/" + query);
            } catch (IOException e) {
                return null;//"Connection Error";
            } catch (XmlPullParserException e) {
                return null;//"XML Error";
            }
        }

        @Override
        protected void onPostExecute(List<User> result) {
            onFinish.onResult(result, true);
        }
    }

    public class DownloadUserSearchXmlTask extends AsyncTask<AsyncListener, Void, List<User>> {
        AsyncListener onFinish;
        String query;

        public DownloadUserSearchXmlTask(String sQuery)
        {
            query = sQuery;
        }
        @Override
        protected List<User> doInBackground(AsyncListener ... listener) {
            onFinish = listener[0];
            try {
                //http://api.gameme.net/playerlist/csgo/name/vlg
                //return loadUserList("http://api.gameme.net/playerlist/csgo/name/" + query);

                return loadUserList("http://vlgsite.gameme.com/api/playerlist/csgo2/name/" + query);
            } catch (IOException e) {
                return null;//"Connection Error";
            } catch (XmlPullParserException e) {
                return null;//"XML Error";
            }
        }

        @Override
        protected void onPostExecute(List<User> result) {
            onFinish.onResult(result, true);
        }
    }

    public class DownloadServerSearchXmlTask extends AsyncTask<AsyncListener, Void, List<Server>> {
        AsyncListener onFinish;
        String query;

        public DownloadServerSearchXmlTask(String sQuery)
        {
            query = sQuery;
        }
        @Override
        protected List<Server> doInBackground(AsyncListener ... listener) {
            onFinish = listener[0];
            Log.d("gmtracker", "doInBackground");
            try {
                //http://api.gameme.net/playerlist/csgo/name/vlg
                //return loadUserList("http://api.gameme.net/playerlist/csgo/name/" + query);

                return loadGameMEServerXmlFromNetwork("http://api.gameme.net/serverlist/name/" + query);
            } catch (IOException e) {
                return null;//"Connection Error";
            } catch (XmlPullParserException e) {
                return null;//"XML Error";
            }
        }

        @Override
        protected void onPostExecute(List<Server> result) {
            onFinish.onResult(result, true);
        }
    }

    public class DownloadGlobalServerXmlTask extends AsyncTask<AsyncListener, Void, List<Server>> {
        AsyncListener onFinish;
        @Override
        protected List<Server> doInBackground(AsyncListener ... listener) {
            onFinish = listener[0];
            try {
                Log.d("gmtracker", "Start GetGlobalServerList");
                List<Server> servers = loadGameMEServerXmlFromNetwork("http://api.gameme.net/serverlist/game/csgo");
                Log.d("gmtracker", "End GetGlobalServerList");

                return servers;
            } catch (IOException e) {
                return null;//"Connection Error";
            } catch (XmlPullParserException e) {
                return null;//"XML Error";
            }
        }

        @Override
        protected void onPostExecute(List<Server> result) {
            GameMECache.getInstance(ctx).servers = result;

            onFinish.onResult(result, true);
        }
    }
    public class DownloadServerXmlTask extends AsyncTask<AsyncListener, Void, List<Server>> {
        AsyncListener onFinish;
        @Override
        protected List<Server> doInBackground(AsyncListener ... listener) {
            onFinish = listener[0];
            try {
                return loadGameMEServerXmlFromNetwork("http://vlgsite.gameme.com/api/serverlist");
            } catch (IOException e) {
                return null;//"Connection Error";
            } catch (XmlPullParserException e) {
                return null;//"XML Error";
            }
        }

        @Override
        protected void onPostExecute(List<Server> result) {
            GameMECache.getInstance(ctx).servers = result;
            for(int i = 0; i < result.size(); i++) {
                getServerPlayerList(result.get(i).getFullAddress(), new AsyncListener() {
                    @Override
                    public void onResult(Object response, boolean isSuccess) {

                    }
                });
            }

            onFinish.onResult(result, true);
        }
    }

    public class DownloadServerInfoXmlTask extends AsyncTask<AsyncListener, Void, List<ServerPlayer>> {
        String address;
        public  DownloadServerInfoXmlTask(String addr)
        {
            address = addr;
        }

        AsyncListener onFinish;
        @Override
        protected List<ServerPlayer> doInBackground(AsyncListener ... listener) {
            onFinish = listener[0];
            try {
                //http://api.gameme.net/serverinfo/74.91.125.229:27015/players

                Log.d("gmtracker", "Start GetServerPlayerList");
                List<ServerPlayer> playerList = loadServerPlayerList("http://api.gameme.net/serverinfo/" + address + "/players");
                Log.d("gmtracker", "End GetServerPlayerList");
                return playerList;
            } catch (IOException e) {
                return null;//"Connection Error";
            } catch (XmlPullParserException e) {
                return null;//"XML Error";
            }
        }

        @Override
        protected void onPostExecute(List<ServerPlayer> result) {
            //GameMECache.getInstance(ctx).savePlayerList(address, result);
            onFinish.onResult(result, true);
        }
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query

        try {
            conn.connect();
        } catch (Exception e) {
            e.printStackTrace();
            int x = 5;
        }
        return conn.getInputStream();
    }

    // Uploads XML from stackoverflow.com, parses it, and combines it with
    // HTML markup. Returns HTML string.
    private List<Server> loadGameMEServerXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        GameMEXmlParser api = new GameMEXmlParser();
        List<Server> servers;
        try {
            Log.d("gmtracker", "Start");
            stream = downloadUrl(urlString);

            Log.d("gmtracker", "End");
            servers = api.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return servers;
    }

    private List<ServerPlayer> loadServerPlayerList(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        GameMEXmlParser api = new GameMEXmlParser();
        List<ServerPlayer> users;
        try {
            stream = downloadUrl(urlString);
            users = api.parseForPlayerList(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return users;
    }

    private List<User> loadUserList(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        GameMEXmlParser api = new GameMEXmlParser();
        List<User> users;
        try {
            stream = downloadUrl(urlString);
            users = api.parseForUserList(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return users;
    }

}
