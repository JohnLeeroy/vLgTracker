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

import vlg.jli.tracker.Model.Activity;
import vlg.jli.tracker.Model.ActivityList;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.ServerList;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 12/1/14.
 */
public class ActivityTabFragment extends Fragment {
    ListView activityListView;
//profile_activity_list_view


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile_activity, container, false);
        activityListView = (ListView)rootView.findViewById(R.id.profile_activity_list_view);
        init();
        return  rootView;
    }

    void init()
    {
        ActivityList activityList = new ActivityList();
        ActivityListAdapter adapter = new ActivityListAdapter(getActivity(), activityList.data);
        activityListView.setAdapter(adapter);
    }

    public class ActivityListAdapter extends ArrayAdapter<Activity> {
        public ActivityListAdapter(Context context, ArrayList<Activity> activities) {
            super(context, 0, activities);
        }

        public ActivityListAdapter(Context context, List<Activity> activities) {
            super(context, 0, activities);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Activity activity = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_activity, parent, false);
            }
            // Lookup view for data population
            TextView header = (TextView) convertView.findViewById(R.id.activity_title);
            TextView body = (TextView) convertView.findViewById(R.id.activity_body);

            header.setText(activity.header);
            body.setText(activity.body);

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
