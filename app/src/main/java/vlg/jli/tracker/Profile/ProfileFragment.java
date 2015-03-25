package vlg.jli.tracker.Profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
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

    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);

        viewPager.setAdapter( new ProfilePagerAdapter(getFragmentManager(), getActivity()) );


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
                viewPager.setCurrentItem(1);
            }else if(s == "StatsTab") {
                viewPager.setCurrentItem(0);

            }else if(s == "WatchlistTab") {
                viewPager.setCurrentItem(2);

            }

            setTabColor(tabHost);
        }
    };

    @Override
    public void onViewStateRestored (Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);

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

    class ProfilePagerAdapter extends FragmentStatePagerAdapter {

        Context mContext;

        public ProfilePagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            if(position == 0)
            {
                fragment = new UserCardFragment();
            }
            else if(position == 1)
            {
                fragment = new UsersTabFragment();

            }
            else if(position == 2)
            {
                fragment = new ServersTabFragment();
            }

            Bundle args = new Bundle();
            args.putInt("page_position", position + 1);

            // Set the arguments on the fragment
            // that will be fetched in the
            // DemoFragment@onCreateView
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + (position + 1);
        }
    }
}
