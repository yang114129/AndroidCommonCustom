package com.yang.custom;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.yang.push.XDFPushManager;

/**
 * Created by yang on 2017/7/31.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XDFPushManager.getInstance(getApplicationContext()).initPush();

        int xiaomi_id;
        try {
            ApplicationInfo appInfo = getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);

            xiaomi_id = appInfo.metaData.getInt("XIAOMI_ID");

            Log.e("XMpush_appkey=", xiaomi_id+"--");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
    }
}
