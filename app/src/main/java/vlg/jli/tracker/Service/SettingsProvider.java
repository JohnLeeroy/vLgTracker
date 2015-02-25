package vlg.jli.tracker.Service;

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

    public void setPref(String jsonData) {
        PreferenceManager.getDefaultSharedPreferences(c).edit()
                .putString("saved_pref", jsonData).commit();
    }

    public String getPref() {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(
                "saved_pref", "");
    }

    public void resetUserPrefs()
    {
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString("saved_pref", "").commit();
    }
}