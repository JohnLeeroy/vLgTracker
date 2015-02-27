package vlg.jli.tracker.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.R;
import vlg.jli.tracker.Server.ServerInfoActivity;

/**
 * Created by johnli on 12/1/14.
 */
public class ServersTabFragment extends Fragment {

    ListView serverListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_serverlist, container, false);
        serverListView = (ListView) rootView.findViewById(R.id.server_list);
        init();
        return  rootView;
    }


    void init()
    {
        List<Server> servers = GameMECache.getInstance(getActivity()).getWatchedServers();
        ServerAdapter adapter = new ServerAdapter(getActivity(), servers);
        serverListView.setAdapter(adapter);
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
}
