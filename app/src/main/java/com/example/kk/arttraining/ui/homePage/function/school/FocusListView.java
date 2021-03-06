package com.example.kk.arttraining.ui.homePage.function.school;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.kk.arttraining.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/10/29.
 * QQ邮箱:515849594@qq.com
 */
public class FocusListView {
    public static void initFocus(Context context, ListView lvSchoolContentFocus) {

        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();

        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("content", i + "");
            mList.add(map);
            SimpleAdapter gv_adapter = new SimpleAdapter(context, mList,
                    R.layout.homepage_province_postion_item, new String[]{"content"},
                    new int[]{R.id.tv_province_name});
            lvSchoolContentFocus.setAdapter(gv_adapter);
            lvSchoolContentFocus.setOnItemClickListener(new FocusItemClick());
        }
    }

    public static class FocusItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
