package com.yang.push.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/**
 * Created by yang on 2017/8/1.
 */

public class UmengService extends UmengMessageService {
    private String TAG = "UmengService";

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            Log.e(TAG, "onReceiveMessageData -> " + "UMmsg = " + msg.toString());
            Log.e(TAG, "message=" + message);    //消息体
            Log.e(TAG, "custom=" + msg.custom);    //自定义消息的内容
            Log.e(TAG, "title=" + msg.title);    //通知标题
            Log.e(TAG, "text=" + msg.text);    //通知内容

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
