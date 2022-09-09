package com.jxxx.tiyu_app.api;


import com.jxxx.tiyu_app.base.Result;
import com.jxxx.tiyu_app.bean.AuthLoginBean;
import com.jxxx.tiyu_app.bean.DictDataTypeBean;
import com.jxxx.tiyu_app.bean.PostStudentResults;
import com.jxxx.tiyu_app.bean.SchoolClassBean;
import com.jxxx.tiyu_app.bean.SchoolClassRecordBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBean;
import com.jxxx.tiyu_app.bean.SchoolCourseBeanSmall;
import com.jxxx.tiyu_app.bean.SchoolStudentBean;
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
     * 登录方法
     * @return
     */
    @POST("school/studentResults/batchAdd")
    Observable<Result> postResultsBatchAdd(@Body List<PostStudentResults> mPostStudentResults);

    /**
     * 新增上课记录
     * @return
     */
    @POST("school/classRecord")
    Observable<Result> postSchoolClassRecord(@Body PostStudentResults mPostStudentResult);

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
    Observable<Result<SchoolCourseBean>> getSchoolCourseQueryCourse();


    /**
     * 查询大课程列表
     * @return
     */
    @GET("school/course/list")
    Observable<Result<List<SchoolCourseBean>>> getSchoolCourseList(@Query("courseName") String courseName,
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
    Observable<Result<SchoolStudentBean>> getSchoolStudentDetail(@Path("id") String id);

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
    Observable<Result<List<SchoolCourseBeanSmall>>> getSchoolCourseListSmall(@Query("courseName") String courseName,
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
     * 用户设备
     */
    @GET("api/v1/version/getLast")
    Observable<Result<VersionResponse>> getLast(@Query("clientType") String clientType);


    /**
     * 用户设备
     */
    @GET("system/dict/data/type/{dictType}")
    Observable<Result<List<DictDataTypeBean>>> getDictDataType(@Path("dictType") String dictType);

    /**
     * 上传文件
     *
     * @return
     */
    @Multipart
    @POST("api/v1/files")
    Observable<Result> submitFiles(@Part MultipartBody.Part[] file, @PartMap Map<String, RequestBody> map);

}
