package com.jxxx.tiyu_app.app;

import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class ConstValues {
    /**
     * 应用名称
     */
    public static String APPNAME_ENGLISH = "TiYuApp";
    /**
     * 修改版本信息
     * 1 中小学
     * 2 幼教
     */
    public static int BANBEN_TYPE = 1;

    /**
     * 文件夹父路径
     */
    public static final String FILE_ROOT_DIRECTORY = "/yzdg";

    /**sharedpreference 判断是否已登录字段*/
    public static final String USER_ID = "user_id";
    public static final String TEACHER_ID = "teacher_id";
    public static final String SCHOOL_ID = "school_id";
    public static final String TOKEN = "token";

    public static final String USER_NAME = "user_name";
    public static final String USER_PAS = "user_pas";

    /**
     * 服务器后台地址
     */
//    public static final String BASE_URL = "http://192.168.2.146:8080/";
    public static String BASE_URL = "";
    public static String PATH_LOGCAT = "";
//    public static  String BASE_URL = "http://mingzhou.nbqichen.com:8888/prod-api/";
//    public static final String BASE_URL = "http://kindergarten.nbqichen.com:8888/prod-api/";

    //默认连接超时时间
    public static final int DEFAULT_TIMEOUT = 130;
    public static final int PAGE_SIZE =10;
    public static final int BALL_NUM_MAX =32;//最大的光电球
    public static final int PLATE_NUM_MAX =32;//最大的光电地板

    /**
     * 离线操作的实体
     */
    public static SchoolCourseBean mSchoolCourseInfoBean = null;//大课程信息
    public static SchoolCourseBeanSmall mSchoolCourseInfoBeanSmall = null;//小课程信息
    public static SchoolClassBean mSchoolClassInfoBean = null;//班级信息
    public static List<SchoolStudentBean> mSchoolStudentInfoBean = null;//班级学生的信息
    public static String classSceduleCardId = null;//课程表的ID
}
