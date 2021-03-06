package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/1/18.
 * QQ邮箱:515849594@qq.com
 */
public class LiveFinishBean extends NoDataResponseBean {
    int owner;
    String owner_type;
    String name;
    String head_pic;
    int like_number;
    int follow_number;
    int chapter_number;
    String pre_time;
    String curr_time;
    List<LiveChapterBean> chapter_list;

    public String getCurr_time() {
        return curr_time;
    }

    public void setCurr_time(String curr_time) {
        this.curr_time = curr_time;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getOwner_type() {
        return owner_type;
    }

    public void setOwner_type(String owner_type) {
        this.owner_type = owner_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getLike_number() {
        return like_number;
    }

    public void setLike_number(int like_number) {
        this.like_number = like_number;
    }

    public int getFollow_number() {
        return follow_number;
    }

    public void setFollow_number(int follow_number) {
        this.follow_number = follow_number;
    }

    public int getChapter_number() {
        return chapter_number;
    }

    public void setChapter_number(int chapter_number) {
        this.chapter_number = chapter_number;
    }

    public String getPre_time() {
        return pre_time;
    }

    public void setPre_time(String pre_time) {
        this.pre_time = pre_time;
    }

    public List<LiveChapterBean> getChapter_list() {
        return chapter_list;
    }

    public void setChapter_list(List<LiveChapterBean> chapter_list) {
        this.chapter_list = chapter_list;
    }
}
