package com.example.kk.arttraining.utils;

import android.os.Environment;

import com.example.kk.arttraining.bean.modelbean.UserLoginBean;

import java.util.ArrayList;

/**
 * 作者：wschenyongyin on 2016/11/6 10:24
 * 说明:配置类
 */
public class Config {


    public final static String testapi = "HTML/dynamictest.html";
    /* 接口相关-start */
    public final static String SERVER_IP = "192.168.188.148";
    public final static String SERVER_MH = ":";
    public final static String SYSTEM_PORT = "8088";
    public final static String SYSTEM_NAME = "/api/";

//    public final static String BASE_URL = "http://" + SERVER_IP + SERVER_MH + SYSTEM_PORT + SYSTEM_NAME;
//
    //API测试服务地址：
//    public final static String BASE_URL = "http://www.artforyou.cn:8088/api/";
    public final static String BASE_URL = "http://118.178.136.110/api/";

    //登陆接口
    public final static String URL_LOGIN = "login/login";//登陆
    public final static String URL_LOGIN_EXIT = "login/exit";//退出登录
    //用户注册接口
    public final static String URL_REGISTER_CREATE = "register/create";//用户注册
    public final static String URL_REGISTER_ISREG = "register/is_reg";//判断手机号码是否注册过
    public final static String URL_SMS_SEND = "sms/verification_code/send";//获取手机验证码
    public final static String URL_SMS_VERIFY = "sms/verification_code/verify";//检验验证码
    public final static String URL_INVITE_CODE_VERIFY = "invite_code/verify";//检验推荐码
    //用户找回密码
    public final static String URL_FORGOT_PWD = "forgot_pwd/create";//

    //用户相关接口
    public final static String URL_USERS_GET_INFO = "users/show";//获取用户信息
    public final static String URL_USERS_SET_INFO = "users/set_info";//设置个人资料
    public final static String URL_USERS_UPDATE_HEAD = "users/update_head";//根据用户ID修改用户头像
    public final static String URL_USERS_UPDATE_PWD = "users/update_pwd";//修改用户登录密码
    public final static String URL_USERS_UPDATE_MOIBLE = "users/change_mobile";//更换手机号
    public final static String URL_USERS_COUNT_NUM = "users/num";//更换手机号
    public final static String URL_MESSAGE_LIST = "message/push/list";//获取消息列表
    //测评接口
    public final static String URL_ASSESSMENTS_LIST = "assessments/list";//根据用户id获取测评列表
    public final static String URL_ASSESSMENTS_SHOW = "assessments/show";//根据id获取测评详情
    //小组接口
    public final static String URL_GROUP_LIST = "group/list";//获取小组列表
    public final static String URL_GROUP_LIST_MY = "group/list_my";//获取我的小组列表
    public final static String URL_GROUP_SHOW = "group/show";//获取小组详情
    public final static String URL_GROUP_USERS = "group/users";//获取小组成员列表
    public final static String URL_GROUP_CREATE = "group/create";//创建小组
    public final static String URL_GROUP_JOIN = "group/join";//加入小组
    public final static String URL_GROUP_EXIT = "group/exit";//退出小组
    //动态接口
    public final static String URL_STATUSES_PUBLIC_TIMELINE_WORK = "homepage/public_timeline/work";//获取首页最新作品列表
    public final static String URL_STATUSES_PUBLIC_TIMELINE_BBS = "statuses/public_timeline/bbs";//获取最新动态、帖子列表
    public final static String URL_STATUSES_USER_TIMELINE_BBS = "statuses/user_timeline/bbs";//获取用户发布的帖子
    public final static String URL_STATUSES_SHOW_BBS = "statuses/show/bbs";//获取帖子详情
    public final static String URL_STATUSES_REPORT_BBS = "statuses/report/bbs";//转发帖子
    public final static String URL_STATUSES_PUBLISH_BBS = "statuses/publish/bbs";//发布帖子
    public final static String URL_STATUSES_DELETE = "statuses/delete/bbs";//删除动态
    public final static String URL_STATUSES_TIMELINE_GROUP = "statuses/public_timeline/g_stus";//获取小组最新动态（精品动态）列表
    public final static String URL_STATUSES_USER_TIMELINE_GROUP = "statuses/user_timeline/g_stus";//获取小组用户发布的动态
    public final static String URL_STATUSES_SHOW_GROUP = "statuses/show/g_stus";//获取小组动态详情
    public final static String URL_STATUSES_REPORT_GROUP = "statuses/report/g_stus";//转发小组动态
    public final static String URL_STATUSES_PUBLISH_GROUP = "statuses/publish/g_stus";//发布小组动态
    public final static String URL_STATUSES_USER_TIMELINE_WORK = "statuses/user_timeline/work";//获取用户的作品列表
    public final static String URL_STATUSES_SHOW_WORK = "statuses/show/work";//获取作品详情
    public final static String URL_STATUSES_SHOW_MY_WORK = "statuses/show_my/work";//获取作品详情
    public final static String URL_STATUSES_SHOW_MY_BBS = "statuses/show_my/bbs";//查看我评论过的帖子
    public final static String URL_STATUSES_TECCOMMENT_READ = "teccomment/read";//收看/听老师点评信息时调用的接口

