package com.raidzero.xposed.bequiet;

import java.util.ArrayList;

import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by raidzero on 4/10/14 8:47 PM
 */
public class XBeQuiet implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    private XSharedPreferences prefs;

    private String TAG = "XBeQuiet";

    private void XLog(String msg) {
        XposedBridge.log(TAG + ": " + msg);
    }

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        loadPrefs();
    }

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        String pkgName = lpparam.packageName;

        // only run on packages selected
        if (isEnabled(pkgName)) {
            try {
                XLog("Attempt hooking SoundPool.play() in " + pkgName);

                // hook method: int play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
                findAndHookMethod("android.media.SoundPool", lpparam.classLoader, "play", int.class, float.class, float.class, int.class, int.class, float.class,
                        new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                // SoundPool.play() returns nonzero if successful, 0 if fail, so just appease it
                                return 1;
                            }
                        }
                );
            } catch (Throwable e) {
            XposedBridge.log(e);
        }

            try {
                XLog("Attempt hooking MediaPlayer.start() in " + pkgName);

                // hook method: int play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
                findAndHookMethod("android.media.MediaPlayer", lpparam.classLoader, "start",
                        XC_MethodReplacement.DO_NOTHING);
            } catch (Throwable e) {
                XposedBridge.log(e);
            }

            try {
                XLog("Attempt hooking AudioTrack.play() in " + pkgName);

                // hook method: int play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
                findAndHookMethod("android.media.AudioTrack", lpparam.classLoader, "play",
                        XC_MethodReplacement.DO_NOTHING);
            } catch (Throwable e) {
                XposedBridge.log(e);
            }
        }
    }

    private boolean isEnabled(String pkgName) {
        prefs.reload();

        return prefs.getBoolean(pkgName, false);
    }

    private void loadPrefs() {
        prefs = new XSharedPreferences("com.raidzero.xposed.bequiet");
        prefs.makeWorldReadable();
        XLog("prefs loaded.");
    }
}
