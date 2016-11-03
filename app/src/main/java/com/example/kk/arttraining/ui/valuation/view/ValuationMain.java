package com.example.kk.arttraining.ui.valuation.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.dialog.PopWindowDialogUtil;
import com.example.kk.arttraining.pay.PayActivity;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.me.view.CouponActivity;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.ui.valuation.presenter.ValuationMainPresenter;
import com.example.kk.arttraining.utils.AudioRecordWav;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.DialogUtils;
import com.example.kk.arttraining.utils.TitleBack;
import com.example.kk.arttraining.utils.UIUtil;
import com.maiml.wechatrecodervideolibrary.recoder.WechatRecoderActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/31 09:52
 * 说明:测评主页面
 */

public class ValuationMain extends BaseActivity implements IValuationMain {
    //作品类型
    @InjectView(R.id.valuation_tv_type)
    TextView valuation_tv_type;
    //作品名称
    @InjectView(R.id.valuation_et_name)
    EditText valuation_et_name;
    //作品描述
    @InjectView(R.id.valuation_describe)
    EditText valuation_et_describe;
    //选择附件
    @InjectView(R.id.iv_enclosure)
    ImageView iv_enclosure;
    //选择名师
    @InjectView(R.id.valuation_iv_increase)
    ImageView valuation_iv_choseTeacher;
    //确定支付
    @InjectView(R.id.iv_sure_pay)
    ImageView iv_sure_pay;
    //实付金额
    @InjectView(R.id.tv_real_cost)
    TextView tv_real_cost;
    //测评费用
    @InjectView(R.id.tv_cost)
    TextView tv_cost;
    //测评费用
    @InjectView(R.id.tv_coupon_cost)
    TextView tv_coupon_cost;

    @InjectView(R.id.valuation_main_ll_coupons)
    LinearLayout ll_coupon;


    private String valuation_type;
    private AudioRecordWav audioFunc;
    //选择老师
    public static final int CHOSE_TEACHER = 1001;
    //选择作品
    public static final int CHOSE_PRODUCTION = 1002;
    //选择优惠券
    public static final int CHOSE_COUPON = 1003;
    private TecInfoBean tecInfoBean;
    private Dialog loadingDialog;
    private ValuationMainPresenter valuationMainPresenter;
    private PopWindowDialogUtil popWindowDialogUtil;
    private Intent choseProductionIntent;

