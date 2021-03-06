package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.modelbean.TecInfoBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/4.
 * QQ邮箱:515849594@qq.com
 */
public interface ITeacherSearch {
    //获取老师列表信息
    void getTeacher(List<TecInfoBean> tecInfoBeanList);

//    //获取artSchool老师列表
//    void getArtTeacher(List<ArtTeacherBean> artTeacherBeanList);

    //上拉加载
    void loadTeacher(List<TecInfoBean> tecInfoBeanList);

    //上拉失败
    void OnLoadTeacherFailure(int result);

    void OnTeacherFailure(String result);

    //获取数据失败
    void OnFailure(String error_code);
}
