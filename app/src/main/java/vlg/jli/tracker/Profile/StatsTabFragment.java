package vlg.jli.tracker.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 12/1/14.
 */
public class StatsTabFragment extends Fragment {
    ListView activityListView;
    UserProfileAdapter adapter;
    User currentUser;
    ImageView profilePicture;

    Button setMainUserButton;

    GameMECache cache;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile_user, container, false);
        activityListView = (ListView)rootView.findViewById(R.id.user_profile_list_view);
        setMainUserButton = (Button)rootView.findViewById(R.id.user_set_main_user_button);
        setMainUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  onClickSetAsMainUser(view);
            }
        });

        profilePicture = (ImageView) rootView.findViewById(R.id.user_picture);

        adapter = new UserProfileAdapter(getActivity(), new ArrayList<String[]>());
        activityListView.setAdapter(adapter);

        cache = GameMECache.getInstance(getActivity());
        init();
        return  rootView;
    }

    void init()
    {
        User me = cache.mainUser;
        if(me == null) {
            me = User.getRed();
        }
        setUser(me);
    }

    public class UserProfileAdapter extends ArrayAdapter<String[]> {
        public UserProfileAdapter(Context context, ArrayList<String[]> data) {
            super(context, 0, data);
        }

        public UserProfileAdapter(Context context, List<String[]> data) {
            super(context, 0, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            String[] data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_user_data, parent, false);
            }
            // Lookup view for data population
            TextView fieldTextView = (TextView) convertView.findViewById(R.id.user_data_field);
            TextView dataTextView = (TextView) convertView.findViewById(R.id.user_data_info);
            fieldTextView.setText(data[0]);
            dataTextView.setText(data[1]);

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

    public void setUser(User user)
    {
        if(user == null)
            return;

        currentUser = user;
        List<String[]> data = user.convertToList();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();

        Ion.with(getActivity()).load(currentUser.avatar).withBitmap().intoImageView(profilePicture);
        //setMainUserButton.setVisibility(View.VISIBLE);
    }
}
