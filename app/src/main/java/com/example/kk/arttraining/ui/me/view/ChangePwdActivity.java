package com.example.kk.arttraining.ui.me.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.AboutActivity;
import com.example.kk.arttraining.ui.me.presenter.ChangePwdPresenter;
import com.example.kk.arttraining.utils.ActivityManage;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/11 21:09
 * 说明:修改密码
 */
public class ChangePwdActivity extends BaseActivity implements IChangePwdActivity {
    ChangePwdPresenter presenter;
    @InjectView(R.id.et_changepwd)
    EditText etChangepwd;
    @InjectView(R.id.et_changepwd_again)
    EditText etChangepwdAgain;
    @InjectView(R.id.btn_chagepwd_ok)
    Button btnChagepwdOk;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_change_pwd);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        TitleBack.TitleBackActivity(this, "修改密码");
        presenter = new ChangePwdPresenter(this);
        dialog = DialogUtils.createLoadingDialog(this, "");
    }

    //点击完成按钮
    @OnClick(R.id.btn_chagepwd_ok)
    public void onClick(View v) {
        changePwd();
    }

    //修改密码
    @Override
    public void changePwd() {
        presenter.changePwd(etChangepwd.getText().toString(), etChangepwdAgain.getText().toString());

    }

    @Override
    public void Success() {
        //跳转到登陆页面
        PreferencesUtils.remove(this,"access_token");
        PreferencesUtils.remove(this,"uid");
        PreferencesUtils.remove(this,"user_code");
        UIUtil.ToastshowShort(this, "修改密码成功，请重新登陆！");
        startActivity(new Intent(this, UserLoginActivity.class));
        ActivityManage.getAppManager().finishActivity(AboutActivity.class);
        finish();
    }

    //修改密码失败
    @Override
    public void Failure(String error_msg) {
        UIUtil.ToastshowShort(this, error_msg);
    }

    @Override
    public void ShowLoading() {
        dialog.show();

    }

    @Override
    public void HideLoading() {
        dialog.dismiss();
    }
}
