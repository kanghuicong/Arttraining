package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.presenter.RegisterPresenter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/5 19:12
 * 说明:注册第一个页面 填写用户手机号码  并发送手机号码
 */
public class RegisterSendPhone extends BaseActivity implements IRegister {

    @InjectView(R.id.et_login_password)
    EditText etLoginPassword;
    @InjectView(R.id.btn_register_next)
    Button btnRegisterNext;
    @InjectView(R.id.et_recommend)
    EditText etRecommend;
    @InjectView(R.id.ress_hint)
    TextView ressHint;
    @InjectView(R.id.ress_hint2)
    TextView ressHint2;

    private String error_code;
    private String phoneNum;
    private Dialog loadingDialog;
    private RegisterPresenter registerPresenter;
    private String from = null;//标记是从哪里过来的
    private String code_type;
    private String recommend_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_resigster_sendphone);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {

        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("register")) {
            TitleBack.TitleBackActivity(RegisterSendPhone.this, "注册");
            code_type = "reg_code";
        } else {
            code_type = "identity_code";
            TitleBack.TitleBackActivity(RegisterSendPhone.this, "找回密码");
            ressHint.setVisibility(View.GONE);
            ressHint2.setVisibility(View.GONE);
        }
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(RegisterSetPwd.FINISH_ACTION);
        registerReceiver(myReceiver, filter);


        registerPresenter = new RegisterPresenter(this);
        loadingDialog = DialogUtils.createLoadingDialog(RegisterSendPhone.this, "");

    }

    @OnClick(R.id.btn_register_next)
    public void onClick(View v) {
        phoneNum = etLoginPassword.getText().toString();

        if (StringUtils.isPhone(phoneNum)) {
            checkIsRegister();

        } else {
            UIUtil.ToastshowShort(this, "请输入正确的手机号码");
        }

    }


    //验证手机是否注册过
    void checkIsRegister() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", phoneNum);
        registerPresenter.checkIsRegister(map);
    }

    //验证邀请是否有效
    void checkRecommend() {
        if (recommend_code.length() != 4) {
            UIUtil.ToastshowShort(this, "您输入");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("invite_code", recommend_code);
        registerPresenter.checkRecommend(map);
    }

    //获取验证码
    void getVerificationCode() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", phoneNum);
        map.put("code_type", code_type);
        registerPresenter.getVerificatioCode(map);
    }


    //成功
    @Override
    public void onSuccess() {
        hideLoading();
        Intent intent = new Intent(RegisterSendPhone.this, RegisterCheckVerificationCode.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("from", from);
        startActivity(intent);
    }

    @Override
    public void RegisterSuccess(UserLoginBean userLoginBean) {

    }

    //检查推荐成功
    @Override
    public void checkRecommendSuccess() {
        getVerificationCode();

    }

    //检查用户是否注册成功
    @Override
    public void checkIsRegisterSuccess() {
        getVerificationCode();
    }

    //失败
    @Override
    public void onFailure(String error_code) {
        hideLoading();
        this.error_code = error_code;
//        UIUtil.ToastshowShort(this, error_code);
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (error_code) {
                case Config.Connection_Failure:
                    UIUtil.ToastshowShort(RegisterSendPhone.this, getResources().getString(R.string.connection_failure));
                    break;
                case "20024":
                    if (from.equals("register")) {
                        UIUtil.ToastshowShort(RegisterSendPhone.this, "手机号码已注册");
                    } else {

                        getVerificationCode();
                    }
                    break;
            }
        }
    };

    //关闭activity
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            UIUtil.showLog("2222222222", "---->");
            finish();

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) unregisterReceiver(myReceiver);

    }
}
