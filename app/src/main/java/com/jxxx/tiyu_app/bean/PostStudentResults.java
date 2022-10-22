package com.jxxx.tiyu_app.bean;

public class PostStudentResults {
    @Override
    public String toString() {
        return "PostStudentResults{" +
                "classDate='" + classDate + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", classId='" + classId + '\'' +
                ", classSceduleCardId='" + classSceduleCardId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", smallCourseId='" + smallCourseId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", timeUse='" + timeUse + '\'' +
                ", times='" + times + '\'' +
                ", finishTimes='" + finishTimes + '\'' +
                ", speed='" + speed + '\'' +
                ", lassGroupId='" + lassGroupId + '\'' +
                '}';
    }

    public PostStudentResults() {

    }

    public PostStudentResults(String classDate, String classId, String classSceduleCardId, String teacherId, String courseId, String smallCourseId) {
        this.classDate = classDate;
        this.classId = classId;
        this.classSceduleCardId = classSceduleCardId;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.smallCourseId = smallCourseId;
    }

    public String classDate;
    public String beginTime;
    public String classId;
    public String classSceduleCardId;
    public String teacherId;

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setClassSceduleCardId(String classSceduleCardId) {
        this.classSceduleCardId = classSceduleCardId;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassDate() {
        return classDate;
    }

    public String getClassId() {
        return classId;
    }

    public String getClassSceduleCardId() {
        return classSceduleCardId;
    }

    public String getTeacherId() {
        return teacherId;
    }


    /**
     * courseId : 0
     * createBy :
     * createTime :
     * delTf : 0
     * id : 0
     * params : {}
     * searchValue :
     * smallCourseId : 0
     * speed : 0
     * status : 0
     * studentId : 0
     * timeUse : 0
     * times : 0
     * updateBy :
     * updateTime :
     */

    private String courseId;//	大课程id
    private String smallCourseId;//	小课程id
    private String studentId;//学生id
    private String timeUse;//用时
    private String times;//	完成次数
    private String finishTimes;//打击次数
    private String speed;//	平均用时
    private String lassGroupId;//	队列的ID

    public void setLassGroupId(String lassGroupId) {
        this.lassGroupId = lassGroupId;
    }

    public String getLassGroupId() {
        return lassGroupId;
    }

    public void setFinishTimes(String finishTimes) {
        this.finishTimes = finishTimes;
    }

    public String getFinishTimes() {
        return finishTimes;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }



    public String getSmallCourseId() {
        return smallCourseId;
    }

    public void setSmallCourseId(String smallCourseId) {
        this.smallCourseId = smallCourseId;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTimeUse() {
        return timeUse;
    }

    public void setTimeUse(String timeUse) {
        this.timeUse = timeUse;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
    public static class ParamsBean {
    }
}
