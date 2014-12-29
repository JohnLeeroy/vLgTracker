package vlg.jli.tracker.GameME;

import android.content.Context;
import android.os.AsyncTask;

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

    public void getUserSearch(String query, AsyncListener getUserSearchResultListener)
    {
        new DownloadUserSearchXmlTask(query).execute(getUserSearchResultListener);
    }

    public void getServers(AsyncListener getServerListener)
    {
        new DownloadServerXmlTask().execute(getServerListener);
    }

    public void getServerPlayerList(String addr, AsyncListener getServerInfoListener)
    {
        new DownloadServerInfoXmlTask(addr).execute(getServerInfoListener);
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
                return loadServerPlayerList("http://api.gameme.net/serverinfo/" + address + "/players");
            } catch (IOException e) {
                return null;//"Connection Error";
            } catch (XmlPullParserException e) {
                return null;//"XML Error";
            }
        }

        @Override
        protected void onPostExecute(List<ServerPlayer> result) {
            GameMECache.getInstance(ctx).savePlayerList(address, result);
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
            stream = downloadUrl(urlString);
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