    //评论接口
    public final static String URL_COMMENTS_LIST_BBS = "comments/list/bbs";//获取动态的评论列表
    public final static String URL_COMMENTS_CREATE_BBS = "comments_v2/create/bbs";//发表一条评论
    public final static String URL_COMMENTS_DELETE_BBS = "comments/delete/bbs";//删除一条评论
    public final static String URL_COMMENTS_REPLY_BBS = "comments_v2/reply/bbs";//回复一条评论

    public final static String URL_COMMENTS_LIST_GROUP = "comments/list/g_stus";//发表一条评论
    public final static String URL_COMMENTS_CREATE_GROUP = "comments_v2/create/g_stus";//发表一条评论
    public final static String URL_COMMENTS_DELETE_GROUP = "comments/delete/g_stus";//删除一条评论
    public final static String URL_COMMENTS_REPLY_GROUP = "comments/reply_v2/g_stus";//回复一条评论

    public final static String URL_COMMENTS_LIST_WORK = "comments/list/work";//获取作品的评论列表
    public final static String URL_COMMENTS_CREATE_WORK = "comments_v2/create/work";//发表一条评论
    public final static String URL_COMMENTS_DELETE_WORK = "comments/delete/work";//删除一条评论
    public final static String URL_COMMENTS_REPLY_WORK = "comments_v2/reply/work";//回复一条评论
    //名师点评接口
    public final static String URL_TECH_COMMENTS_LIST = "tech_comments/list";//获取动态的名师点评列表
    public final static String URL_TECH_COMMENTS_CREATE = "tech_comments/list";//发表点评
    public final static String URL_TECH_COMMENTS_REPLY = "tech_comments/list";//发表追问
    //搜索接口
    public final static String URL_SEARCH_PUBLIC = "search/public";//首页搜索
    public final static String URL_SEARCH_ORG = "search/org";//机构搜索
    public final static String URL_SEARCH_TEC = "search/tec";//名师搜索
    public final static String URL_SEARCH_STATUSES = "search/statuses";//动态搜索
    public final static String URL_SEARCH_GROUP = "search/group";//小组搜索

    //
    public final static String URL_SEARCH_CITY = "common/get_city";//获取城市列表