    /**
     * 变量
     */
    //优惠券价格
    private String coupon_price;
    //作品价格
    private String production_price = "60";
    //实付款
    private String real_price;
    //作品标题
    private String production_title;
    //作品说明
    private String production_content;
    //作品文件地址
    private String production_path;
    //封装的名师列表
    private String teacher_list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_main);
        ButterKnife.inject(ValuationMain.this);
        init();
    }

    @Override
    public void init() {
        loadingDialog = DialogUtils.createLoadingDialog(ValuationMain.this, "");
        audioFunc = new AudioRecordWav();
        valuationMainPresenter = new ValuationMainPresenter(this);
        TitleBack.TitleBackActivity(ValuationMain.this, "名师测评");
        Intent intent = getIntent();
        valuation_type = intent.getStringExtra("type");
        valuation_tv_type.setText(valuation_type);
    }

    @OnClick({R.id.valuation_iv_increase, R.id.valuation_describe, R.id.iv_sure_pay, R.id.iv_enclosure, R.id.valuation_main_ll_coupons})
    public void onClick(View v) {
        switch (v.getId()) {
            //选择老师
            case R.id.valuation_iv_increase:
//                Intent choseTeacherIntent = new Intent(ValuationMain.this, ChoserTeacher.class);
//                startActivityForResult(choseTeacherIntent, CHOSE_TEACHER);
//                UIUtil.showLog("录音大小1", audioFunc.getRecordFileSize() + "");
//                audioFunc.stopRecordAndFile();
//
//                UIUtil.showLog("录音大小2", audioFunc.getRecordFileSize() + "");
//                startActivity(new Intent(this, AudioActivity.class));
                valuationMainPresenter.showPopwindow(ValuationMain.this);
                break;
            //提交订单
            case R.id.iv_sure_pay:
                Map<String, String> map = new HashMap<String, String>();
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("uid", Config.UID);
                map.put("ass_type", valuation_type);
                map.put("title", production_title);
                map.put("content", production_content);
                map.put("attachment", production_path);
                map.put("total_pay", production_price);
                map.put("coupon_pay", coupon_price);
                map.put("final", real_price);
                map.put("teacher_list", teacher_list);

                valuationMainPresenter.CommitOrder(map);
                break;
            case R.id.iv_enclosure:
//                Intent intent = new Intent(ValuationMain.this, MediaRecorderActivity.class);
//                startActivityForResult(intent, 7001);

//                WechatRecoderActivity.launchActivity(this, 7001);
//                Intent intent1 = new Intent(this, MediaRecorderActivity.class);
//                startActivityForResult(intent1, 7001);

//                audioFunc.startRecordAndFile();
                showDialog();

                break;

            case R.id.valuation_main_ll_coupons:
                Intent intent = new Intent(this, CouponActivity.class);
                intent.putExtra("from", "ValuationActivity");
                startActivityForResult(intent, CHOSE_COUPON);
                break;
        }
    }

    void showDialog() {
        popWindowDialogUtil = new PopWindowDialogUtil(ValuationMain.this, R.style.transparentDialog, R.layout.dialog_chose_production, "chose_production", new PopWindowDialogUtil.ChosePicDialogListener() {
            @Override
            public void onClick(View view) {
                popWindowDialogUtil.dismiss();
                switch (view.getId()) {

                    case R.id.btn_valutaion_dialog_cancel:

                        break;
                    case R.id.btn_valutaion_dialog_video:
                        choseProductionIntent = new Intent(ValuationMain.this, MediaActivity.class);
                        choseProductionIntent.putExtra("media_type", "video");
                        startActivityForResult(choseProductionIntent, CHOSE_PRODUCTION);
                        break;
                    case R.id.btn_valutaion_dialog_music:
                        choseProductionIntent = new Intent(ValuationMain.this, AudioActivity.class);
                        startActivityForResult(choseProductionIntent, CHOSE_PRODUCTION);
                        break;
                }

            }
        });
        //设置从底部显示
        Window window = popWindowDialogUtil.getWindow();
        popWindowDialogUtil.show();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }


    @Override
    public String getValuationName() {
        return valuation_et_name.getText().toString();
    }

    @Override
    public String getValuationDescribe() {
        return valuation_et_describe.getText().toString();
    }

    //设置付款金额
    @Override
    public void setCostPay() {

    }

    //设置实付金额
    @Override
    public void setRealCostPay() {

    }

    //获取作品名称
    @Override
    public String getProductionName() {
        production_title = valuation_et_name.getText().toString();
        return production_title;
    }

    //获取作品描述
    @Override
    public String getProductionDescribe() {
        production_content = valuation_et_describe.getText().toString();
        return production_content;
    }

    //获取选择老师信息
    @Override
    public TecInfoBean getTeacherInfo() {
        return tecInfoBean;
    }

    //获取作品文件路径
    @Override
    public String getProductionPath() {
        return production_path;
    }

    //提交订单
    @Override
    public void CommitOrder() {
        Intent commitIntent = new Intent(ValuationMain.this, PayActivity.class);
        CommitOrderBean orderBean = new CommitOrderBean();
        Bundle bundle = new Bundle();
        bundle.putSerializable("order_bean", orderBean);
        commitIntent.putExtras(bundle);
        startActivity(commitIntent);
    }

    @Override
    public void OnFailure(String error_code) {
        switch (error_code) {
            case "500":
                mHandler.sendEmptyMessage(500);
                break;
            case "501":
                mHandler.sendEmptyMessage(501);
                break;
        }
    }

    //显示加载dialog
    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    //隐藏加载dialog
    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    //回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                //选择老师返回
                case CHOSE_TEACHER:
                    tecInfoBean = (TecInfoBean) data.getExtras().getSerializable("tecinfo");
                    // TODO: 2016/11/2  
                    break;
                //选择作品返回
                case CHOSE_PRODUCTION:
                    production_path = data.getStringExtra("production_path");
                    break;
                //选择优惠券返回
                case CHOSE_COUPON:
                    coupon_price = data.getStringExtra("values");
                    tv_coupon_cost.setText("￥" + coupon_price);
                    real_price = (Integer.parseInt(production_price) - Integer.parseInt(coupon_price)) + "";
                    tv_cost.setText("￥" + real_price);
                    break;


            }
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 500:
                    UIUtil.ToastshowShort(ValuationMain.this, getResources().getString(R.string.connection_timeout));
                    break;
                case 501:
                    UIUtil.ToastshowShort(ValuationMain.this, getResources().getString(R.string.data_no_full));
                    break;

            }
        }
    };


}