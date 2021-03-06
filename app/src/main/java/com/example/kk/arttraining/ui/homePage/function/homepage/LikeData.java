package com.example.kk.arttraining.ui.homePage.function.homepage;


import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.kk.arttraining.bean.modelbean.GeneralBean;
import com.example.kk.arttraining.ui.homePage.prot.ILike;
import com.example.kk.arttraining.ui.me.view.UserLoginActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.TimeDelayClick;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/17.
 * QQ邮箱:515849594@qq.com
 */
public class LikeData {
    ILike iLike;
    public LikeData(ILike iLike) {
        this.iLike = iLike;
    }

    public void getLikeData(final Context context, String like_state, int like_id, String type, TextView tv_like) {
        if (TimeDelayClick.isFastClick(500)) {
            return;
        } else {
            UIUtil.showLog("like_state",like_state);
            if (like_state.equals("no")) {
                if (Config.ACCESS_TOKEN == null || Config.ACCESS_TOKEN.equals("")) {
                    UIUtil.ToastshowShort(context, "请先登录...");
                } else {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("access_token", Config.ACCESS_TOKEN);
                    map.put("uid", Config.UID);
                    map.put("utype", Config.USER_TYPE);
                    map.put("like_id", like_id);

                    Callback<GeneralBean> callback = new Callback<GeneralBean>() {
                        @Override
                        public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                            GeneralBean generalBean = response.body();
                            if (response.body() != null) {
                                if (generalBean.getError_code().equals("0")) {
                                    UIUtil.showLog("GeneralBean", "GeneralBean");
                                    iLike.getLike();
                                }else {
                                    UIUtil.ToastshowLong(context, generalBean.getError_msg());
                                    if (generalBean.getError_code().equals("20028")){
                                        context.startActivity(new Intent(context, UserLoginActivity.class));
                                    }
                                }
                            } else {
                                UIUtil.ToastshowLong(context, "点赞失败！");
                            }
                        }
                        @Override
                        public void onFailure(Call<GeneralBean> call, Throwable t) {
                            UIUtil.ToastshowLong(context, "网络连接失败！");
                        }
                    };
                    if (type.equals("work")) {
                        Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesLikeCreateWork(map);
                        call.enqueue(callback);
                    } else if (type.equals("status")) {
                        Call<GeneralBean> call = HttpRequest.getStatusesApi().statusesLikeCreateBBS(map);
                        call.enqueue(callback);
                    }
                }
            } else if (like_state.equals("yes")) {
                UIUtil.ToastshowShort(context, "已点赞！");
            }
        }
    }
}
