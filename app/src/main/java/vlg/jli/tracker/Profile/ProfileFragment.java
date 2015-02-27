package vlg.jli.tracker.Profile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

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
        setTabColor(tabHost);

        tabHost.getTabWidget().setLeftStripDrawable(R.drawable.theme_green);
        tabHost.getTabWidget().setDividerDrawable(R.drawable.theme_green);

        updateTabSelector(tabHost.getTabWidget());

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

            setTabColor(tabHost);
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
        ServersTabFragment fragment = new ServersTabFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.tab_content, fragment)
                .commit();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private void setTabColor(TabHost tabHost) {
        try {
            for (int i=0; i < tabHost.getTabWidget().getChildCount();i++) {
                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                if (tv != null) {
                    tv.setTextColor(getResources().getColor(R.color.theme));
                    tv.setTextSize(12);
                }
                TextView tv2 = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); // Selected Tab
                if (tv2 != null) {
                    tv2.setTextColor(getResources().getColor(R.color.theme));
                    tv2.setTextSize(16);
                }
            }
        } catch (ClassCastException e) {
            // A precaution, in case Google changes from a TextView on the tabs.
        }
    }

    private void updateTabSelector(TabWidget tabWidget) {
        // Change background
        for(int i=0; i < tabWidget.getChildCount(); i++)
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_indicator_ab_app);
    }


}
