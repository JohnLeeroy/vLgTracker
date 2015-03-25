package vlg.jli.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import java.util.List;

import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.Profile.ProfileFragment;
import vlg.jli.tracker.Server.ServerListFragment;
import vlg.jli.tracker.User.UserListFragment;


public class MainActivity extends NavDrawerActivity {

    private interface ISearcher extends AsyncListener
    {
        public void search(String query);
    }

    private ISearcher userSearchDelegate;
    private ISearcher serverSearchDelegate;
    private ISearcher searchDelegate;

    String query;
    CountDownTimer searchCooldown;
    boolean isSearching = false;

    GameMEAPI api;

    ProfileFragment profileFragment;
    ServerListFragment serverListFragment;
    UserListFragment userListFragment;
    AboutFragment aboutFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        initSearchDelegates();
        initSearchBar();

        api = new GameMEAPI(this);
        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }

    void initSearchDelegates()
    {
        userSearchDelegate = new ISearcher() {
            @Override
            public void search(String query) {
                api.getUserSearch(query, this);
            }

            @Override
            public void onResult(Object response, boolean isSuccess) {
                List<User> searchResults = (List<User>) response;
                userListFragment.updateData(searchResults);
            }
        };

        serverSearchDelegate = new ISearcher() {
            @Override
            public void search(String query) {
                api.getServerSearch(query, this);
            }

            @Override
            public void onResult(Object response, boolean isSuccess) {
                List<Server> searchResults = (List<Server>) response;
                Log.d("gmtracker", "Count: " + searchResults.size());
                serverListFragment.updateData(searchResults);
            }
        };
    }

    public void initSearchBar(){
        // Associate searchable configuration with the SearchView
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                //switchToSearch();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.d("tag", "Collapsing");
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = s;
                startSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                query = s;
                searchCooldown.cancel();
                searchCooldown.start();
                return false;
            }
        });

        searchCooldown = new CountDownTimer(800, 100) {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                startSearch(query);
            }
        };
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        super.onNavigationDrawerItemSelected(position);
        if(position == 0)
        {
            profileFragment = new ProfileFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, profileFragment)
                    .commit();

            getActionBar().setTitle("Profile");
        }
        else if(position == 1)
        {
            serverListFragment = new ServerListFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, serverListFragment)
                    .commit();
            getActionBar().setTitle("Search Server");

            searchDelegate = serverSearchDelegate;
        }
        else if(position == 2) {
            userListFragment = new UserListFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, userListFragment)
                    .commit();
            searchDelegate = userSearchDelegate;

            getActionBar().setTitle("Search User");
        }
        else if(position == 3)
        {
            getActionBar().setTitle("About");
            aboutFragment = new AboutFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, aboutFragment)
                    .commit();
        }
    }

    /**
     * get list of search results
     */
    void startSearch(String query)
    {
        if(query.length() == 0)
            return;

        Log.d("gmtracker", "Searching for ... " + query);
        isSearching = true;
        searchDelegate.search(query);
    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        if(searchItem != null)
            searchItem.collapseActionView();
    }

    public void onBackPressed(){
        // do something here and don't write super.onBackPressed()
        if(mNavigationDrawerFragment.getSelectedPosition() == 0)
            finish();
        else
            mNavigationDrawerFragment.selectItem(0);
    }

}
