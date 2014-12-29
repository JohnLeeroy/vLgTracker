package vlg.jli.tracker.GameME;

import android.content.Context;
import android.preference.PreferenceManager;


/**
 * SettingsProvider
 *  Responsible for saving & loading the feed & feed item info
 */

public class SettingsProvider {
    Context c;

    public SettingsProvider(Context c) {
        this.c = c;
    }

    public void setUserPref(String jsonData) {
        PreferenceManager.getDefaultSharedPreferences(c).edit()
                .putString("user_pref", jsonData).commit();
    }

    public String getUserPref() {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(
                "user_pref", "");
    }

    public void resetUserPrefs()
    {
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString("feed_pref", "").commit();
    }
}