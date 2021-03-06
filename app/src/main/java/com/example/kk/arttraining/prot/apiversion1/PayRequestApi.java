package com.example.kk.arttraining.prot.apiversion1;

import com.example.kk.arttraining.bean.modelbean.GeneralBean;
import com.example.kk.arttraining.pay.bean.AliPay;
import com.example.kk.arttraining.pay.bean.RemainTimeBean;
import com.example.kk.arttraining.pay.bean.WeChat;
import com.example.kk.arttraining.prot.rxjava_retrofit.BaseModel;
import com.example.kk.arttraining.ui.valuation.bean.CommitOrderBean;
import com.example.kk.arttraining.utils.Config;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：wschenyongyin on 2016/10/30 20:11
 * 说明:支付调用的api接口
 */
public interface PayRequestApi {

    //获取微信支付的必要信息--测评
    @POST(Config.URL_PAY_REWORK)
    @FormUrlEncoded
    Call<WeChat> weChatPayData(@FieldMap Map<String, Object> map);


    //获取微信支付的必要信息--直播
    @POST(Config.URL_PAY_REWORK_SYSTEM)
    @FormUrlEncoded
    Call<WeChat> weChatPaySystem(@FieldMap Map<String, Object> map);

    //获取支付宝支付的必要信息
    @POST(Config.URL_PAY_REWORK)
    @FormUrlEncoded
    Call<AliPay> aliPayData(@FieldMap Map<String, Object> map);


    //提交订单
    @POST(Config.URL_ORDERS_CREATE)
    @FormUrlEncoded
    Call<CommitOrderBean> commitOrder(@FieldMap Map<String, Object> map);

    //附件上传完成后更新订单
    @POST(Config.URL_ORDERS_UPDATE)
    @FormUrlEncoded
    Call<GeneralBean> UpdateOrder(@FieldMap Map<String, Object> map);


    //其他支付获取orderNumber
    @POST(Config.URL_ORDERS_BUY)
    @FormUrlEncoded
    Observable<BaseModel<CommitOrderBean>> payOther(@FieldMap Map<String, Object> map);


    //取消订单
    @POST(Config.URL_ORDERS_CANCEL)
    @FormUrlEncoded
    Call<GeneralBean> CancelOrder(@FieldMap Map<String, Object> map);

    //获取订单剩余支付时间
    @POST(Config.URL_ORDERS_REMAINING_TIME)
    @FormUrlEncoded
    Call<RemainTimeBean> getRemainTime(@FieldMap Map<String, Object> map);

}
