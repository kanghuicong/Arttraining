package com.example.kk.arttraining.ui.teacher.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.NoPreloadViewPager;
import com.example.kk.arttraining.custom.view.NoScrollViewPager;
import com.example.kk.arttraining.ui.homePage.activity.SearchMain;
import com.example.kk.arttraining.ui.homePage.activity.ThemeTeacher;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by kanghuicong on 2016/12/8.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherMain extends Fragment {
    Activity activity;
    View view_teacher;

    @InjectView(R.id.viewPager)
    NoScrollViewPager viewPager;

    LocalActivityManager manager;
    @InjectView(R.id.rb_experts)
    RadioButton rbExperts;
    @InjectView(R.id.rb_teacher)
    RadioButton rbTeacher;
    @InjectView(R.id.rb_intelligent)
    RadioButton rbIntelligent;
    @InjectView(R.id.iv_teacher_back)
    ImageView ivTeacherBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        activity = getActivity();
        if (view_teacher == null) {
            view_teacher = View.inflate(activity, R.layout.homepage_teacher_all, null);
            ButterKnife.inject(this, view_teacher);
            manager = new LocalActivityManager(activity, true);
            manager.dispatchCreate(savedInstanceState);
            initPager();
        }
        ViewGroup parent = (ViewGroup) view_teacher.getParent();
        if (parent != null) {
            parent.removeView(view_teacher);
        }
        ButterKnife.inject(this, view_teacher);
        return view_teacher;
    }

    private void initPager() {
        ivTeacherBack.setVisibility(View.INVISIBLE);
        viewPager.setAdapter(new MyPageAdapter(activity.getApplicationContext()));
        viewPager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbExperts.setChecked(true);
                        break;
                    case 1:
                        rbTeacher.setChecked(true);
                        break;
                    case 2:
                        rbIntelligent.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @OnClick({R.id.rb_experts, R.id.rb_teacher, R.id.rb_intelligent, R.id.iv_teacher_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_teacher_search:
                Intent intent_school = new Intent(activity, SearchMain.class);
                intent_school.putExtra("type", "teacher");
                startActivity(intent_school);
                break;
            case R.id.rb_experts:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_teacher:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_intelligent:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public class MyPageAdapter extends PagerAdapter {
        List<View> list = new ArrayList<View>();
        Context context;

        public MyPageAdapter(Context context) {
            this.context = context;

            //仅仅是为了让list拥有三个基础数据
            View view = View.inflate(context, R.layout.homepage_listview_header, null);
            for (int i = 0; i < 3; i++) {
                list.add(view);
            }
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object arg2) {
            NoPreloadViewPager pViewPager = ((NoPreloadViewPager) view);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {

            switch (position) {
                case 0:
                    Intent i0 = new Intent(context, ThemeTeacher.class);
                    i0.putExtra("type", "zj");
                    list.set(position, getView("T0Activity", i0));
                    break;
                case 1:
                    Intent i1 = new Intent(context, ThemeTeacher.class);
                    i1.putExtra("type", "ms");
                    list.set(position, getView("T1Activity", i1));
                    break;
                case 2:
                    Intent i2 = new Intent(context, ThemeTeacher.class);
                    i2.putExtra("type", "dr");
                    list.set(position, getView("T2Activity", i2));
                    break;
            }

            NoPreloadViewPager pViewPager = ((NoPreloadViewPager) view);
            pViewPager.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

}
