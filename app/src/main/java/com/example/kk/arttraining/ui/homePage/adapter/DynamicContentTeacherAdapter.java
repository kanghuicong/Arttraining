package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecCommentsBean;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;
import com.example.kk.arttraining.bean.parsebean.TecCommentsList;
import com.example.kk.arttraining.custom.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacherAdapter extends BaseAdapter {
    List<ParseCommentDetail> parseCommentDetailList = new ArrayList<ParseCommentDetail>();
    ParseCommentDetail parseCommentDetail = new ParseCommentDetail();
    List<TecCommentsBean> tec_comments;
    TecInfoBean tecInfoBean;
    Activity activity;
    int count;

    public DynamicContentTeacherAdapter(Activity activity,List<ParseCommentDetail> parseCommentDetailList) {
        this.parseCommentDetailList = parseCommentDetailList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return parseCommentDetailList.size();
    }

    @Override
    public ParseCommentDetail getItem(int position) {
        return parseCommentDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        parseCommentDetail = parseCommentDetailList.get(position);
        tecInfoBean = parseCommentDetail.getTec();
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.homepage_dynamic_teacher_item, null);
            holder = new ViewHolder();
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_dynamic_teacher_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_time);
            holder.tv_college = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_school);
            holder.tv_professor = (TextView) convertView.findViewById(R.id.tv_dynamic_teacher_professor);
            holder.lv_teacher_comment = (MyListView) convertView.findViewById(R.id.lv_dynamic_content_teacher_comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(tecInfoBean.getName());
        holder.tv_time.setText(tecInfoBean.getTime());
        holder.tv_college.setText(tecInfoBean.getSchool());
        holder.tv_professor.setText(tecInfoBean.getIdentity());

        tec_comments = parseCommentDetail.getTec_comments();
//        DynamicContentTeacherCommentAdapter dynamicContentTeacherCommentAdapter = new DynamicContentTeacherCommentAdapter(activity);
//        holder.lv_teacher_comment.setAdapter(dynamicContentTeacherCommentAdapter);

        return convertView;
    }

    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_time;
        TextView tv_college;
        TextView tv_professor;
        MyListView lv_teacher_comment;
    }
}
