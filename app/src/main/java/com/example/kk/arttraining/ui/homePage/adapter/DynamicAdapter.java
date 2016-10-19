package com.example.kk.arttraining.ui.homePage.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.AdvertisementEntity;
import com.example.kk.arttraining.bean.DynamicContentEntity;
import com.example.kk.arttraining.bean.TopicEntity;
import com.example.kk.arttraining.customview.MyListView;
import com.example.kk.arttraining.ui.homePage.function.homepage.LikeAnimatorSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicAdapter extends BaseAdapter {
    Context context;
    List<DynamicContentEntity> dynamicList;
    DynamicContentEntity dynamicMolder;
    List<AdvertisementEntity> advertisementList;
    List<TopicEntity> topicList;
    List<String> likeList = new ArrayList<String>();
    int like_position;
    TextView tv_like;


    public DynamicAdapter(Context context, List<DynamicContentEntity> dynamicList, List<AdvertisementEntity> advertisementList, List<TopicEntity> topicList){
        this.context = context;
        this.dynamicList = dynamicList;
        this.advertisementList = advertisementList;
        this.topicList = topicList;

        for (int i=0;i<dynamicList.size()+2;i++){
            likeList.add("0");
        }
    }
    @Override
    public int getCount() {
        Log.i("count", dynamicList.size() + 2 + "----");
        return dynamicList.size()+2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        int ret;
        if (position == 3 && advertisementList.size()!=0) {
            ret = 1;
        } else if(position == 4 && topicList.size()!=0){
            ret = 2;
        }else {
            ret = 3;
        }
        Log.i("ret", ret + "----");
        Log.i("position", position + "----");

        return ret;
    }

    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 1:
                convertView = View.inflate(context, R.layout.homepage_dynamic_advertisement_item, null);
                ImageView iv_advertisement = (ImageView) convertView.findViewById(R.id.iv_advertisement);

                break;
            case 2:
                Log.i("position2", position + "----");
                convertView = View.inflate(context, R.layout.homepage_dynamic_topic_list, null);
                View view_title = (View)convertView.findViewById(R.id.layout_dynamic_topic_title);
                TextView tv_title = (TextView) view_title.findViewById(R.id.tv_homepage_title);
                TextView tv_more = (TextView) view_title.findViewById(R.id.tv_homepage_more);
                tv_title.setText("话题");

                MyListView lv_topic = (MyListView) convertView.findViewById(R.id.lv_dynamic_topic);
                TopicAdapter topicAdapter = new TopicAdapter(context, topicList);
                lv_topic.setAdapter(topicAdapter);
                break;

            case 3:
                Log.i("position3", position + "----");
                final ViewHolder holder;

                if (convertView == null){
                    convertView = View.inflate(context, R.layout.homepage_dynamic_content_item, null);
                    holder = new ViewHolder();
                    holder.tv_like = (TextView)convertView.findViewById(R.id.tv_homepage_dynamic_like);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                likeList.add(position,"0");
                if (likeList.get(position).equals("1")) {
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.authority_heart);
                    holder.tv_like.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                } else if (likeList.get(position).equals("0")) {
                    Drawable drawable = context.getResources().getDrawable(R.mipmap.authority_honor);
                    holder.tv_like.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                }
                holder.tv_like.setOnClickListener(new LikeClick(position));

                break;
        }
        return convertView;
    }

    private class LikeClick implements View.OnClickListener {
        int position;
        public LikeClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            like_position = position;
            tv_like = (TextView)v.findViewById(R.id.tv_homepage_dynamic_like);
            if (likeList.get(position).equals("0")) {
                Log.i("isClick1", position + "----");
                LikeAnimatorSet.likeAnimatorSet(context,tv_like,R.mipmap.authority_heart);
                likeList.add(position,"1");
            }else if (likeList.get(position).equals("1")) {
                Log.i("isClick2", position + "----");
                Log.i("isList1", likeList.get(position) + "----");
                LikeAnimatorSet.nolikeAnimatorSet(context,tv_like,R.mipmap.authority_honor);
                likeList.add(position,"0");
                Log.i("isList2", likeList.get(position) + "----");
            }
        }
    }

    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_time;
        TextView tv_address;
        TextView tv_class;
        GridView gv_image;
        TextView tv_like;
        TextView tv_comment;
        TextView tv_browse;
        TextView tv_share;
    }
}
