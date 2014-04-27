package com.raidzero.xposed.bfquiet;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by raidzero on 4/13/14 10:16 AM
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.preferences);
    }
}
