package com.jxxx.tiyu_app.bean;

import java.util.List;

public class PostStudentBean {

    /**
     * beginTime :
     * classDate :
     * classId : 0
     * classSceduleCardId : 0
     * courseId : 0
     * createBy :
     * createTime :
     * endTime :
     * id : 0
     * params : {}
     * schoolId : 0
     * searchValue :
     * smallCourseId : 0
     * studentResultsList : [{"classRecordId":0,"courseId":0,"createBy":"","createTime":"","delTf":0,"finishTimes":0,"id":0,"params":{},"searchValue":"","smallCourseId":0,"speed":0,"status":0,"studentId":0,"timeUse":0,"times":0,"updateBy":"","updateTime":""}]
     * teacherId : 0
     * updateBy :
     * updateTime :
     */

    private String beginTime;
    private String classDate;
    private String classId;
    private String classSceduleCardId;
    private String courseId;
    private String createBy;
    private String createTime;
    private String endTime;
    private String id;
    private String schoolId;
    private String searchValue;
    private String smallCourseId;
    private String teacherId;
    private String updateBy;
    private String updateTime;
    private List<PostStudentResults> studentResultsList;

    public void setClassSceduleCardId(String classSceduleCardId) {
        this.classSceduleCardId = classSceduleCardId;
    }

    public String getClassSceduleCardId() {
        return classSceduleCardId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSmallCourseId() {
        return smallCourseId;
    }

    public void setSmallCourseId(String smallCourseId) {
        this.smallCourseId = smallCourseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<PostStudentResults> getStudentResultsList() {
        return studentResultsList;
    }

    public void setStudentResultsList(List<PostStudentResults> studentResultsList) {
        this.studentResultsList = studentResultsList;
    }
}
