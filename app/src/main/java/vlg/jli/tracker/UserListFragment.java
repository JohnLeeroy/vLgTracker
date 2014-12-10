package vlg.jli.tracker;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.Model.UserList;

/**
 * Created by johnli on 12/1/14.
 */
public class UserListFragment extends Fragment {

    Menu mainMenu;
    MenuItem searchItem;

    SearchView searchView;

    ArrayList searchResults;

    boolean isSearching = false;
    String query;

    CountDownTimer searchCooldown;

    ListView serverListView;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_servers, container, false);
        serverListView = (ListView)rootView.findViewById(R.id.server_list);

        initTwo();
        return rootView;
    }

    void initTwo()
    {
        UserList userList = new UserList();
        UserAdapter adapter = new UserAdapter(getActivity(), userList.users);
        serverListView.setAdapter(adapter);
    }

    public class UserAdapter extends ArrayAdapter<User> {
        public UserAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);
        }

        public UserAdapter(Context context, List<User> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            User user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_user, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.user_title);
            TextView rank = (TextView) convertView.findViewById(R.id.rank_stat);
            TextView kill = (TextView) convertView.findViewById(R.id.kill_stat);
            TextView headshot = (TextView) convertView.findViewById(R.id.headshots_stat);
            rank.setText("Rank: " + user.rank);
            kill.setText("Kills: " + user.kills);
            headshot.setText("HS: " + user.headshots);

            // Populate the data into the template view using the data object
            tvName.setText(user.name);

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

    public void init(){
        Menu menu = mainMenu;
        searchItem = menu.findItem(R.id.search);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                //switchToSearch();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.d("tag", "Collapsing");
                /*
                getActionBar().setDisplayHomeAsUpEnabled(true);
                onFeedUpdated();
                feedListView.setAdapter(feedListAdapter);
                feedListView.setOnItemClickListener(onFeedRowClick);
                */
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
        isSearching = true;
        /*AsyncListener searchHandler = new AsyncListener() {
            @Override
            public void onResult(String response, boolean isSuccess) {
                if(isSuccess) {
                    feedManager.storeSearchResults(response);
                    onSearchResultsUpdated(feedManager.getSearchResults());
                }else {
                    Log.d(tag, "Error Searching Feeds: " + response);
                }
            }
        };

        api.searchForNewFeeds(query, searchHandler);
        */
    }
}