    public final static String URL_SEARCH_CITY_BYPROVINCE = "common/get_city/by_province";//按省来查找 我的页面
    public final static String URL_COMMON_PROVINCE = "common/get_province";//获取省份列表
    //提醒接口
    public final static String URL_REMIND_LIST = "remind/list";//获取提醒列表
    public final static String URL_REMIND_SHOW = "remind/show";//提醒详情
    public final static String URL_REMIND_UNREAD_COUNT = "remind/unread_cont";//未读提醒数量
    //收藏接口
    public final static String URL_FAVORITES_LIST = "favorites/list";//获取用户收藏列表
    public final static String URL_FAVORITES_SHOW = "favorites/show";//获取收藏详情
    public final static String URL_FAVORITES_CREATE = "favorites/create";//添加收藏
    public final static String URL_FAVORITES_DELETE = "favorites/delete";//删除收藏
    //点赞接口
    public final static String URL_LIKE_LIST_BBS = "like/list/bbs";//获取帖子点赞列表
    public final static String URL_LIKE_LIST_PIC_BBS = "like/list/pic/bbs";//获取帖子点赞用户头像列表
    public final static String URL_LIKE_CREATE_BBS = "like/create/bbs";//添加帖子点赞
    public final static String URL_LIKE_LIST_GROUP = "like/list/g_stus";//获取小组点赞列表
    public final static String URL_LIKE_LIST_PIC_GROUP = "like/list/pic/g_stus";//获取小组点赞用户头像列表
    public final static String URL_LIKE_CREATE_GROUP = "like/create/g_stus";//添加小组点赞
    public final static String URL_LIKE_LIST_WORK = "like/list/work";//获取作品点赞列表
    public final static String URL_LIKE_LIST_PIC_WORK = "like/list/pic/work";//获取作品点赞用户头像列表
    public final static String URL_LIKE_CREATE_WORK = "like/create/work";//添加作品点赞
    public final static String URL_LIKE_DELETE = "like/delete";//删除点赞
    //支付接口
    public final static String URL_PAY_REWORK = "pay/mobile/do_pay";//获取支付信息
    public final static String URL_PAY_REWORK_SYSTEM = "pay/mobile/payment/system";//获取支付信息

    //机构
    public final static String URL_ORG_LIST = "org/list";//获取机构列表
    public final static String URL_ORG_SHOW = "org/show";//获取机构详情
    //艺术家/名师接口
    public final static String URL_TECHER_LIST = "techer/list";//获取名师列表
    public final static String URL_TECHER_LIST_INDEX = "techer/list/index";//首页测评权威
    public final static String URL_TECHER_SHOW = "techer/show";//获取名师详情


    //活动
    public final static String URL_ACTIVITYIES_LIST = "activities/list";//获取活动列表
    public final static String URL_ACTIVITYIES_SHOW = "activities/show";//获取活动详情
    //专题
    public final static String URL_TOPICS_LIST = "topics/list";//获取专题列表
    public final static String URL_TOPICS_SHOW = "topics/show";//获取专题详情
    //帮助
    public final static String URL_HELP_LIST = "help/list";//获取帮助列表
    public final static String URL_HELP_SHOW = "help/show";//获取帮助详情
    //建议反馈
    public final static String URL_RECOMMEND_BY_ME = "recommend/by_me";//获取我的反馈列表
    public final static String URL_RECOMMEND_SHOW = "recommend/show";//反馈详情
    public final static String URL_RECOMMEND_CREATE = "recommend/create";//发送反馈
    //艺培头条
    public final static String URL_INFORMATION_LIST = "information/list";//获取头条列表
    public final static String URL_INFORMATION_SHOW = "information/show";//获取头条详情
    //资讯
    public final static String URL_INFO_LIST = "information_v2/list";//获取头条列表

    //广告
    public final static String URL_ADVERTISING_LIST = "advertising/list";//获取广告列表
    public final static String URL_ADVERTISING_SHOW = "advertising/show";//获取广告详情
    //院校
    public final static String URL_INSTITUTIONS_CONDITIONS = "institutions/conditions";////获取院校左边列表
    public final static String URL_INSTITUTIONS_LIST = "institutions/list";//获取院校列表
    public final static String URL_INSTITUTIONS_SHOW = "institutions/show";//获取院校详情

