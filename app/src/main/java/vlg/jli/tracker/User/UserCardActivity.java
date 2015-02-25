package vlg.jli.tracker.User;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;

/**
 * Created by johnli on 2/21/15.
 */
public class UserCardActivity extends FragmentActivity{

    UserCardFragment userCardFrag;
    User currentUser;
    GameMECache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        View contentView = (View) findViewById(R.id.user_list_content);

        userCardFrag = new UserCardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.user_list_content, userCardFrag)
                .commit();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());

        cache = GameMECache.getInstance(this);
        if(cache.mainUser == null)
        {
            userCardFrag.setMainUserListener = new Button.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(currentUser != null)
                        cache.setMainUser(currentUser);
                }
            };
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.watch_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    return true;
                case R.id.action_watch:
                    Log.d("GMTracker", "WATCH!");
                    cache.addWatchedUser(currentUser);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
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
                return;
            }
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
                            userCardFrag.setUser(user);
                            api.getUser(currentUser.steamId, new AsyncListener() {
                                @Override
                                public void onResult(Object response, boolean isSuccess) {
                                    User user = (User)response;
                                    userCardFrag.setUser(user);
                                }
                            });
                        }
                    }, 100);
                }
            });
        }


}
