package com.jxxx.tiyu_app.bean;

public class SchoolClassRecordBean {

    /**
     * id : 1
     * classId : 11
     * classSceduleCardId : 1
     * classDate : 2022-09-15 00:00:00
     * teacherId : 5
     * smallCourseId : null
     * courseId : 1
     * courseName : 模拟课程A
     * labels : 一年级 | 课外 | 新兴体育项目 | 平衡车
     * imgUrl : https://www.nbqichen.com/upload/20220803/202208031630575804.png
     */

    private String id;
    private String classId;
    private String classSceduleCardId;
    private String classDate;
    private String teacherId;
    private String smallCourseId;
    private String courseId;
    private String courseName;
    private String labels;
    private String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassSceduleCardId() {
        return classSceduleCardId;
    }

    public void setClassSceduleCardId(String classSceduleCardId) {
        this.classSceduleCardId = classSceduleCardId;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSmallCourseId() {
        return smallCourseId;
    }

    public void setSmallCourseId(String smallCourseId) {
        this.smallCourseId = smallCourseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
