package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.ConditionBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 11:47
 * 说明:省份listview适配器
 */
public class SchoolProvinceAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private Context context;
    private List<ConditionBean> provinceBeanList;
    private int selectPostion;

    public SchoolProvinceAdapter(Context context) {
        this.context = context;
    }

    public SchoolProvinceAdapter(Context context, List<ConditionBean> provinceBeanList) {
        this.context = context;
        this.provinceBeanList = provinceBeanList;
    }

    @Override
    public int getCount() {
        return provinceBeanList.size();
    }

    @Override
    public Object getItem(int position) {


        return provinceBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConditionBean bean = provinceBeanList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_school_province, null);
            viewHolder.province_name = (TextView) convertView.findViewById(R.id.tv_school_province);
            viewHolder.ll_layout = (LinearLayout) convertView.findViewById(R.id.ll_layout);
            viewHolder.view_splitter = (View) convertView.findViewById(R.id.view_splitter);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.province_name.setText(bean.getName());

        try {
            if (position == selectPostion) {
                viewHolder.province_name.setTextColor(context.getResources().getColor(R.color.blue_overlay));
                viewHolder.ll_layout.setBackgroundResource(R.color.white);
                viewHolder.view_splitter.setVisibility(View.GONE);
            } else {
                viewHolder.province_name.setTextColor(context.getResources().getColor(R.color.content_text));
                viewHolder.ll_layout.setBackgroundResource(R.color.ViewBackground);
                viewHolder.view_splitter.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public void selectPosition(int selectPostion) {
        this.selectPostion = selectPostion;
    }

    class ViewHolder {
        TextView province_name;
        LinearLayout ll_layout;
        View view_splitter;
    }
}
