package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.TecherShow;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.view.JustifyText;
import com.example.kk.arttraining.ui.homePage.function.homepage.FollowCreate;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherContentData;
import com.example.kk.arttraining.ui.homePage.prot.IFollow;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherContent;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.ui.valuation.view.ValuationMain;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.utils.LruCacheUtils;
import com.example.kk.arttraining.utils.PhotoLoader;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/10/31.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacherContent extends Activity implements ITeacherContent, IFollow {

    TecherShow techerShow;
    TeacherContentData teacherContentData;
    String FollowType;

    @InjectView(R.id.iv_teacher_header)
    ImageView ivTeacherHeader;
    @InjectView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    //    @InjectView(R.id.tv_teacher_address)
//    TextView tvTeacherAddress;
    @InjectView(R.id.tv_teacher_title)
    TextView tvTeacherTitle;
    //    @InjectView(R.id.tv_teacher_specialty)
//    TextView tvTeacherSpecialty;
    @InjectView(R.id.tv_teacher_like)
    TextView tvTeacherLike;
    @InjectView(R.id.tv_teacher_fans)
    TextView tvTeacherFans;
    //    @InjectView(R.id.tv_teacher_group)
//    TextView tvTeacherGroup;
    @InjectView(R.id.tv_teacher_focus)
    TextView tvTeacherFocus;
    @InjectView(R.id.tv_teacher_introduction)
    JustifyText tvTeacherIntroduction;
    @InjectView(R.id.ll_teacher_valuation)
    LinearLayout llTeacherValuation;
    @InjectView(R.id.bt_only_valuation)
    Button btOnlyValuation;
    @InjectView(R.id.iv_teacher_focus_on)
    TextView ivTeacherFocusOn;
    @InjectView(R.id.fl_teacher_content)
    FrameLayout flTeacherContent;
    @InjectView(R.id.no_wifi)
    TextView noWifi;
    @InjectView(R.id.teacher_bg)
    ImageView teacherBg;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;

    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_teacher_content);
        ButterKnife.inject(this);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        if (getIntent().getStringExtra("type") != null && getIntent().getStringExtra("type").equals("valuation")) {
            llTeacherValuation.setVisibility(View.GONE);
            btOnlyValuation.setVisibility(View.GONE);
        }

        teacherContentData = new TeacherContentData(this);
        teacherContentData.getTeacherContentData(Integer.valueOf(getIntent().getStringExtra("tec_id")));

    }

    @OnClick({R.id.iv_teacher_focus_on, R.id.bt_teacher_measurement, R.id.bt_teacher_teaching, R.id.bt_only_valuation, R.id.iv_teacher_header, R.id.iv_title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_teacher_header:
                Intent intent_header = new Intent(this, ShareDynamicImage.class);
                intent_header.putExtra("image_path", techerShow.getPic());
                int[] location = new int[2];
                ivTeacherHeader.getLocationOnScreen(location);
                intent_header.putExtra("locationX", location[0]);
                intent_header.putExtra("locationY", location[1]);
                intent_header.putExtra("width", ivTeacherHeader.getWidth());
                intent_header.putExtra("height", ivTeacherHeader.getHeight());
                startActivity(intent_header);
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_teacher_focus_on:
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                    if (FollowType != null) {
                        if (FollowType.equals("no")) {
                            FollowCreate followCreate = new FollowCreate(this);
                            followCreate.getFocus("tec", techerShow.getTec_id());
                        } else {
                            UIUtil.ToastshowShort(this, "已经关注了");
                        }
                    }
                } else {
                    UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
                    startActivity(new Intent(this, UserLoginActivity.class));
                }

                break;
            case R.id.bt_teacher_measurement:
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                    List<TecInfoBean> list = new ArrayList<TecInfoBean>();
                    TecInfoBean tecInfoBean = new TecInfoBean();
                    tecInfoBean.setName(techerShow.getName());
                    tecInfoBean.setTec_id(techerShow.getTec_id());
                    tecInfoBean.setAss_pay(techerShow.getAss_pay());
                    list.add(tecInfoBean);
                    Intent intent = new Intent(this, ValuationMain.class);
                    intent.putExtra("mold", "onlyOne");
                    intent.putExtra("type", techerShow.getSpecialty());
                    intent.putStringArrayListExtra("tec", (ArrayList) list);
                    startActivity(intent);
                } else {
                    UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
                    startActivity(new Intent(this, UserLoginActivity.class));
                }

                break;
            case R.id.bt_teacher_teaching:
                break;
            case R.id.bt_only_valuation:
                if (Config.ACCESS_TOKEN != null && !Config.ACCESS_TOKEN.equals("")) {
                    List<TecInfoBean> list = new ArrayList<TecInfoBean>();
                    TecInfoBean tecInfoBean = new TecInfoBean();
                    tecInfoBean.setName(techerShow.getName());
                    tecInfoBean.setTec_id(techerShow.getTec_id());
                    tecInfoBean.setAss_pay(techerShow.getAss_pay());
                    list.add(tecInfoBean);
                    Intent intent = new Intent(this, ValuationMain.class);
                    intent.putExtra("mold", "onlyOne");
                    intent.putExtra("type", techerShow.getSpecialty());
                    intent.putStringArrayListExtra("tec", (ArrayList) list);
                    startActivity(intent);
                } else {
                    UIUtil.ToastshowShort(this, getResources().getString(R.string.toast_user_login));
                    startActivity(new Intent(this, UserLoginActivity.class));
                }
                break;
        }
    }

    @Override
    public void getTeacherContent(TecherShow techerShow) {
        this.techerShow = techerShow;

        tvTitleBar.setText(techerShow.getName());

        if (techerShow.getIdentity().equals("dr")) {
            llTeacherValuation.setVisibility(View.GONE);
            btOnlyValuation.setVisibility(View.GONE);
        }

        Glide.with(getApplicationContext()).load(techerShow.getPic()).transform(new GlideCircleTransform(this)).error(R.mipmap.default_user_header).into(ivTeacherHeader);
        tvTeacherName.setText(techerShow.getName());
        if (techerShow.getCollege() == null || techerShow.getCollege().equals("")) {
            tvTeacherTitle.setVisibility(View.INVISIBLE);
        } else {
            tvTeacherTitle.setText(techerShow.getTitle());
        }
        tvTeacherLike.setText(techerShow.getComment() + "");
        tvTeacherFans.setText(techerShow.getFans_num() + "");
        tvTeacherFocus.setText(techerShow.getBrowse_num() + "");
        //设置老师背景大图

        Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemCache(techerShow.getBg_pic());
        if (bitmap != null) {
            teacherBg.setImageBitmap(bitmap);
        } else {
            PhotoLoader.displayImageTarget(teacherBg, techerShow.getBg_pic(), PhotoLoader.getTarget(teacherBg,
                    techerShow.getBg_pic(), 0),R.mipmap.me_bg);
        }

//        Glide.with(getApplicationContext()).load(techerShow.getBg_pic()).error(R.mipmap.default_teacher_bg).into(teacherBg);
        String tv1 = techerShow.getIntroduction().replace("\\n", "\n\n");
        String tv2 = tv1.replace("\\u3000", "\u3000");
        techerShow.setIntroduction(tv2);
        tvTeacherIntroduction.setText(techerShow.getIntroduction());

        FollowType = techerShow.getIs_follow();
        if (techerShow.getIs_follow().equals("no")) {
            ivTeacherFocusOn.setText("+ 关注");
        } else if (techerShow.getIs_follow().equals("yes")) {
            ivTeacherFocusOn.setText("已关注");
        }

        loadingDialog.dismiss();
    }

    @Override
    public void OnTeacherContentFailure(String result) {
        UIUtil.ToastshowShort(this, result);
        loadingDialog.dismiss();
    }

    @Override
    public void NoWifi() {
        flTeacherContent.setVisibility(View.GONE);
        noWifi.setVisibility(View.VISIBLE);
        loadingDialog.dismiss();
    }

    @Override
    public void getCreateFollow() {
        ivTeacherFocusOn.setText("已关注");
        FollowType = "yes";
        UIUtil.ToastshowShort(this, "关注成功！");
    }

    @Override
    public void OnFollowFailure(String code, String msg) {
        UIUtil.ToastshowShort(this, msg);
        if (code.equals("20028")) {
            if (code.equals("20028")) {
                startActivity(new Intent(this, UserLoginActivity.class));
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        teacherContentData.getTeacherContentData(Integer.valueOf(getIntent().getStringExtra("tec_id")));
    }

}
