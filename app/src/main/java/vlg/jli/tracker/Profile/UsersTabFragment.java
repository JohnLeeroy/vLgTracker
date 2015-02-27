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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.FeedItem;
import vlg.jli.tracker.Model.FeedList;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;
import vlg.jli.tracker.User.UserCardActivity;

/**
 * Created by johnli on 12/1/14.
 */
public class UsersTabFragment extends Fragment {
    ListView activityListView;
    GameMECache cache;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile_users, container, false);
        activityListView = (ListView)rootView.findViewById(R.id.profile_activity_list_view);
        init();
        return  rootView;
    }

    void init()
    {
        cache = GameMECache.getInstance(getActivity());
        ArrayList<User> watchedUsers = cache.getWatchedUsers();

        User mainUser = cache.mainUser;
        if( watchedUsers.size() == 0)
            return;

        UsersListAdapter adapter = new UsersListAdapter(getActivity(), watchedUsers);
        activityListView.setAdapter(adapter);
    }

    public class UsersListAdapter extends ArrayAdapter<User> {
        public UsersListAdapter(Context context, ArrayList<User> activities) {
            super(context, 0, activities);
        }

        public UsersListAdapter(Context context, List<User> activities) {
            super(context, 0, activities);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final User user = getItem(position);
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
            tvName.setText(user.name);
            rank.setText("Rank: " + user.rank);
            kill.setText("Kills: " + user.kills);
            headshot.setText("HS: " + user.headshots);

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
}
