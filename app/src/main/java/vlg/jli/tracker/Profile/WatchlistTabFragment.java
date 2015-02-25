package vlg.jli.tracker.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerList;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 12/1/14.
 */
public class WatchlistTabFragment extends Fragment {
    ListView activityListView;
//profile_activity_list_view


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile_watchlist, container, false);
        return  rootView;
    }


    void init()
    {
        ServerList serverList = new ServerList();
        ServerAdapter adapter = new ServerAdapter(getActivity(), serverList.servers);
        activityListView.setAdapter(adapter);
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
}
