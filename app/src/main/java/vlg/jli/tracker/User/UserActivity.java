package vlg.jli.tracker.User;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.Profile.StatsTabFragment;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 12/27/14.
 */
public class UserActivity extends FragmentActivity {

    StatsTabFragment userFrag;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        View contentView = (View)findViewById(R.id.user_list_content);

        userFrag = new StatsTabFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.user_list_content, userFrag)
                .commit();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if(intent == null)
            return;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Gson gson = new Gson();
            String serializedUser = getIntent().getStringExtra("watch_bar");
            User user =  gson.fromJson(serializedUser, User.class);
            updateUser(user);
            Log.d("GMTracker", serializedUser);
            return;
        }
    }

    public void onBackPressed(){
        // do something here and don't write super.onBackPressed()
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    void updateUser(final User user)
    {
        setTitle(user.name);

        final GameMEAPI api = new GameMEAPI(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentUser = user;
                        userFrag.setUser(user);
                        api.getUser(currentUser.steamId, new AsyncListener() {
                            @Override
                            public void onResult(Object response, boolean isSuccess) {
                                User user = (User)response;
                                userFrag.setUser(user);
                            }
                        });
                    }
                }, 100);
            }
        });
    }

}
