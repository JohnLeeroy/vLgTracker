package vlg.jli.tracker.User;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 3/27/15.
 */
public class UserListActivity  extends FragmentActivity{

    private interface ISearcher extends AsyncListener
    {
        public void search(String query);
    }

    private UserListFragment userListFragment;
    private ISearcher userSearchDelegate;
    private ISearcher searchDelegate;

    GameMEAPI api;

    MenuItem searchItem;

    String query;
    CountDownTimer searchCooldown;
    boolean isSearching = false;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        View contentView = (View) findViewById(R.id.user_list_content);

        userListFragment = new UserListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.user_list_content, userListFragment)
                .commit();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.global, menu);

        searchItem = menu.findItem(R.id.search);

        SearchManager searchManager =
                (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

        initSearchDelegates();
        initSearchBar();

        api = new GameMEAPI(this);
        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        handleIntent(getIntent());

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
        searchDelegate = userSearchDelegate;
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

    void startSearch(String query)
    {
        if(query.length() == 0)
            return;

        Log.d("gmtracker", "Searching for ... " + query);
        isSearching = true;
        searchDelegate.search(query);
    }

    private void handleIntent(Intent intent) {
        if(intent == null)
            return;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Gson gson = new Gson();
            String serializedResponse = getIntent().getStringExtra("searchResult");
            List<User> searchResults =  gson.fromJson(serializedResponse, new TypeToken<List<User>>() {}.getType());
            userListFragment.updateData(searchResults);
            return;
        }
    }


}
