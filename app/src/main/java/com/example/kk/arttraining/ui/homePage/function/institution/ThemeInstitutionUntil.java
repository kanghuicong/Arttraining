package com.example.kk.arttraining.ui.homePage.function.institution;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.bean.parsebean.OrgListBean;
import com.example.kk.arttraining.bean.parsebean.SearchBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.ui.homePage.activity.ThemeInstitutionContent;
import com.example.kk.arttraining.ui.homePage.adapter.InstitutionFragmentAdapter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.yixia.camera.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeInstitutionUntil{

    public static void themeInstitutionUntil(final Context context, final ListView lvInstitution, String province) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("uid", Config.User_Id);
        map.put("city", "");
        map.put("province", "");
        map.put("type", "");
        map.put("self", "");
        map.put("prev", "");

        Callback<OrgListBean> callback = new Callback<OrgListBean>() {
            @Override
            public void onResponse(Call<OrgListBean> call, Response<OrgListBean> response) {
                OrgListBean orgListBean = response.body();
                if (response.body() != null) {
                    if (orgListBean.getError_code().equals("0")) {
                        final List<OrgBean> orgBeanList = orgListBean.getOrg();
                        InstitutionFragmentAdapter adapter = new InstitutionFragmentAdapter(context, orgBeanList);
                        lvInstitution.setAdapter(adapter);
                        lvInstitution.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                OrgBean orgBean = orgBeanList.get(position);
                                Intent intent = new Intent(context, ThemeInstitutionContent.class);
                                intent.putExtra("org_id", orgBean.getId()+"");
                                context.startActivity(intent);
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<OrgListBean> call, Throwable t) {}
        };

        Call<OrgListBean> call = HttpRequest.getCommonApi().orgList(map);
        call.enqueue(callback);
    }
}
