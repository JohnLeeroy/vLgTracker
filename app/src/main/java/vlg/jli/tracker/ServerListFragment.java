package vlg.jli.tracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerList;

/**
 * Created by johnli on 11/28/14.
 */
public class ServerListFragment extends Fragment {
    ListView serverListView;
    View rootView;
    List<Server> servers;
    ServerAdapter adapter;


    ViewPager pager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_servers, container, false);
        serverListView = (ListView)rootView.findViewById(R.id.server_list);

        init();
        return rootView;
    }

    void init()
    {
        ServerList serverList = new ServerList();
        adapter = new ServerAdapter(getActivity(), serverList.servers);
        serverListView.setAdapter(adapter);

        String blah;
        try {
            getServers();
        }catch (Exception e){
            e.getMessage();
        }
    }



    void getServers()
    {
        new DownloadXmlTask().execute(getServerListener);
    }

    AsyncListener getServerListener = new AsyncListener(){
        @Override
        public void onResult(Object response, boolean isSuccess){
            servers = (List<Server>)response;
            adapter.clear();
            adapter.addAll(servers);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));*/
    }

    public class ServerAdapter extends ArrayAdapter<Server> {
        public ServerAdapter(Context context, ArrayList<Server> servers) {
            super(context, 0, servers);
        }

        public ServerAdapter(Context context, List<Server> servers) {
            super(context, 0, servers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Server server = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_server, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.server_title);
            TextView map = (TextView) convertView.findViewById(R.id.map);
            map.setText(server.map);

            // Populate the data into the template view using the data object
            tvName.setText(server.name);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("VLG", "?");

                }
            });
            // Return the completed view to render on screen
            return convertView;
        }
    }

    // Uploads XML from stackoverflow.com, parses it, and combines it with
    // HTML markup. Returns HTML string.
    private List<Server> loadGameMEXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
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

    // Implementation of AsyncTask used to download XML feed from stackoverflow.com.
    private class DownloadXmlTask extends AsyncTask<AsyncListener, Void, List<Server>> {
        AsyncListener onFinish;
        @Override
        protected List<Server> doInBackground(AsyncListener ... listener) {
            onFinish = listener[0];
            try {
                return loadGameMEXmlFromNetwork("http://vlgsite.gameme.com/api/serverlist");
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
        }catch ( Exception e)
        {
            e.printStackTrace();
            int x = 5;
        }
        return conn.getInputStream();
    }
}



