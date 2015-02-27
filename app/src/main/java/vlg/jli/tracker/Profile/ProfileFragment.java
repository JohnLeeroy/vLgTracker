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
import vlg.jli.tracker.User.UserCardFragment;

public class ProfileFragment extends Fragment {
    TabHost tabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile, container, false);

        tabHost = (TabHost)rootView.findViewById(R.id.tabHost_profile);
        tabHost.setup();
        TabHost.TabSpec ts = tabHost.newTabSpec("StatsTab");
        ts.setContent(R.id.StatsTab);
        ts.setIndicator("Stats");
        tabHost.addTab(ts);

        ts = tabHost.newTabSpec("ActivityTab");
        ts.setContent(R.id.ActivityTab);
        ts.setIndicator("Users");
        tabHost.addTab(ts);

        ts= tabHost.newTabSpec("WatchlistTab");
        ts.setContent(R.id.WatchlistTab);
        ts.setIndicator("Servers");
        tabHost.addTab(ts);

        tabHost.setOnTabChangedListener(onTabChangeListener);
        switchToStats();
        return rootView;
    }

    TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String s) {
            if(s == "ActivityTab") {
                switchToActivity();
            }else if(s == "StatsTab") {
                switchToStats();
            }else if(s == "WatchlistTab") {
                switchToWatchlist();
            }
        }
    };

    void switchToActivity()
    {
        UsersTabFragment fragment = new UsersTabFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.tab_content, fragment)
                .commit();
    }
    void switchToStats()
    {
        UserCardFragment fragment = new UserCardFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.tab_content, fragment)
                .commit();

    }

    void switchToWatchlist()
    {
        WatchlistTabFragment fragment = new WatchlistTabFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.tab_content, fragment)
                .commit();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
