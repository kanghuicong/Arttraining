package com.example.kk.arttraining.ui.homePage.activity;

import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.bumptech.glide.Glide;
import com.example.kk.arttraining.MyApplication;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.BannerBean;
import com.example.kk.arttraining.bean.HeadNews;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.custom.view.MyGridView;
import com.example.kk.arttraining.custom.view.RewriteBanner;
import com.example.kk.arttraining.ui.homePage.adapter.AuthorityAdapter;
import com.example.kk.arttraining.ui.discover.adapter.DynamicAdapter;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicFailureAdapter;
import com.example.kk.arttraining.ui.homePage.function.homepage.MusicTouch;
import com.example.kk.arttraining.ui.homePage.function.shuffling.ADBean;
import com.example.kk.arttraining.ui.homePage.function.shuffling.TuTu;
import com.example.kk.arttraining.ui.homePage.function.homepage.AuthorityData;
import com.example.kk.arttraining.ui.homePage.function.homepage.WorkData;
import com.example.kk.arttraining.ui.homePage.function.homepage.FindTitle;
import com.example.kk.arttraining.ui.homePage.function.homepage.Headlines;
import com.example.kk.arttraining.ui.homePage.function.homepage.ProvinceDialog;
import com.example.kk.arttraining.ui.homePage.function.homepage.ShufflingData;
import com.example.kk.arttraining.ui.homePage.function.refresh.PullToRefreshLayout;
import com.example.kk.arttraining.ui.homePage.prot.IAuthority;
import com.example.kk.arttraining.ui.homePage.prot.IHomePageMain;
import com.example.kk.arttraining.ui.homePage.prot.IShuffling;

import com.example.kk.arttraining.ui.webview.WebActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.NetUtils;
import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.PreferencesUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.mingle.widget.ShapeLoadingDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

//import com.jaeger.library.StatusBarUtil;

/**
 * Created by kanghuicong on 2016/10/17.
 * QQ邮箱:515849594@qq.com
 */
public class HomePageMain extends Fragment implements IHomePageMain, IShuffling, IAuthority, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, DynamicAdapter.MusicCallBack {

    View view_institution, view_teacher, view_test, view_performance, view_live;
    @InjectView(R.id.tv_homepage_address)
    TextView tvHomepageAddress;
    //    @InjectView(R.id.lv_authority)
    MyGridView lvAuthority;
    @InjectView(R.id.lv_homepage_dynamic)
    ListView lvHomepageDynamic;

    int dynamic_num;
    AuthorityData authorityData;
    ExecutorService mThreadService;
    @InjectView(R.id.refresh_view)
    PullToRefreshLayout refreshView;
    private LocationService locationService;
    List<Map<String, Object>> DynamicList = new ArrayList<Map<String, Object>>();
    Activity activity;
    View view_homepage, view_header;
    TextView default_authority;
    Headlines headlines;
    WorkData dynamicData;
    ShufflingData shufflingData;
    private String error_code;
    private static final int BAIDU_READ_PHONE_STATE = 100;
    DynamicAdapter dynamicadapter;
    int dynamicPosition = 0;
    List<ADBean> listADbeans;
    private TuTu tu;
    private RewriteBanner ad_viewPage;
    private TextView tv_msg;
    private LinearLayout ll_dian;
    boolean Flag = false;
    boolean AuthorityFlag = false;
    int authority_self = 1;
    private ShapeLoadingDialog shapeLoadingDialog;
    int refreshResult = PullToRefreshLayout.FAIL;
    PlayAudioUtil playAudioUtil = null;
    int MusicPosition = -5;
    AnimatorSet MusicArtSet = null;
    AnimationDrawable MusicAnim = null;
    int shuffling[] = {R.mipmap.shullfing_1, R.mipmap.shullfing_2, R.mipmap.shullfing_3};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();

        if (view_homepage == null) {
            view_homepage = View.inflate(activity, R.layout.homepage_main, null);
            ButterKnife.inject(this, view_homepage);
            view_header = View.inflate(activity, R.layout.homepage_listview_header, null);
            FindHeaderId();
            lvHomepageDynamic.addHeaderView(view_header);
            shapeLoadingDialog = new ShapeLoadingDialog(activity);
            shapeLoadingDialog.show();
            shapeLoadingDialog.setLoadingText("加载中...");

            refreshView.setOnRefreshListener(this);

            mThreadService = Executors.newFixedThreadPool(1);
            locationThread();
            shufflingData = new ShufflingData(this);
            shufflingData.getShufflingData();//轮播

            headlines = new Headlines(this);
            headlines.getHeadNews("");//头条

            dynamicData = new WorkData(this);
            dynamicData.getDynamicData();//动态

            initAuthority();//测评权威
            initTheme();//四个Theme
        }

