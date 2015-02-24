package vlg.jli.tracker.Server;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;


/**
 * Created by johnli on 11/28/14.
 */

public class ServerPagerFragment extends Fragment {
    View rootView;

    ViewPager pager;
    FragmentStatePagerAdapter pageAdapter;
    ServerRowSelectedListener serverRowSelectedListener;

    Server selectedServer;

    ServerInfoFragment serverInfoFragment;
    ServerListFragment serverListFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_servers, container, false);
        pager = (ViewPager)rootView.findViewById(R.id.pager);
        pageAdapter = new ServerPagerAdapter(getFragmentManager());
        pager.setAdapter(pageAdapter);

        init();
        return rootView;
    }

    void init()
    {
        serverRowSelectedListener = new ServerRowSelectedListener() {
            @Override
            public void onServerChanged(Server server) {
                selectedServer = server;
                serverInfoFragment.setServer(selectedServer);
                pager.setCurrentItem(1, true);
            }
        };
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    class ServerPagerAdapter extends FragmentStatePagerAdapter {

        public ServerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int i) {
            if(i == 0) {
                serverListFragment = new ServerListFragment();
                serverListFragment.serverRowSelectedListener = serverRowSelectedListener;
                return serverListFragment;
            }
            else if(i == 1)
            {
                serverInfoFragment = new ServerInfoFragment();
                serverInfoFragment.setServer(selectedServer);
                return serverInfoFragment;
            }
            return  null;
        }
    }

    public void updateData(List<Server> data)
    {
        serverListFragment.updateData(data);
    }
}





