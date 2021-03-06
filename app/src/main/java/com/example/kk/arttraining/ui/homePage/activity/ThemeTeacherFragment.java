package com.example.kk.arttraining.ui.homePage.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.ui.homePage.adapter.ThemeTeacherAdapter;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullableGridView;
import com.example.kk.arttraining.ui.homePage.function.teacher.TeacherSearchData;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherSearch;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ThemeTeacherFragment extends Fragment implements ITeacherSearch, PullToRefreshLayout.OnRefreshListener {

    String identity;
    String major;
    Activity activity;
    View view;

    TeacherSearchData teacherSearchData;
    ThemeTeacherAdapter teacherListViewAdapter;
    List<TecInfoBean> tecInfoBeanList = new ArrayList<TecInfoBean>();
    int teacher_num = 0;
    boolean Flag = false;
    int teacherPosition = 0;
    int refreshResult = PullToRefreshLayout.FAIL;

    @InjectView(R.id.gv_teacher)
    PullableGridView gvTeacher;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    @InjectView(R.id.tv_default_teacher)
    TextView tvDefaultTeacher;

    LoadingDialog progressDialog;
    @InjectView(R.id.lv_art_type)
    ListView lvArtType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();

        if (view == null) {
            view = View.inflate(activity, R.layout.homepage_teacher_fragment, null);
            ButterKnife.inject(this, view);
            lvArtType.setVisibility(View.GONE);
            identity = getArguments().getString("type");
            major = getArguments().getString("major");

            progressDialog = new LoadingDialog(activity);
            progressDialog.setTitle("数据加载中");
            progressDialog.show();

            teacherSearchData = new TeacherSearchData(this);
            teacherSearchData.getTeacherListData(identity, major);
            refreshView.setOnRefreshListener(this);
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void getTeacher(List<TecInfoBean> tecInfoBeanList1) {
        Flag = true;
        tvDefaultTeacher.setVisibility(View.GONE);


        //名师列表
        if (teacherPosition == 0) {
            teacher_num = tecInfoBeanList1.size();
            tecInfoBeanList.addAll(tecInfoBeanList1);
            teacherListViewAdapter = new ThemeTeacherAdapter(activity.getApplicationContext(), tecInfoBeanList);
            gvTeacher.setAdapter(teacherListViewAdapter);
            gvTeacher.setOnItemClickListener(new TeacherListItemClick());
            teacherPosition++;
        } else {
            tecInfoBeanList.clear();
            tecInfoBeanList.addAll(tecInfoBeanList1);
            teacherListViewAdapter.ChangeCount(tecInfoBeanList1.size());
            teacherListViewAdapter.notifyDataSetChanged();
            teacher_num = tecInfoBeanList.size();
        }
        progressDialog.dismiss();
    }


    //名师列表点击事件
    private class TeacherListItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(activity, ThemeTeacherContent.class);
            intent.putExtra("tec_id", tecInfoBeanList.get(position).getTec_id() + "");
            startActivity(intent);
        }
    }

    @Override
    public void loadTeacher(List<TecInfoBean> tecInfoBeanList1) {

        if (tecInfoBeanList.size() == 0 || tecInfoBeanList == null) {
            getTeacher(tecInfoBeanList1);
        } else {
            tecInfoBeanList.addAll(tecInfoBeanList1);
            teacher_num = teacher_num + tecInfoBeanList1.size();

            teacherListViewAdapter.ChangeCount(teacher_num);
            teacherListViewAdapter.notifyDataSetChanged();
        }
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void OnLoadTeacherFailure(int result) {
        switch (result) {
            case 0:
                refreshResult = PullToRefreshLayout.EMPTY;
                break;
            case 1:
                refreshResult = PullToRefreshLayout.FAIL;
                break;
            case 2:
                refreshResult = PullToRefreshLayout.FAIL;
                UIUtil.ToastshowShort(activity, "网络连接失败！");
                break;
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {

                refreshView.loadmoreFinish(refreshResult);

            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void OnTeacherFailure(String result) {
        progressDialog.dismiss();
        if (result.equals("null")) {
            tvDefaultTeacher.setVisibility(View.VISIBLE);
        }else {
            UIUtil.ToastshowShort(activity, result);
        }
    }

    @Override
    public void OnFailure(String error_code) {
        tvDefaultTeacher.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        teacherSearchData.getTeacherListData(identity, major);
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        if (Flag) {
            UIUtil.showLog("loadTeacher_Self", teacherListViewAdapter.getSelfId() + "");
            teacherSearchData.loadTeacherListData(teacherListViewAdapter.getSelfId(), identity, major);
        } else {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    refreshView.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }


}

