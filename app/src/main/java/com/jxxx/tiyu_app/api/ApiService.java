package com.jxxx.tiyu_app.api;


import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.AuthLoginBean;
import com.jxxx.tiyu_app.bean.CourseTypeListAllBean;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;
import com.jxxx.tiyu_app.bean.PostStudentBean;
import com.jxxx.tiyu_app.bean.PostStudentResults;
import com.jxxx.tiyu_app.bean.SceduleCourseBean;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.SchoolClassRecordBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
import com.jxxx.tiyu_app.bean.SchoolStudentDetailBean;
import com.jxxx.tiyu_app.bean.UserInfoProfileBean;
import com.jxxx.tiyu_app.bean.VersionResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 登录方法
     * @return
     */
    @POST("auth/login")
    Observable<Result<AuthLoginBean>> postAuthLogin(@Body RequestBody body);

    /**
     * 批量添加学生成绩
     * @return
     */
    @POST("school/classRecord")
    Observable<Result> postResultsBatchAdd(@Body PostStudentBean mPostStudentBean);
    /**
     * 批量添加学生成绩
     * @return
     */
    @POST("school/classRecord/addList")
    Observable<Result> postResultsBatchAdds(@Body List<PostStudentBean> mPostStudentBeans);

    /**
     * 新增上课记录
     * @return
     */
    @POST("school/studentResults/batchAdd")
    Observable<Result> postSchoolClassRecord(@Body PostStudentResults mPostStudentResult);

    /**
     * 复制队列
     * @return
     */
    @POST("school/smallCourse/copyQueue")
    Observable<Result<SchoolCourseBean.CourseSectionVoListBean>> postSmallCourseCopyQueue(@Query("id") String id, @Query("num") String num);
//    Observable<Result> postSmallCourseCopyQueue(@Body SchoolCourseBean.CourseSectionVoListBean mCourseSectionVoListBean);

    /**
     * 登出方法
     * @return
     */
    @DELETE("auth/logout")
    Observable<Result> deleteAuthLogout();

    /**
     * 根据当前时间查询课程
     * @return
     */
    @GET("school/course/queryCourse")
    Observable<Result<SceduleCourseBean>> getSchoolCourseQueryCourse();

    /**
     * 查询备课列表
     * @return
     */
    @GET("school/sceduleCourse/list")
    Observable<Result<List<SceduleCourseBean>>> getSceduleCourseList(@Query("teacherId") String teacherId,
                                                                     @Query("schoolId") String schoolId,
                                                                     @Query("courseName") String courseName,
                                                                     @Query("ageRange") String ageRange,
                                                                     @Query("contentType") String contentType,
                                                                     @Query("category") String category,
                                                                     @Query("theme") String theme,
                                                                     @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 查询备课详情
     * @return
     */
    @GET("school/sceduleCourse/{id}")
    Observable<Result<SceduleCourseBean>> getSchoolSceduleCourseDetail(@Path("id") String id);

    /**
     * 查询大课程列表
     * @return
     */
    @GET("school/course/list")
    Observable<Result<List<SchoolCourseBean>>> getSchoolCourseList(@Query("teacherId") String teacherId,
                                                                   @Query("schoolId") String schoolId,
                                                                   @Query("courseName") String courseName,
                                                                   @Query("ageRange") String ageRange,
                                                                   @Query("contentType") String contentType,
                                                                   @Query("category") String category,
                                                                   @Query("theme") String theme,
                                                                   @Query("pageNum") int pageNum,@Query("pageSize") int pageSize);

    /**
     * 查询班级列表
     * @return
     */
    @GET("school/class/list")
    Observable<Result<List<SchoolClassBean>>> getSchoolClassList(@Query("teacherId") String teacherId,@Query("schoolId") String schoolId,@Query("pageNum") int pageNum,@Query("pageSize") int pageSize);

    /**
     * 查询班级列表
     * @return
     */
    @GET("school/class/{id}")
    Observable<Result<SchoolClassBean>> getSchoolClassId(@Path("id") String id);

    /**
     * 查询学生列表
     * @return
     */
    @GET("school/student/list")
    Observable<Result<List<SchoolStudentBean>>> getSchoolStudentList(@Query("classId") String classId,@Query("pageNum") Integer pageNum,@Query("pageSize") Integer pageSize);

    /**
     * 获取学生详细信息
     * @return
     */
    @GET("school/student/{id}")
    Observable<Result<SchoolStudentDetailBean>> getSchoolStudentDetail(@Path("id") String id);

    /**
     * 查询大课程详情
     * @return
     */
    @GET("school/course/{id}")
    Observable<Result<SchoolCourseBean>> getSchoolCourseDetail(@Path("id") String id);


    /**
     * 查询上课记录列表
     * @return
     */
    @GET("school/classRecord/list")
    Observable<Result<List<SchoolClassRecordBean>>> getSchoolClassRecordList(@Query("teacherId") String teacherId, @Query("classId") String classId);

    /**
     * 获取上课记录详细信息
     * @return
     */
    @GET("school/classRecord/{id}")
    Observable<Result<SchoolClassRecordBean>> getSchoolClassRecord(@Path("id") String id);

    /**
     * 查询小课程详情
     * @return
     */
    @GET("school/smallCourse/{id}")
    Observable<Result<SchoolCourseBeanSmall>> getSchoolSmallCourseDetail(@Path("id") String id);


    /**
     * 查询小课程列表
     * @return
     */
    @GET("school/smallCourse/list")
    Observable<Result<List<SchoolCourseBeanSmall>>> getSchoolCourseListSmall(@Query("teacherId") String teacherId,
                                                                             @Query("schoolId") String schoolId,
                                                                             @Query("courseId") String courseId,
                                                                             @Query("courseName") String courseName,
                                                                             @Query("ageRange") String ageRange,
                                                                             @Query("contentType") String contentType,
                                                                             @Query("processType") String processType,
                                                                             @Query("trainType") String trainType,
                                                                             @Query("pageNum") int pageNum,@Query("pageSize") int pageSize);

    /**
     * 当前登陆教师
     */
    @GET("school/teacher/current")
    Observable<Result<UserInfoProfileBean>> getSchoolTeacherCurrent();


    /**
     * 用户设备 // 1安卓 2IOS 2pad
     */
    @GET("system/version/getLastVersion")
    Observable<Result<VersionResponse>> getLast(@Query("clientType") String clientType);


    /**
     * 分类条件
     */
    @GET("system/dict/data/type/{dictType}")
    Observable<Result<List<DictDataTypeBean>>> getDictDataType(@Path("dictType") String dictType);

    /**
     * 大类别：
     */
    @GET("school/courseType/listAll")
    Observable<Result<List<CourseTypeListAllBean>>> getCourseTypeListAll();

    /**
     * 上传文件
     *
     * @return
     */
    @Multipart
    @POST("api/v1/files")
    Observable<Result> submitFiles(@Part MultipartBody.Part[] file, @PartMap Map<String, RequestBody> map);

}