    //订单
    public final static String URL_ORDERS_LIST = "orders/list_my";//获取订单列表
    public final static String URL_ORDERS_SHOW = "orders/show";//获取订单详情
    public final static String URL_ORDERS_CREATE = "orders/create/assessment";//下订单
    public final static String URL_ORDERS_UPDATE = "orders_v3/update/assessment";//更新订单状态
    public final static String URL_ORDERS_BUY = "orders_v3/buy/assessment/other";

    public final static String URL_ORDERS_CANCEL = "orders/cancel";//取消订单
    public final static String URL_ORDERS_REMAINING_TIME = "orders/remaining/time";//获取订单支付剩余时间
    //轮播接口
    public final static String URL_BANNER_LIST = "banner/list";//获取轮播列表
    public final static String URL_BANNER_SHOW = "banner/show";//根据广告ID获取轮播详情信息
    //专业接口
    public final static String URL_MAJOR_LIST = "major/list";
    public final static String URL_MAJOR_LIST_LEVEL_ONE = "major/list/level_one";
    //关注
    public final static String URL_FOLLOW_CREATE = "follow/create";
    public final static String URL_FOLLOW_FANS_LIST = "follow/fans/list";
    public final static String URL_FOLLOW_FOLLOW_LIST = "follow/follow/list";

    //七牛云上传
    public final static String URL_UPLOAD_QINIU_GETTOKEN = "upload/get_token/qiniu";//从服务器获取token
    public final static String URL_UPLOAD_QINIU_PUTURL = "upload/createqiniu";//将上传的文件连接返回给服务器

    //获取优惠券
    public final static String URL_COUPONS_LIST = "coupons/list";
    public final static String URL_EXCHANGE_COUPONS = "invite_code/verify";//兑换优惠券

    //课程
    public final static String BASE_URL_COURSE = "http://www.iartschool.com:10080/iartschool/";//课程请求base url
    public final static String URL_COURSE_ART_TYPE_LIST = "art_type/list_type";//艺术类别
    public final static String URL_COURSE_TEACHER_LIST = "teacher/list_info";//教师列表
    public final static String URL_COURSE_TEACHER_CONTENT = "teacher/get_info";//教师详情
    public final static String URL_COURSE_COURSE_INFO = "course/get_info";//课程详情
    public final static String URL_COURSE_COURSE_LIST = "course/list_info";//课程列表
    public final static String URL_COURSE_CHAPTER_LIST = "chapter/list_info";//课程章节列表
    public final static String URL_LESSON_CHAPTER_LIST = "les_source/list_info";//课堂列表
    public final static String URL_LESSON_SOURCE_PLAY_LIST = "les_source/play";//视频列表列表
    //消息列表
    public final static String URL_PUSH_NEW_LIST = "message/push/list";//新的消息列表
    public final static String URL_PUSH_ALL_LIST = "message/list/more";//全部消息列表
    public final static String URL_MESSAGE_RED_ONE = "message/read/one";//标记已读
    public final static String URL_MESSAGE_RED_ALL = "message/list/all";//全部标为已读
    /* 接口相关-start */
    public final static String URL_ALIPAY_ASYNC = BASE_URL + "";//支付宝支付服务器异步通知页面接口
    public final static String URL_WECHAT_PAY_ASYNC = BASE_URL + "";//微信支付服务器异步通知页面接口
    //获取身份列表
    public final static String URL_IDENTITY_LIST = "identity/list";//获取身份列表
    //检查更新
    public final static String URL_UPDATE_APP = "version/update";//获取身份列表
    //token验证
    public final static String TOKEN_VERIFY = "token/verify";//token验证

    /**
     * v2版本接口
     */

    //第三方登录
    public final static String API_UMLOGIN = "login_v2/third/login";//登陆
    public final static String API_REGISTER_CREATE = "login_v2/register/create";//注册用户接口
    public final static String API_VERIFY_PHONE = "login_v2/register/is_reg";//注册用户接口
    public final static String API_LOGIN_V2 = "login_v2/login";//登陆

