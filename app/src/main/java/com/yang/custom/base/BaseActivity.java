package com.yang.custom.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yang.custom.R;
import com.yang.custom.utils.AppManger;
import com.yang.custom.utils.NetUtils;

/**
 * Created by yang on 2017/7/25.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected LayoutInflater mInflater;
    protected String TAG;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        AppManger.getAppManager().addActivity(this);
        activity = this;
        setContentView();
        initializeView();
        initializeData();
    }

    protected abstract void setContentView();

    protected abstract void initializeView();

    protected abstract void initializeData();

    /**
     * 打开一个Activity 默认 不关闭当前activity
     */
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(this, clz);
        if (ex != null) intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finish();
        }
    }

    /**
     * 统一初始化titlebar
     */
    protected void initToolBar(String title) {
        ImageView mBackImg = (ImageView) findViewById(R.id.toolbar_back_img);
        TextView mTitleTxt = (TextView) findViewById(R.id.toolbar_title);
        mTitleTxt.setText(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    /**
     * 统一初始化titlebar右侧图片
     */
    protected void initToolBarRightImg(String title, int rightId, View.OnClickListener listener) {
        ImageView mBackImg = (ImageView) findViewById(R.id.toolbar_back_img);
        TextView mTitleTxt = (TextView) findViewById(R.id.toolbar_title);
        mTitleTxt.setText(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        TextView mRightTxt = (TextView) findViewById(R.id.toolbar_right_txt);
        mRightTxt.setVisibility(View.GONE);
        ImageView mRightImg = (ImageView) findViewById(R.id.toolbar_right_img);
        mRightImg.setVisibility(View.VISIBLE);
        mRightImg.setImageResource(rightId);
        mRightImg.setOnClickListener(listener);
    }

    /**
     * 统一初始化titlebar右侧文字
     */
    protected void initToolBarRightTxt(String title, String right, View.OnClickListener listener) {
        ImageView mBackImg = (ImageView) findViewById(R.id.toolbar_back_img);
        TextView mTitleTxt = (TextView) findViewById(R.id.toolbar_title);
        mTitleTxt.setText(title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        ImageView mRightImg = (ImageView) findViewById(R.id.toolbar_right_img);
        mRightImg.setVisibility(View.GONE);
        TextView mRightTxt = (TextView) findViewById(R.id.toolbar_right_txt);
        mRightTxt.setVisibility(View.VISIBLE);
        mRightTxt.setText(right);
        mRightTxt.setOnClickListener(listener);
    }

    protected void checkNet(View.OnClickListener listener) {
        LinearLayout mNoNet = (LinearLayout) findViewById(R.id.base_nonet_container);
        TextView mRefresh = (TextView) findViewById(R.id.base_nonet_refresh);
        if (NetUtils.isConnected(this)) {
            mNoNet.setVisibility(View.GONE);
        } else {
            mNoNet.setVisibility(View.VISIBLE);
        }
        mRefresh.setOnClickListener(listener);
    }

    protected void back() {
        if (activity != null) {
            activity.finish();
        }
    }

}
