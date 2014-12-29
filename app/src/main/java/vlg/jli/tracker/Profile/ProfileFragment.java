package vlg.jli.tracker.Profile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import vlg.jli.tracker.R;

public class ProfileFragment extends Fragment {
    TabHost tabHost;

    void ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile, container, false);

        tabHost = (TabHost)rootView.findViewById(R.id.tabHost_profile);
        tabHost.setup();
        TabHost.TabSpec ts = tabHost.newTabSpec("ActivityTab");
        ts.setContent(R.id.ActivityTab);
        ts.setIndicator("Activity");
        tabHost.addTab(ts);

        ts = tabHost.newTabSpec("StatsTab");
        ts.setContent(R.id.StatsTab);
        ts.setIndicator("Stats");
        tabHost.addTab(ts);
        ts= tabHost.newTabSpec("AwardsTab");
        ts.setContent(R.id.AwardsTab);
        ts.setIndicator("Awards");
        tabHost.addTab(ts);

        tabHost.setOnTabChangedListener(onTabChangeListener);
        switchToActivity();
        return rootView;
    }

    TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String s) {
            if(s == "ActivityTab") {
                switchToActivity();
            }else if(s == "StatsTab") {
                switchToStats();
            }else if(s == "AwardsTab") {
                switchToAwards();
            }
        }
    };

    void switchToActivity()
    {
        ActivityTabFragment fragment = new ActivityTabFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.tab_content, fragment)
                .commit();
    }
    void switchToStats()
    {
        UserViewFragment fragment = new UserViewFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.tab_content, fragment)
                .commit();
    }

    void switchToAwards()
    {
        AwardsTabFragment fragment = new AwardsTabFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.tab_content, fragment)
                .commit();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));*/
    }
}