    //直播接口
    public final static String API_LIVE_HOME = "open/class/live/home";//直播首页
//    public final static String API_LIVE_LIST = "open/class/live/list";//直播封面列表
    public final static String API_LIVE_LIST = "open/class/live/list_v2";//直播封面列表
    public final static String API_LIVE_LIST_HISTORY = "open/class/live/history";//直播回放列表
    public final static String API_LIVE_LIST_HISTORY_TYPE = "open/class/watch/history";//直播回放状态
    public final static String API_LIVE_ENTER = "open/class/enter/live";//直播状态
    public final static String API_WAIT_LIVE = "open/class/wait/live";//直播未开始
    public final static String API_FINISH_LIVE = "open/class/finish/live";//直播未开始


    public final static String API_LIVE_JOIN_ROOM = "live/room/join";//进入房间看直播
    public final static String API_LIVE_EXIT_ROOM = "live/room/exit";//退出房间
    public final static String API_LIVE_CREATE_LIKE = "live/create/like";//对直播点赞

    public final static String API_CLASS_LIVE_JOIN = "open/class/lvie/join";//加入直播课时
    public final static String API_CLASS_LIVE_EXIT = "open/class/lvie/exit";//退出直播课时
    public final static String API_CLASS_LIVE_PLAY_URL = "open/class/lvie/exit";//退出直播课时
    public final static String API_CLASS_LIVE_BEING = "open/class/being/live";//获取正在直播信息
    public final static String API_LIVE_CREATE_COMMENT = "open/class/online/comment";//评论
    public final static String API_LIVE_COMMENT_LIST = "open/class/comment/list";//房间评论信息列表
    public final static String API_LIVE_MEMBER_LIST = "open/class/member/list";//主播端房间成员信息列表
    public final static String API_LIVE_COURSELIST = "open/class/timetable/list";//课程列表
    public final static String API_LIVE_TALK_STATUS = "open/class/live/talk";//课程列表
    public final static String API_LIVE_GIFT_LIST = "open/class/gift/list";//礼物列表
    public final static String API_LIVE_GIVE_GIFT = "open/class/give/gift";//正送礼物

    public final static String API_BUY_CHAPTER = "open/class/buy/chapter";//购买直播课程


    public final static String API_EXCEPTION_RECEIVE = "exception/receive";//捕获异常

    //支付系统
    public final static String API_CHAPTER_CLOUND = "payment/buy/chapter/clound";//云币支付
    public final static String API_CHAPTER_OTHER = "payment/buy/chapter/other";//其他支付
    public final static String API_CHAPTER_UPDATE = "payment/buy/chapter/update";//更新
    public final static String API_CHAPTER_ORDER_LIST = "payment/chapter/order/list";//我的课程list


    //积分接口
    public final static String API_SCORE_QUERY = "score/query";//查询用户当前积分
    public final static String API_SCORE_CONSUME = "score/live/consume";//直播礼物消费积分接口
    public final static String API_SCORE_DETAIL = "score/detail/query";//查询积分详情列表接口

    //云币接口
    public final static String API_RECHARGE_ICLOUD_LIST = "wallet/cloud/tranform/list";//云币和钱价值转换列表信息的接口
    public final static String API_RECHARGE_ICLOUD_CREATE = "wallet/cloud/create/order";//创建充值订单
    public final static String API_RECHARFE_PAY = "pay/mobile/recharge";//调用微信支付
    public final static String API_CLOUD_QUERY = "wallet/cloud/query";//查询用户当前云币
    public final static String API_CLOUD_CONSUME = "wallet/cloud/live/consume";//直播礼物消费云币接口
    public final static String API_CLOUD_DETAIL = "wallet/cloud/detail/query";//查询云币详情列表接口
    public final static String API_CLOUD_UPDATE = "wallet/cloud/update/order";//更新充值订单状态
    public final static String API_CLOUD_HELP = "wallet/cloud/help/recharge";//查询账号信息

