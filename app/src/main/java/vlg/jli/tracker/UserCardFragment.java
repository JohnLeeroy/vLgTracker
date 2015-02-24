package vlg.jli.tracker;

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

import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.View.UserCardStatBarView;

/**
 * Created by johnli on 2/21/15.
 */
public class UserCardFragment extends Fragment {

    ImageView userProfilePicture;
    TextView username;

    UserCardStatBarView statBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_user_card, container, false);
        userProfilePicture = (ImageView) rootView.findViewById(R.id.card_user_picture);
        username = (TextView) rootView.findViewById(R.id.card_user_name);
        statBar = (UserCardStatBarView) rootView.findViewById(R.id.user_card_stat_bar);

        /*
        setMainUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSetAsMainUser(view);
            }
        });
        */
        init();
        return  rootView;
    }

    void init()
    {
        User me = GameMECache.getInstance(getActivity()).mainUser;
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

    public void onClickSetAsMainUser(View v)
    {
        try {
           // GameMECache.getInstance(getActivity()).saveUserPrefs(currentUser);
        }
        catch (Exception e)
        {

        }
    }

    public void setUser(User user)
    {
        if(user == null) {
            Log.w("Tracker", "UserViewFragment: Setting user to null");
            return;
        }

        Ion.with(getActivity()).load(user.avatar).withBitmap().intoImageView(userProfilePicture);
        username.setText(user.name);
        statBar.tvKills.setText(String.valueOf(user.kills));
        statBar.tvRank.setText(String.valueOf(user.rank));
        statBar.tvSkill.setText(String.valueOf(user.skill));
    }
}
