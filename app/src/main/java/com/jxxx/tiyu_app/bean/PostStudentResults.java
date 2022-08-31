package com.jxxx.tiyu_app.bean;

public class PostStudentResults {

    @Override
    public String toString() {
        return "PostStudentResults{" +
                "courseId='" + courseId + '\'' +
                ", smallCourseId='" + smallCourseId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", timeUse='" + timeUse + '\'' +
                ", times='" + times + '\'' +
                ", finishTimes='" + finishTimes + '\'' +
                ", speed='" + speed + '\'' +
                '}';
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
