package com.jxxx.tiyu_app.bean;

public class SchoolClassRecordBean {

    /**
     * id : 1
     * classId : 10
     * classSceduleCardId : 1
     * classDate : 2022-08-31 00:00:00
     * teacherId : 5
     * smallCourseId : null
     * courseId : 1
     */

    private String id;
    private String classId;
    private String classSceduleCardId;
    private String classDate;
    private String teacherId;
    private String smallCourseId;
    private String courseId;

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
}
