package vlg.jli.tracker;

import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.List;

import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.Model.Server;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.Profile.ProfileFragment;
import vlg.jli.tracker.Server.ServerPagerFragment;


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
    ServerPagerFragment serverFragment;
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
    public void onNavigationDrawerItemSelected(int position) {
        super.onNavigationDrawerItemSelected(position);
        if(position == 0)
        {
            profileFragment = new ProfileFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, profileFragment)
                    .commit();
        }
        else if(position == 1)
        {
            serverFragment = new ServerPagerFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, serverFragment)
                    .commit();

            searchDelegate = serverSearchDelegate;
        }
        else if(position == 2) {
            userListFragment = new UserListFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, userListFragment)
                    .commit();
            searchDelegate = userSearchDelegate;
            //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
        else if(position == 3)
        {
            aboutFragment = new AboutFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, aboutFragment)
                    .commit();
        }
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
                serverFragment.updateData(searchResults);
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

        searchCooldown = new CountDownTimer(400, 100) {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                startSearch(query);
            }
        };
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
}