    //报考
    public final static String API_ENTRANCE_ADMISSION = "entrance/admission/score/list";//新增查看历年来各批次艺术类高校(专业)录取最低控制分数线接口
    public final static String API_ENTRANCE_QUALIFY = "entrance/qualify/line/list";//新增艺术类术科统考本科资格线接口
    public final static String API_ENTRANCE_PROVINCE = "entrance/province/list";//选择报考生源地列表
    public final static String API_ENTRANCE_CATEGORY = "entrance/category/list";//选择专业类别列表
    public final static String API_ENTRANCE_COLLEGE = "entrance/college/list";//输入统考术科成绩和文化分,推荐相应的院校列表接口


    /* 全局变量-start */
    public static final String BASE_LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    //拍照保存的路径
    public static final String IMAGE_SAVE_PATH = BASE_LOCAL_PATH + "/image/";
    public static String ACCESS_TOKEN = null;
    public static String User_Id = null;
    public static String USER_NAME = null;
    public static int UID = 0;
    //用户类型
    public static String USER_TYPE = "stu";
    public static String CITY = "";
    public static String USER_TITLE = "";
    //七牛云token
    public static String QINIUYUN_WORKS_TOKEN = null;
    public static String QINIUYUN_PIC_TOKEN = null;
    public static String QINIUYUN_BBS_TOKEN = null;
    public static String QINIUYUN_COURSE_TOKEN = null;
    public static String QINIUYUN_GOURP_TOKEN = null;
    public static String QINIUYUN_ADVERT_TOKEN = null;
    public static String QINIUYUN_USER_HEADER_TOKEN = null;
    //用于保存用户信息
    public static UserLoginBean userBean = null;
    public static int PermissionsState = 0;//权限状态
    public static String REQUEST_FAILURE = "请求网络失败";
    public static String test_video = "http://flv2.bn.netease.com/tvmrepo/2016/4/G/O/EBKQOA8GO/SD/EBKQOA8GO-mobile.mp4";
    public static String SCHOOL_PIC = "http://g.hiphotos.baidu.com/baike/w%3D268/sign=e4b93743f5d3572c66e29bdab2126352/f7246b600c33874405904fd6560fd9f9d62aa0c7.jpg";
    public final static String USER_HEADER_Url = "http://awb.img.xmtbang.com/img/uploadnew/201510/23/8bd0ba8fb90d4d0d99aefef22d8b4034.jpg";
    public final static String TEST_COURSE = "http://118.178.136.110/course/";
    /* 全局变量-end */
    //选择图片
    public static ArrayList<String> ShowImageList = null;
    public static ArrayList<String> ProductionImageList = null;

    //错误代码
    public final static String System_Error = "10001";//系统错误
    public final static String Service_Unavailable = "10002";//服务暂停
    public final static String Job_Expired = "10010";//任务超时
    public final static String Connection_NO_CONTENT = "20007";//连接失败
    public final static String Connection_Failure = "400";//连接失败
    public final static String TOKEN_INVALID = "20028";//token失效  重新登陆

    //错误提示语

    public final static String Connection_ERROR_TOAST = "网络连接失败";

    //khc
    public static int HeadlinesPosition = 0;
    public static PlayAudioUtil playAudioUtil = null;
    public static String ArtName = "18979756586";
    public static int ListenPosition = -1;
    public static String ArtForYou = "https://www.artforyou.cn/";
    public static boolean liveType = false;
    public static boolean historyType = false;

    public static String order_number = null;
    public static String liveName = null;


    //支付
    public static String order_num = null;
    public static String order_att_path = null;
    public static String att_type = null;

    public static String rechargeNum = null;//云币充值订单号
    public static String rechargeId = null;//云币充币订单id

    public static String WxCallBackType = null;//微信支付完成后回调 判断充值类型（充值云币/测评）

    //用于识别用户点击退出登陆后 点击返回按钮
    public static String EXIT_FLAG = null;
    public static String IART_USER_NAME = "13155822445";


}