        ViewGroup parent = (ViewGroup) view_homepage.getParent();
        if (parent != null) {
            parent.removeView(view_homepage);
        }
        return view_homepage;
    }

    private void FindHeaderId() {
//        lvAuthority = (HorizontalListView) view_header.findViewById(R.id.lv_authority);
//        vpImg = (InnerView) view_header.findViewById(R.id.vp_img);
        ad_viewPage = (RewriteBanner) view_header.findViewById(R.id.ad_viewPage);

        ad_viewPage.setImageLoader(new GlideImageLoader());
        ad_viewPage.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.Tablet);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        ad_viewPage.isAutoPlay(true);
        //设置轮播时间为3秒
        ad_viewPage.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        ad_viewPage.setIndicatorGravity(BannerConfig.CENTER);
//        tv_msg = (TextView) view_header.findViewById(R.id.tv_msg);
//        ll_dian = (LinearLayout) view_header.findViewById(R.id.ll_dian);
        lvAuthority = (MyGridView) view_header.findViewById(R.id.lv_authority);

        default_authority = (TextView) view_header.findViewById(R.id.tv_default_authority);
        LinearLayout institution = (LinearLayout) view_header.findViewById(R.id.layout_theme_institution);
        LinearLayout teacher = (LinearLayout) view_header.findViewById(R.id.layout_theme_teacher);
        LinearLayout test = (LinearLayout) view_header.findViewById(R.id.layout_theme_test);
        LinearLayout performance = (LinearLayout) view_header.findViewById(R.id.layout_theme_performance);
        LinearLayout live = (LinearLayout) view_header.findViewById(R.id.layout_theme_live);

        institution.setOnClickListener(this);
        teacher.setOnClickListener(this);
        test.setOnClickListener(this);
        performance.setOnClickListener(this);
        live.setOnClickListener(this);
    }

    @OnClick({R.id.ll_homepage_search, R.id.tv_homepage_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage_search:
                Intent intent = new Intent(activity, SearchMain.class);
                intent.putExtra("type", "homepage");
                activity.startActivity(intent);
                break;
            case R.id.tv_homepage_address:
                Intent intentHome = new Intent(activity, ChooseProvinceMain.class);
                intentHome.putExtra("fromType", "home_city");
                startActivity(intentHome);
                break;
            case R.id.layout_theme_institution:
                UIUtil.IntentActivity(activity, new ThemeInstitution());
                break;
            case R.id.layout_theme_teacher:
//                UIUtil.IntentActivity(activity, new ThemeTeacher());
                UIUtil.IntentActivity(activity, new ThemeTeacherAll());

                break;
            case R.id.layout_theme_test:
//                UIUtil.IntentActivity(activity, new ThemeTest());
                UIUtil.IntentActivity(activity, new ThemeSchool());
                break;
            case R.id.layout_theme_performance:
//                UIUtil.IntentActivity(activity, new ThemePerformance());
//                Intent intent1 = new Intent(activity, CourseWebView.class);
//                intent1.putExtra("url", Config.TEST_COURSE);
//                startActivity(intent1);
                startActivity(new Intent(activity, ThemeApplyExamineActivity.class));
                break;
            case R.id.layout_theme_live:
                UIUtil.ToastshowShort(activity, "功能开发中，敬请期待");
                break;
        }
    }

    //四个Theme
    private void initTheme() {
        view_institution = FindTitle.findView(view_homepage, R.id.layout_theme_institution);
        TextView tv_institution = FindTitle.findText(view_institution);
        FindTitle.initImage(activity, R.mipmap.view_institution, tv_institution, "机构");

        view_teacher = FindTitle.findView(view_homepage, R.id.layout_theme_teacher);
        TextView tv_teacher = FindTitle.findText(view_teacher);
        FindTitle.initImage(activity, R.mipmap.view_teacher, tv_teacher, "老师");

        view_test = FindTitle.findView(view_homepage, R.id.layout_theme_test);
        TextView tv_test = FindTitle.findText(view_test);
        FindTitle.initImage(activity, R.mipmap.view_performance, tv_test, "院校");

        view_performance = FindTitle.findView(view_homepage, R.id.layout_theme_performance);
        TextView tv_performance = FindTitle.findText(view_performance);
        FindTitle.initImage(activity, R.mipmap.view_test, tv_performance, "报考");

        view_live = FindTitle.findView(view_homepage, R.id.layout_theme_live);
        TextView tv_live = FindTitle.findText(view_live);
        FindTitle.initImage(activity, R.mipmap.view_live, tv_live, "直播");
    }

    //名师指路
    private void initAuthority() {
        FindTitle mFindTitle = new FindTitle(this);
        mFindTitle.findTitle(FindTitle.findView(view_homepage, R.id.layout_authority_title), activity, "名师指路", R.mipmap.add_more, "authority");//为测评权威添加标题
        authorityData = new AuthorityData(this);
        authorityData.getAuthorityData(authority_self);//获取测评权威数据
    }

    // 定位结果回调
    private BDLocationListener mListener = new BDLocationListener() {

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                try {
                    tvHomepageAddress.setText(Config.CITY + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (Config.CITY.equals("")) {
                    PreferencesUtils.put(activity, "province", location.getCity().substring(0, location.getCity().length() - 1));
                    if (location.getCity().substring(location.getCity().length() - 1, location.getCity().length()).equals("市")) {
                        Config.CITY = location.getCity().substring(0, location.getCity().length() - 1);
                    } else {
                        Config.CITY = location.getCity();
                    }
                    tvHomepageAddress.setText(Config.CITY+"");
                } else {
                    if (!location.getCity().equals("")) {
                        if (location.getCity().substring(location.getCity().length() - 1, location.getCity().length()).equals("市")) {
                            if (!Config.CITY.equals(location.getCity().substring(0, location.getCity().length() - 1))) {
                                ProvinceDialog.getProvinceDialog(activity, location.getCity().substring(0, location.getCity().length() - 1), tvHomepageAddress);
                            }
                        } else {
                            ProvinceDialog.getProvinceDialog(activity, location.getCity(), tvHomepageAddress);
                        }
                    } else {
                        tvHomepageAddress.setText(Config.CITY);
                    }
                }
                locationService.unregisterListener(mListener); //注销掉监听
                locationService.stop(); //停止定位服务
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                UIUtil.ToastshowShort(activity, "网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                UIUtil.ToastshowShort(activity, "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            } else {
                tvHomepageAddress.setText(Config.CITY);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    Toast.makeText(activity, "获取到权限，作相应处理", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "没有获取到权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    public void locationThread() {
        mThreadService.execute(new Runnable() {
            @Override
            public void run() {
                locationService = ((MyApplication) activity.getApplication()).locationService;
                locationService.registerListener(mListener);//注册监听
                int type = activity.getIntent().getIntExtra("from", 0);
                if (type == 0) {
                    locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                } else if (type == 1) {
                    locationService.setLocationOption(locationService.getOption());
                    locationService.unregisterListener(mListener); //注销掉监听
                    locationService.stop(); //停止定位服务
                }
                locationService.start();// 定位SDK
            }
        });
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        Headlines.stopEffect();
        MusicTouch.stopMusicAll(playAudioUtil, MusicArtSet, MusicAnim);
    }


    @Override
    public void onResume() {
        super.onResume();
        UIUtil.showLog("tvHomepageAddress", Config.CITY);
        tvHomepageAddress.setText(Config.CITY);
        if (Config.HeadlinesPosition == 1) {
            Headlines.startEffect();
            UIUtil.showLog("startEffect","-------");
        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.reset(this);
//    }

    //获取动态数据
    @Override
    public void getDynamicListData(List<Map<String, Object>> mapList) {

        Flag = true;
        if (dynamicPosition == 0) {
            DynamicList.addAll(mapList);
            dynamicadapter = new DynamicAdapter(activity, DynamicList, this, "homepage");
            dynamic_num = mapList.size();
            try {
                lvHomepageDynamic.setAdapter(dynamicadapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dynamicPosition++;
        } else {
            DynamicList.clear();
            DynamicList.addAll(mapList);
            dynamicadapter.changeCount(DynamicList.size());
            dynamicadapter.notifyDataSetChanged();
            dynamic_num = mapList.size();
        }

        lvHomepageDynamic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        UIUtil.showLog("触摸移动时的操作", lvHomepageDynamic.getFirstVisiblePosition() + "----==" + MusicPosition);
                        if (MusicPosition != -5) {
                            if (lvHomepageDynamic.getFirstVisiblePosition() - 2 >= MusicPosition || lvHomepageDynamic.getLastVisiblePosition() <= MusicPosition) {
                                MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);
                            }
                        }
                        break;
                }
                return false;
            }
        });
        shapeLoadingDialog.dismiss();
    }

    //获取动态数据失败
    @Override
    public void OnDynamicFailure(String result) {
        shapeLoadingDialog.dismiss();
        UIUtil.ToastshowShort(activity, result);

        if (DynamicList == null || DynamicList.size() == 0) {
            DynamicFailureAdapter dynamicFailureAdapter = new DynamicFailureAdapter(activity);
            try {
                lvHomepageDynamic.setAdapter(dynamicFailureAdapter);
                Flag = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //头条数据
    @Override
    public void getHeadNews(List<HeadNews> headNewsList) {
        UIUtil.showLog("HeadNews", headNewsList.size() + "----");
        Headlines.initHeadlines(view_homepage, activity, headNewsList, "yes");//头条动画
        if (Config.HeadlinesPosition != 2) {
            Headlines.startEffect();
        }
        Config.HeadlinesPosition = 1;
    }

    //获取头条数据失败
    @Override
    public void OnHeadNewsFailure(String error_code) {
        List<HeadNews> headNewsList = new ArrayList<HeadNews>();
        Headlines.initHeadlines(view_homepage, activity, headNewsList, "no");//头条获取失败
        if (Config.HeadlinesPosition == 0) {
            Headlines.startEffect();
        }
        Config.HeadlinesPosition = 1;
    }

    //测评权威
    @Override
    public void getTeacherData(final List<TecInfoBean> tecInfoBeanList) {
        AuthorityFlag = true;
        default_authority.setVisibility(View.GONE);
        lvAuthority.setVisibility(View.VISIBLE);
        AuthorityAdapter authorityAdapter = new AuthorityAdapter(activity, tecInfoBeanList);
        lvAuthority.setAdapter(authorityAdapter);
    }

    //刷新测评权威数据
    @Override
    public void getAuthorityResult() {
        authority_self++;
        authorityData.getAuthorityData(authority_self);
    }

    //获取测评权威失败
    @Override
    public void OnTeacherFailure() {
        if (!AuthorityFlag) {
            default_authority.setVisibility(View.VISIBLE);
            lvAuthority.setVisibility(View.GONE);
        }
    }

    //获取轮播数据成功
    @Override
    public void getShuffling(final List<BannerBean> list) {
        UIUtil.showLog("getShuffling---->", list.toString());
        List<String> listPic = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            listPic.add(list.get(i).getPic());
        }
        ad_viewPage.setImages(listPic);
        ad_viewPage.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                if (list.get(position).getUrl() != null && !list.get(position).getUrl().equals("")) {
                    Intent intent = new Intent(activity, WebActivity.class);
                    intent.putExtra("url", list.get(position).getUrl());
                    intent.putExtra("title", list.get(position).getTitle());
                    activity.startActivity(intent);
                }
            }
        });
        ad_viewPage.start();
    }

    //获取轮播失败
    @Override
    public void OnShufflingFailure(String failure) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < shuffling.length; i++) {
            list.add(shuffling[i]);
        }
        ad_viewPage.setImages(list);
        ad_viewPage.start();

    }

    //连接网络失败
    @Override
    public void OnFailure(String error_code) {
        this.error_code = error_code;
        UIUtil.showLog("homeMain_error_code", error_code);
        mHandler.sendEmptyMessage(0);
        shapeLoadingDialog.dismiss();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = "";
            switch (error_code) {
                case Config.Connection_Failure:
                    message = getResources().getString(R.string.connection_failure);
                    break;
                case "20007":
                    message = "数据内容为空";
                    break;
            }
            UIUtil.ToastshowShort(activity, message);
        }
    };


    //下拉刷新
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);

        Config.HeadlinesPosition = 2;
        headlines.getHeadNews("");//头条

        authority_self = 1;
        authorityData.getAuthorityData(authority_self);//测评

        dynamicData.getDynamicData();//动态

        if (!NetUtils.isConnected(activity)) {
            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
        } else {
            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        }

    }

    //上拉加载
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        MusicTouch.stopMusicAnimator(playAudioUtil, MusicArtSet, MusicAnim);

        if (Flag) {
            if (DynamicList.get(DynamicList.size() - 1).get("type").equals("work") || DynamicList.get(DynamicList.size() - 1).get("type").equals("status")) {
                dynamicData.loadDynamicData(dynamicadapter.getSelfId());
            } else {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        refreshView.loadmoreFinish(PullToRefreshLayout.EMPTY);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        } else {
//            UIUtil.ToastshowShort(activity, "网络连接失败！");
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    refreshView.loadmoreFinish(refreshResult);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    //上拉加载数据
    @Override
    public void loadDynamicListData(List<Map<String, Object>> mapList) {
        UIUtil.showLog("getSelfId", mapList.size() + "---------");
        DynamicList.addAll(mapList);
        dynamic_num = dynamic_num + mapList.size();
        dynamicadapter.changeCount(dynamic_num);
        dynamicadapter.notifyDataSetChanged();
        refreshView.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    //上拉加载数据失败
    @Override
    public void OnLoadDynamicFailure(int result) {
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
    public void backPlayAudio(PlayAudioUtil playAudioUtil, AnimatorSet MusicArtSet, AnimationDrawable MusicAnim, int MusicPosition) {
        this.playAudioUtil = playAudioUtil;
        this.MusicPosition = MusicPosition;
        this.MusicArtSet = MusicArtSet;
        this.MusicAnim = MusicAnim;
        UIUtil.showLog("触摸移动时的操作position", MusicPosition + "----");
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).error(R.mipmap.shullfing_1).into(imageView);
        }
    }

}
