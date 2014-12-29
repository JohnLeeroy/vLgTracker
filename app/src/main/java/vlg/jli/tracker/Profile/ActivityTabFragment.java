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

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.Model.VLGActivity;
import vlg.jli.tracker.Model.VLGActivityList;
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
        VLGActivityList VLGActivityList = new VLGActivityList();
        ActivityListAdapter adapter = new ActivityListAdapter(getActivity(), VLGActivityList.data);
        activityListView.setAdapter(adapter);
    }

    public class ActivityListAdapter extends ArrayAdapter<VLGActivity> {
        public ActivityListAdapter(Context context, ArrayList<VLGActivity> activities) {
            super(context, 0, activities);
        }

        public ActivityListAdapter(Context context, List<VLGActivity> activities) {
            super(context, 0, activities);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            VLGActivity VLGActivity = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_activity, parent, false);
            }
            // Lookup view for data population
            TextView header = (TextView) convertView.findViewById(R.id.activity_title);
            TextView body = (TextView) convertView.findViewById(R.id.activity_body);

            header.setText(VLGActivity.header);
            body.setText(VLGActivity.body);

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
