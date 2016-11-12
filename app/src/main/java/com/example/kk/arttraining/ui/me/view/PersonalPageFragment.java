package com.example.kk.arttraining.ui.me.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.VideoView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.DynamicItemClick;

import java.util.List;
import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/11/10 11:31
 * 说明:个人主页fragment
 */
public class PersonalPageFragment extends Fragment {
    private View view;
    Activity context;
    List<Map<String, Object>> mapList;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        view = View.inflate(context, R.layout.me_personal_page_fragment, null);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void getUId(List<Map<String, Object>> mapList, Activity activity) {
        DynamicAdapter dynamicadapter = new DynamicAdapter(activity, mapList);
        view.findViewById(R.id.lv_personal_page);
        listView.setAdapter(dynamicadapter);
        listView.setOnItemClickListener(new DynamicItemClick(context, mapList));

    }

    ;
}