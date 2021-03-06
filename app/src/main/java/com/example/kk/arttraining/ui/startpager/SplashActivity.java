package com.example.kk.arttraining.ui.startpager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.kk.arttraining.MainActivity;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.ToolKits;
import com.example.kk.arttraining.utils.UIUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 作者：wschenyongyin on 2016/9/22 16:10
 * 说明:启动页
 */
public class SplashActivity extends Activity {

    public static final String IS_FIRST = "is_first";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(SplashActivity.this, R.layout.activity_splash, null);
        context = getApplicationContext();
        Config.ACCESS_TOKEN = PreferencesUtils.get(getApplicationContext(), "access_token", "").toString();
        Config.UID = (int) PreferencesUtils.get(getApplicationContext(), "uid", 0);
        Config.User_Id = PreferencesUtils.get(getApplicationContext(), "user_code", "").toString();
        Config.USER_TITLE = PreferencesUtils.get(getApplicationContext(), "user_title", "").toString();
        Config.CITY = PreferencesUtils.get(getApplicationContext(), "province", "").toString();
        Config.USER_NAME = PreferencesUtils.get(getApplicationContext(), "user_name", "").toString();
        UIUtil.showLog("ACCESS_TOKEN------>", Config.ACCESS_TOKEN);
        UIUtil.showLog("UID-->", Config.UID + "");
//        setJpushTag("13155822449");

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(3000);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                skip();
            }
        });
        view.setAnimation(animation);
        setContentView(view);
    }

    private void skip() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    //全部权限获取成功
                    if (granted) {
                        enty();
                    }
                    //获取了部分权限或者获取权限失败
                    else {
                        Toast.makeText(SplashActivity.this, "获取权限失败,部分功能将无法使用", Toast.LENGTH_LONG).show();
                        enty();
                    }
                });
    }

    private void enty() {
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (!ToolKits
                        .fetchBooble(SplashActivity.this, IS_FIRST, false)) {
                    startActivity(new Intent(SplashActivity.this,
                            GuideActivity.class));
                    ToolKits.putBooble(SplashActivity.this, IS_FIRST, true);
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                ToolKits.putBooble(SplashActivity.this, IS_FIRST, true);
                return true;
            }
        }).sendEmptyMessageDelayed(0, 0);
    }


    public void setJpushTag(String user_code) {
        mHandler2.sendMessage(mHandler2.obtainMessage(10001, user_code));
    }

    //设置极光推送的别名
    private final Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10001:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            UIUtil.showLog("设置jpush别名---》", code + "");
            String logs;
            switch (code) {
                case 0:
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    UIUtil.showLog("设置别名成功------->", "true");
                    break;
                case 6002:
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler2.sendMessageDelayed(mHandler2.obtainMessage(10001, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;

            }
        }

    };

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
}
