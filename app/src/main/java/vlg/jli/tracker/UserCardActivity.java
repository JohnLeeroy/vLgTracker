package vlg.jli.tracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;

import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.Profile.UserViewFragment;

/**
 * Created by johnli on 2/21/15.
 */
public class UserCardActivity extends FragmentActivity{

        UserCardFragment userCardFrag;
        User currentUser;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_userlist);

            View contentView = (View)findViewById(R.id.user_list_content);

            userCardFrag = new UserCardFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.user_list_content, userCardFrag)
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
                User user = extras.getParcelable("user");
                updateUser(user);
                return;
            }
        }

        void updateUser(final User user)
        {
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
