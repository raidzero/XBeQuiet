package com.raidzero.xposed.bfquiet;

import android.content.Context;
import android.content.SharedPreferences;
import de.robv.android.xposed.XSharedPreferences;

/**
 * Created by raidzero on 4/27/14 3:22 PM
 * This provides a bridge from shared preferences to xsharedpreferences
 */
public class SettingsHelper {
    private XSharedPreferences xSharedPreferences = null;
    private SharedPreferences sharedPreferences = null;
    private Context context = null;

    public SettingsHelper() {
        xSharedPreferences = new XSharedPreferences("com.raidzero.xposed.bfquiet");
        xSharedPreferences.reload();
        xSharedPreferences.makeWorldReadable();
    }

    public void reload() {
        xSharedPreferences.reload();
    }

    public boolean isEnabled() {
        boolean enabled = xSharedPreferences.getBoolean("disableSound", true);

        return enabled;
    }

}
