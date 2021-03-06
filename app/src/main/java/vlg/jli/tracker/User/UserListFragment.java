package vlg.jli.tracker.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 12/27/14.
 */
public class UserListFragment extends Fragment
{
    public ListView userListView;

    List<User> userList;

    public UserListAdapter adapter;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_users, container, false);
        userListView = (ListView)rootView.findViewById(R.id.users_list);

        adapter = new UserListAdapter(getActivity(), new ArrayList<User>());
        userListView.setAdapter(adapter);

        /*GameMEAPI api = new GameMEAPI();
        api.getDefaultSearch(new AsyncListener() {
            @Override
            public void onResult(Object response, boolean isSuccess) {
                adapter.clear();
                adapter.addAll((List<User>)response);
                adapter.notifyDataSetChanged();
            }
        });
        */

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return rootView;
    }

    public class UserListAdapter extends ArrayAdapter<User> {
        public UserListAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);
        }

        public UserListAdapter(Context context, List<User> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final User user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_user, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.user_title);
            TextView rank = (TextView) convertView.findViewById(R.id.rank_stat);
            TextView kill = (TextView) convertView.findViewById(R.id.kill_stat);
            TextView headshot = (TextView) convertView.findViewById(R.id.headshots_stat);

            ImageView profilePic = (ImageView) convertView.findViewById(R.id.user_icon);
            Ion.with(getActivity()).load(user.avatar).intoImageView(profilePic);
            rank.setText("Rank: " + user.rank);
            kill.setText("Kills: " + user.kills);
            headshot.setText("HS: " + user.headshots);

            // Populate the data into the template view using the data object
            tvName.setText(user.name);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), UserCardActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("watch_bar",  gson.toJson(user));
                    startActivity(intent);

                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });

            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void updateData(List<User> data)
    {
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

}
