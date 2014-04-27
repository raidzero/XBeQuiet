package com.raidzero.xposed.bfquiet;

import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by raidzero on 4/10/14 8:47 PM
 */
public class XBfQuiet implements IXposedHookLoadPackage {

    private final static SettingsHelper settingsHelper = new SettingsHelper();
    private String TAG = "XBFQuiet";

    private void XLog(String msg) {
        XposedBridge.log(TAG + ": " + msg);
    }

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        // only run on brave frontier
        if (lpparam.packageName.equals("sg.gumi.bravefrontier")) {

            settingsHelper.reload();

            // only run if it's enabled
            if (settingsHelper.isEnabled()) {
                // this one is for background music
                try {
                    XLog("Attempt hooking background music");

                    // hook method: void playBackgroundMusic(String s, boolean b)
                    findAndHookMethod("org.cocos2dx.lib.Cocos2dxMusic", lpparam.classLoader, "playBackgroundMusic", String.class, boolean.class,
                            XC_MethodReplacement.DO_NOTHING);
                } catch (Throwable e) {
                    XposedBridge.log(e);
                }

                // this is for individual sound playback
                try {
                    XLog("Attempt hooking sound effect");

                    // hook method: int doPlayEffect(String s, int i, boolean b)
                    findAndHookMethod("org.cocos2dx.lib.Cocos2dxSound", lpparam.classLoader, "doPlayEffect", String.class, int.class, boolean.class,
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
            }
        }
    }
}
