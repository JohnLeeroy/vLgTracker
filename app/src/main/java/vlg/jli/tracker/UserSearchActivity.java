package vlg.jli.tracker;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.Model.User;

/**
 * Created by johnli on 12/1/14.
 */
public class UserSearchActivity extends FragmentActivity {

    Menu mainMenu;
    MenuItem searchItem;

    SearchView searchView;

    ArrayList searchResults;

    boolean isSearching = false;
    String query;

    CountDownTimer searchCooldown;

    UserListFragment userListFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

       View contentView = (View)findViewById(R.id.user_list_content);

        userListFrag = new UserListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.user_list_content, userListFrag)
                .commit();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global, menu);

        searchItem = menu.findItem(R.id.search);
        init(menu);

        //init();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    AdapterView.OnItemClickListener onUserRowClickedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), UserCardActivity.class);
            User user = (User)adapterView.getItemAtPosition(i);
            intent.putExtra("user",  user);
            startActivity(intent);

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    };

    public void init(Menu menu){

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

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
                searchForNewFeeds(query);
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
                searchForNewFeeds(query);
            }
        };
    }

    /**
     * get list of search results
     */
    void searchForNewFeeds(String query)
    {
        if(query.length() == 0)
            return;

        isSearching = true;
        AsyncListener searchHandler = new AsyncListener() {
            @Override
            public void onResult(Object response, boolean isSuccess) {
                if(isSuccess) {
                    List<User> searchResult = (List<User>)response;
                    userListFrag.updateData(searchResult);
                    userListFrag.userListView.setOnItemClickListener(onUserRowClickedListener);
                }else {
                    Log.d("VLG", "Error Searching Users: " + response);
                }
            }
        };
        GameMEAPI api = new GameMEAPI(this);
        api.getUserSearch(query, searchHandler);
    }
}
