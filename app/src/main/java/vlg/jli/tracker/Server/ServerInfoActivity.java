package vlg.jli.tracker.Server;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerPlayer;
import vlg.jli.tracker.R;
import vlg.jli.tracker.View.ServerInfoRowView;

public class ServerInfoActivity extends Activity implements Observer {

    View rootView;
    LinearLayout contentLayout;
    ListView listViewPlayers;
    PlayerListAdapter playerListAdapter;
    Server currentServer;

    public ServerInfoActivity()
    {
        super();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_server_info);
        rootView = findViewById(R.id.activity_server_info_root);
        contentLayout = (LinearLayout)rootView.findViewById(R.id.server_info_list);
        listViewPlayers = (ListView)rootView.findViewById(R.id.list_view_player_list);
        playerListAdapter = new PlayerListAdapter(this, new ArrayList<ServerPlayer>());
        listViewPlayers.setAdapter(playerListAdapter);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.watch_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
            case R.id.action_watch:
                Log.d("GMTracker", "WATCH!");
                GameMECache.getInstance(this).addWatchedServer(currentServer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed(){
        // do something here and don't write super.onBackPressed()
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void handleIntent(Intent intent) {
        if(intent == null)
            return;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Gson gson = new Gson();
            String serializedServer = getIntent().getStringExtra("server");
            Server server =  gson.fromJson(serializedServer, Server.class);
            setServer(server);
            Log.d("GMTracker", serializedServer);
            return;
        }
    }

    public void setServer(Server server)
    {
        if(server == null)
            return;

        if(currentServer != null)
            currentServer.deleteObserver(this);


        Log.d("GMTracker", "Set Server: " + server.name);
        currentServer = server;
        currentServer.addObserver(this);
        updateViewWithServer(currentServer);

        setTitle(server.name);

        GameMEAPI api = new GameMEAPI(this);
        api.getServerPlayerList(currentServer.getFullAddress(), new AsyncListener() {
            @Override
            public void onResult(Object response, boolean isSuccess) {
                List<ServerPlayer> players = (List<ServerPlayer>)response;
                currentServer.playerList = players;
                updateViewWithServer(currentServer);
            }
        });
    }

    void updateViewWithServer(Server server)
    {
        if(this == null)   //if context is not initialized, errors will occur
            return;

        contentLayout.removeAllViewsInLayout();
        ServerInfoRowView serverNameRow = new ServerInfoRowView(this);
        serverNameRow.tvProperty.setText("Server Name: ");
        serverNameRow.tvValue.setText(server.name);

        ServerInfoRowView serverAddressRow = new ServerInfoRowView(this);
        serverAddressRow.tvProperty.setText("Address: ");
        serverAddressRow.tvValue.setText(server.addr + ":" + server.port);

        ServerInfoRowView serverPlayerCount = new ServerInfoRowView(this);
        serverPlayerCount.tvProperty.setText("Players: ");
        serverPlayerCount.tvValue.setText(server.playerCount + "/" + server.maxPlayers);

        ServerInfoRowView serverMapRow = new ServerInfoRowView(this);
        serverMapRow.tvProperty.setText("Map: ");
        serverMapRow.tvValue.setText(server.map);

        ServerInfoRowView playerInfoRow = new ServerInfoRowView(this);
        serverMapRow.tvProperty.setText("Player Names: ");
        serverMapRow.tvValue.setText("Kill/Death");

        contentLayout.addView(serverNameRow);
        contentLayout.addView(serverAddressRow);
        contentLayout.addView(serverPlayerCount);
        contentLayout.addView(serverMapRow);

        if(server.playerList != null && server.playerList.size() > 0)
            updatePlayerList();
        else
            playerListAdapter.clear();
    }

    @Override
    public void update(Observable observable, Object o) {
        updatePlayerList();
    }

    public void updatePlayerList()
    {
        playerListAdapter.clear();
        playerListAdapter.addAll(currentServer.playerList);
        playerListAdapter.notifyDataSetChanged();
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
            if(currentServer.playerList == null || currentServer.playerList.size() == 0)
                return convertView;

            ServerPlayer player = currentServer.playerList.get(position);
            TextView property = (TextView)convertView.findViewById(R.id.server_info_row_property);
            property.setText(player.name);
            TextView val = (TextView)convertView.findViewById(R.id.server_info_row_value);
            val.setText(player.kills + "/" + player.deaths);
            return convertView;
        }
    }
}
