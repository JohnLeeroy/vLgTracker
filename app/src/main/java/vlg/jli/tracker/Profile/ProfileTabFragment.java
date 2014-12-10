package vlg.jli.tracker.Profile;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 12/1/14.
 */
public class ProfileTabFragment extends Fragment{
    ListView activityListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile_user, container, false);
        activityListView = (ListView)rootView.findViewById(R.id.user_profile_list_view);
        init();
        return  rootView;
    }

    void init()
    {
        User me = User.getRed();
        List<String[]> data = me.convertToList();

        UserProfileAdapter adapter = new UserProfileAdapter(getActivity(), data);
        activityListView.setAdapter(adapter);
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
}
