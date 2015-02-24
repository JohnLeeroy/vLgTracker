package vlg.jli.tracker.Server;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerList;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;
import vlg.jli.tracker.UserCardActivity;

/**
 * Created by johnli on 12/20/14.
 */

public class ServerListFragment extends Fragment
{
    View rootView;
    ListView serverListView;
    List<Server> servers;
    ServerAdapter adapter;

    public ServerRowSelectedListener serverRowSelectedListener;
    public ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_serverlist, container, false);
        serverListView = (ListView)rootView.findViewById(R.id.server_list);

        init();
        return rootView;
    }

    void init()
    {
        ServerList serverList = new ServerList();
        adapter = new ServerAdapter(getActivity(), serverList.servers);
        serverListView.setAdapter(adapter);
        List<Server> cachedServers = GameMECache.getInstance(getActivity()).servers;
        if(cachedServers != null) {
            adapter.clear();
            adapter.addAll(cachedServers);
            adapter.notifyDataSetChanged();
        }

        String blah;
        try {
            getServers();
        }catch (Exception e){
            e.getMessage();
        }

    }

    void getServers()
    {
        //REFACTOR
        GameMEAPI api = new GameMEAPI(getActivity());
        api.getGlobalServerList(getServerListener);
    }

    AsyncListener getServerListener = new AsyncListener(){
        @Override
        public void onResult(Object response, boolean isSuccess){
            servers = (List<Server>)response;
            List<Server> toRemove = new ArrayList<Server>();

            Server currentServer;
            for(int i = 0; i < servers.size(); i++)
            {
                currentServer = servers.get(i);
                if(!currentServer.game.equalsIgnoreCase("csgo"))
                {
                    toRemove.add(currentServer);
                }
            }
            servers.removeAll(toRemove);

            adapter.clear();
            adapter.addAll(servers);
            adapter.notifyDataSetChanged();
        }
    };



    private class ServerAdapter extends ArrayAdapter<Server> {
        public ServerAdapter(Context context, ArrayList<Server> servers) {
            super(context, 0, servers);
        }

        public ServerAdapter(Context context, List<Server> servers) {
            super(context, 0, servers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Server server = getItem(position);
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
                    Log.d("VLG", "?" + server.toString());
                   // serverRowSelectedListener.onServerChanged(server);
                    Intent intent = new Intent(getActivity().getApplicationContext(), ServerInfoActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("server",  gson.toJson(server));
                    startActivity(intent);

                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void updateData(List<Server> data)
    {
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
