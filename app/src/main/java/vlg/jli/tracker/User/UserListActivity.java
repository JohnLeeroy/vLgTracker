package vlg.jli.tracker.User;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 3/27/15.
 */
public class UserListActivity  extends FragmentActivity{

    UserListFragment userListFragment;
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
    protected void onStart()
    {
        super.onStart();
        handleIntent(getIntent());

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
