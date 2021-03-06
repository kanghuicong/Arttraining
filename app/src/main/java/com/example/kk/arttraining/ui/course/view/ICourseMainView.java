package com.example.kk.arttraining.ui.course.view;

import com.example.kk.arttraining.ui.course.bean.ArtTypeBean;
import com.example.kk.arttraining.ui.course.bean.CourseBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/12/17.
 * QQ邮箱:515849594@qq.com
 */
public interface ICourseMainView {

    void getArtType(List<ArtTypeBean> type_list);

    void getCourseList(List<CourseBean> course_list);

    void OnCourseFailure();

    void loadCourseList(List<CourseBean> course_list);

    void OnLoadCourseListFailure(int code);



}
