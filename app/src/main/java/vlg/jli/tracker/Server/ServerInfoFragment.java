package vlg.jli.tracker.Server;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerPlayer;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;
import vlg.jli.tracker.View.ServerInfoRowView;

/**
 * Created by johnli on 12/20/14.
 */
public class ServerInfoFragment extends Fragment implements Observer {
    View rootView;
    LinearLayout contentLayout;
    ListView listViewPlayers;
    PlayerListAdapter playerListAdapter;
    Server currentServer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_server_info, container, false);
        contentLayout = (LinearLayout)rootView.findViewById(R.id.server_info_list);
        listViewPlayers = (ListView)rootView.findViewById(R.id.list_view_player_list);
        playerListAdapter = new PlayerListAdapter(getActivity(), new ArrayList<ServerPlayer>());
        listViewPlayers.setAdapter(playerListAdapter);
        return rootView;
    }

    public void setServer(Server server)
    {
        if(server == null)
            return;

        if(currentServer != null)
            currentServer.deleteObserver(this);

        currentServer = server;
        currentServer.addObserver(this);
        updateViewWithServer(currentServer);
    }

    void updateViewWithServer(Server server)
    {
        if(getActivity() == null)   //if context is not initialized, errors will occur
            return;

        ServerInfoRowView serverNameRow = new ServerInfoRowView(getActivity());
        serverNameRow.tvProperty.setText("Server Name: ");
        serverNameRow.tvValue.setText(server.name);

        ServerInfoRowView serverAddressRow = new ServerInfoRowView(getActivity());
        serverAddressRow.tvProperty.setText("Address: ");
        serverAddressRow.tvValue.setText(server.addr + ":" + server.port);

        ServerInfoRowView serverPlayerCount = new ServerInfoRowView(getActivity());
        serverPlayerCount.tvProperty.setText("Players: ");
        serverPlayerCount.tvValue.setText(server.playerCount + "/" + server.maxPlayers);

        ServerInfoRowView serverMapRow = new ServerInfoRowView(getActivity());
        serverMapRow.tvProperty.setText("Map: ");
        serverMapRow.tvValue.setText(server.map);

        ServerInfoRowView playerInfoRow = new ServerInfoRowView(getActivity());
        serverMapRow.tvProperty.setText("Player Names: ");
        serverMapRow.tvValue.setText("Kill/Death");

        contentLayout.addView(serverNameRow);
        contentLayout.addView(serverAddressRow);
        contentLayout.addView(serverPlayerCount);
        contentLayout.addView(serverMapRow);

        if(server.playerList != null && server.playerList.size() > 0)
           updatePlayerList();
    }

    @Override
    public void update(Observable observable, Object o) {
        updatePlayerList();
    }

    private class PlayerListAdapter extends ArrayAdapter<ServerPlayer> {
        public PlayerListAdapter(Context context, ArrayList<ServerPlayer> players) {
            super(context, 0, players);
        }

        public PlayerListAdapter(Context context, List<ServerPlayer> players) {
            super(context, 0, players);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_server_info, parent, false);

            ServerPlayer player = currentServer.playerList.get(position);
            TextView property = (TextView)convertView.findViewById(R.id.server_info_row_property);
            property.setText(player.name);
            TextView val = (TextView)convertView.findViewById(R.id.server_info_row_value);
            val.setText(player.kills + "/" + player.deaths);
            return convertView;
        }
    }

    public void updatePlayerList()
    {
        playerListAdapter.clear();
        playerListAdapter.addAll(currentServer.playerList);
        playerListAdapter.notifyDataSetChanged();
    }

    protected void finalize()
    {
        currentServer.deleteObserver(this);
    }
}
