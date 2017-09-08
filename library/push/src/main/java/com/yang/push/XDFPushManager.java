package com.yang.push;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.TokenResult;
import com.igexin.sdk.PushManager;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yang.push.service.GeTuiService;
import com.yang.push.service.GetuiIntentService;
import com.yang.push.service.UmengService;

/**
 * Created by yang on 2017/7/31.
 */

public class XDFPushManager {
    private String TAG = "XDFPushManager";

    private volatile static XDFPushManager instance;
    private static Context appContext;
    private static final String CHANNEL = "CHANNEL";

    private XDFPushManager() {
    }

    public static XDFPushManager getInstance(Context context) {
        if (instance == null) {
            synchronized (XDFPushManager.class) {
                if (instance == null) {
                    instance = new XDFPushManager();
                    appContext = context;
                }
            }
        }
        return instance;
    }

    /**
     * 根据手机系统初始化各推送SDK
     */
    public void initPush() {
        //个推
        PushManager.getInstance().initialize(appContext, GeTuiService.class);
        PushManager.getInstance().registerPushIntentService(appContext, GetuiIntentService.class);

        //小米
//        int xiaomi_id;
//        try {
//            ApplicationInfo appInfo = appContext.getPackageManager()
//                    .getApplicationInfo(appContext.getPackageName(),
//                            PackageManager.GET_META_DATA);
//
//            xiaomi_id = appInfo.metaData.getInt("XIAOMI_ID");
//
//            Log.e("XMpush_appkey=", xiaomi_id+"--");
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//
//        }
        MiPushClient.registerPush(appContext, "2882303761517170087", "5291717034087");

        //友盟
        PushAgent mPushAgent = PushAgent.getInstance(appContext);
        mPushAgent.setDebugMode(false);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                System.out.println("ym注册成功" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

                System.out.println("ym注册失败" + s1);
            }
        });

        mPushAgent.setPushIntentServiceClass(UmengService.class);
    }

    private HuaweiApiClient mClient;

    private void initHuaweiPush(final Context context) {
        mClient = new HuaweiApiClient.Builder(context)
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(new HuaweiApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected() {
                        getToken();
                        Log.e(TAG, "HUAWEI onConnected, IsConnected: " + mClient.isConnected());

                    }

                    @Override
                    public void onConnectionSuspended(final int i) {
                        Log.e(TAG, "HUAWEI onConnectionSuspended, cause: " + i + ", IsConnected:" +
                                " " +
                                mClient.isConnected());
                    }
                })
                .addOnConnectionFailedListener(new HuaweiApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull final ConnectionResult
                                                           connectionResult) {
                        Log.e(TAG, "HUAWEI onConnectionFailed, ErrorCode: " + connectionResult.getErrorCode());
                    }
                })
                .build();
        mClient.connect();
    }


    private void getToken() {
        // 同步调用方式，不会返回token,通过广播的形式返回。
        new Thread(new Runnable() {
            @Override
            public void run() {
                PendingResult<TokenResult> token = HuaweiPush.HuaweiPushApi.getToken(mClient);
                token.await();
            }
        }).start();
    }

    public boolean isConnected() {
        if (mClient != null && mClient.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
