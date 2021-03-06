package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.function.chatting.FaceConversionUtil;
import com.example.kk.arttraining.ui.me.view.PersonalHomePageActivity;
import com.example.kk.arttraining.utils.DateUtils;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/30.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentCommentAdapter extends BaseAdapter {
    List<CommentsBean> commentList = new ArrayList<CommentsBean>();
    CommentsBean commentsBean = new CommentsBean();
    ViewHolder holder = null;
    Activity activity;
    int count;

    public DynamicContentCommentAdapter(Activity activity, List<CommentsBean> commentList) {
        this.commentList = commentList;
        this.activity = activity;
        count = commentList.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CommentsBean getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        commentsBean = commentList.get(position);
        UIUtil.showLog("commentsBean",position+"---"+commentsBean);

        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.homepage_dynamic_content_comment, null);
            holder = new ViewHolder();
            holder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_dynamic_comment_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.iv_dynamic_comment_name);
            holder.tv_time = (TextView) convertView.findViewById(R.id.iv_dynamic_comment_time);
            holder.tv_content = (TextView) convertView.findViewById(R.id.iv_dynamic_comment_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(activity).load(commentsBean.getUser_pic()).transform(new GlideCircleTransform(activity)).error(R.mipmap.default_user_header).into(holder.iv_header);
        holder.tv_name.setText(commentsBean.getName());
        holder.tv_time.setText(DateUtils.getDate(commentsBean.getTime()));
        if (("reply").equals(commentsBean.getComm_type())) {
            SpannableString spannableString = new SpannableString("回复  "+commentsBean.getReply().getName()+"：");
            int spannableLength = spannableString.length();
            holder.tv_content.setText(FaceConversionUtil.getInstace().getExpressionStringReply(activity, "回复  "+commentsBean.getReply().getName()+"："+ commentsBean.getContent(),spannableLength,commentsBean.getReply().getUser_id()));
            holder.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        }else {
            holder.tv_content.setText(FaceConversionUtil.getInstace().getExpressionString(activity,commentsBean.getContent()));
        }
        holder.iv_header.setOnClickListener(new HeaderClick(commentsBean.getUser_id()));
        return convertView;
    }

    private class HeaderClick implements View.OnClickListener {
        int id;
        public HeaderClick(int user_id) {
            id = user_id;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, PersonalHomePageActivity.class);
            intent.putExtra("uid", id);
            activity.startActivity(intent);
        }
    }

    class ViewHolder {
        LinearLayout ll_comment;
        ImageView iv_header;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
    }

    public void changeCount(int changeCount){
        count=changeCount;
    }

    public int getSelf() {
        return commentList.get(commentList.size()-1).getComment_id();
    }

}
