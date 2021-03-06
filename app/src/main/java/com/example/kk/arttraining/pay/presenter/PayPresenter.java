package com.example.kk.arttraining.pay.presenter;

import android.app.Activity;

import com.example.kk.arttraining.bean.modelbean.GeneralBean;
import com.example.kk.arttraining.pay.alipay.AlipayUtil;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.RemainTimeBean;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.pay.view.IPayActivity;
import com.example.kk.arttraining.pay.wxapi.Constants;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/30 20:17
 * 说明:
 */
public class PayPresenter {
    private IPayActivity iPayActivity;
    private AliPay aliPay;
    private WeChat weChatBean;
    private Activity activity;
    private Boolean state = false;
    private AlipayUtil alipayUtil;

    public PayPresenter() {
    }

    public PayPresenter(IPayActivity iPayActivity, Activity activity) {
        this.iPayActivity = iPayActivity;
        this.activity = activity;
    }


    public void AliPay(Map<String, Object> map, String pay_type, CommitOrderBean commitOrderBean,String from) {

        switch (pay_type) {
            //如果从后台获取支付宝密钥成功，调用支付宝进行支付
            case "alipay":
                if (getAlipayInfo(map)) {
                    alipayUtil = new AlipayUtil(activity);
                    alipayUtil.pay(commitOrderBean);
                } else {
                    sendFailure("500", "");
                }
                break;
            //如果从后台获取微信支付密钥成功，调用微信进行支付
            case "wechat":

                if (Constants.isInstallWX(activity)) {
                    if (getWeChatPayInfo(map,from)){}
                } else {
                    sendFailure("600", "");
                }
                break;
        }

    }


    //获取支付宝支付必要信息
    Boolean getAlipayInfo(Map<String, Object> map) {
        Callback<AliPay> aliPayCallback = new Callback<AliPay>() {
            @Override
            public void onResponse(Call<AliPay> call, Response<AliPay> response) {

                if (response.body() != null) {
                    aliPay = response.body();
                    if (aliPay.getError_code().equals("0")) {
                        iPayActivity.getAliPayPermissions(aliPay);
                        state = true;
                    } else {
                        iPayActivity.showFailure(aliPay.getError_code(), aliPay.getError_msg());
                    }
                } else {
                    iPayActivity.showFailure(response.code() + "", Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<AliPay> call, Throwable t) {
                iPayActivity.showFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };

        Call<AliPay> call = HttpRequest.getPayApi().aliPayData(map);
        call.enqueue(aliPayCallback);
        return state;
    }

    Boolean getWeChatPayInfo(Map<String, Object> map,String from) {
        Callback<WeChat> aliPayCallback = new Callback<WeChat>() {
            @Override
            public void onResponse(Call<WeChat> call, Response<WeChat> response) {
                UIUtil.showLog("getWeChatPayInfo--->response", response.code() + "--->" + response.message());
                if (response.body() != null) {
                    weChatBean = response.body();
                    UIUtil.showLog("getWeChatPayInfo--->weChatBean", "--->" + weChatBean.toString());
                    if (weChatBean.getError_code().equals("0")) {
                        iPayActivity.wxPay(weChatBean.getModel());
                        state = true;
                    } else {
                        sendFailure(weChatBean.getError_code(), weChatBean.getError_msg());
                    }
                } else {
                    sendFailure(response.code() + "", Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<WeChat> call, Throwable t) {
                UIUtil.showLog("getWeChatPayInfo--->onFailure", "--->" + t.getMessage() + "---->" + t.getCause());
                sendFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };

        if ("live".equals(from)) {
            Call<WeChat> call = HttpRequest.getPayApi().weChatPaySystem(map);
            call.enqueue(aliPayCallback);
        } else{
            Call<WeChat> call = HttpRequest.getPayApi().weChatPayData(map);
            call.enqueue(aliPayCallback);
        }
        return state;
    }

    //取消订单
    public void cancelOrder(Map<String, Object> map) {

        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                GeneralBean generalBean = response.body();
                UIUtil.showLog("paypresenter--->onresponse:", response.code() + "--->" + response.message());
                if (generalBean != null) {
                    if (generalBean.getError_code().equals("0")) {
                        iPayActivity.cancelOrderSuccess();
                    } else {
                        iPayActivity.cancelOrderFailure(generalBean.getError_code(), generalBean.getError_msg());
                    }
                } else {
                    iPayActivity.cancelOrderFailure(response.code() + "", generalBean.getError_msg());
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                UIUtil.showLog("paypresenter--->onFailure", t.getMessage() + "--->" + t.getCause());
                iPayActivity.cancelOrderFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };
        Call<GeneralBean> call = HttpRequest.getPayApi().CancelOrder(map);
        call.enqueue(callback);
    }

    //获取订单支付剩余时间
    public void getRemainTime(Map<String, Object> map) {
        Callback<RemainTimeBean> callback = new Callback<RemainTimeBean>() {
            @Override
            public void onResponse(Call<RemainTimeBean> call, Response<RemainTimeBean> response) {
                UIUtil.showLog("getRemainTime---->",response.code()+"--->"+response.message());
                RemainTimeBean remainTimeBean = response.body();
                if (remainTimeBean != null) {
                    UIUtil.showLog("getRemainTime---->remainTimeBean",remainTimeBean.toString()+"--->");
                    if (remainTimeBean.getError_code().equals("0")) {
                        iPayActivity.SuccessRemainTime(remainTimeBean.getRemaining_time());
                    } else {
                        iPayActivity.FailureRemainTime();
                    }
                } else {
                    iPayActivity.FailureRemainTime();
                }
            }

            @Override
            public void onFailure(Call<RemainTimeBean> call, Throwable t) {
                UIUtil.showLog("getRemainTime---->onFailure",t.getMessage()+"--->"+t.getCause());
                iPayActivity.FailureRemainTime();
            }
        };
        Call<RemainTimeBean> call = HttpRequest.getPayApi().getRemainTime(map);
        call.enqueue(callback);

    }


    public void sendSuccess() {
        iPayActivity.showSuccess();
    }

    public void sendFailure(String error_code, String error_msg) {
        iPayActivity.showFailure(error_code, error_msg);
    }


}
