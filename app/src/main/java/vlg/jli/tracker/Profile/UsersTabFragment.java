package vlg.jli.tracker.Profile;

import android.content.Context;
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

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.FeedItem;
import vlg.jli.tracker.Model.FeedList;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;

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
            User user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_activity, parent, false);
            }

            ImageView userIcon = (ImageView) convertView.findViewById(R.id.watch_user_icon);
            Ion.with(getActivity()).load(user.avatar).withBitmap().intoImageView(userIcon);

            // Lookup view for data population
            TextView header = (TextView) convertView.findViewById(R.id.watched_user_name);
            TextView body = (TextView) convertView.findViewById(R.id.watch_user_body);

            header.setText(user.name);
            //body.setText(user.rank);

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
