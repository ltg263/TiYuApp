package com.jxxx.tiyu_app.bean;

import java.util.List;

public class PostStudentResults {
    @Override
    public String toString() {
        return "PostStudentResults{" +
                "classDate='" + classDate + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", classId='" + classId + '\'' +
                ", classSceduleCardId='" + classSceduleCardId + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", smallCourseId='" + smallCourseId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", timeUse='" + timeUse + '\'' +
                ", times='" + times + '\'' +
                ", finishTimes='" + finishTimes + '\'' +
                ", timeoutTimes='" + timeoutTimes + '\'' +
                ", speed='" + speed + '\'' +
                ", lassGroupId='" + lassGroupId + '\'' +
                ", timeNodeList=" + timeNodeList +
                ", timeoutTimeNode=" + timeoutTimeNode +
                '}'+"\n";
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
    public String endTime;
    public String classId;
    public String classSceduleCardId;
    public String teacherId;

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

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
    private String studentName;//学生名称
    private String timeUse;//用时
    private String times;//	完成次数
    private String finishTimes;//打击次数
    private String timeoutTimes;//超时的次数
    private String speed;//	平均用时
    private String lassGroupId;//	队列的ID
    private List<TimeNodeBean> timeNodeList;//	按钮
    private List<TimeNodeBean> timeoutTimeNode;//	超时

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setTimeoutTimeNode(List<TimeNodeBean> timeoutTimeNode) {
        this.timeoutTimeNode = timeoutTimeNode;
    }

    public List<TimeNodeBean> getTimeoutTimeNode() {
        return timeoutTimeNode;
    }

    public static class TimeNodeBean {
//:[{"time":""#触发时间
//                ,"sortNum":"01"#球号
//                ,"color":02 #颜色,
//                "triggerMode":01  #触发方式,
//                "lightTime":05 #亮灯时长}]

        @Override
        public String toString() {
            return "TimeNodeBean{" +
                    "time=" + time +
                    ", sortNum='" + sortNum + '\'' +
                    ", color='" + color + '\'' +
                    ", triggerMode='" + triggerMode + '\'' +
                    ", lightTime='" + lightTime + '\'' +
                    '}';
        }

        private long time;//触发时间
        private String sortNum;//球号
        private String color;// 颜色,
        private String triggerMode;//触发方式
        private String lightTime;//亮灯时长

        public TimeNodeBean(long time, String sortNum, String color, String triggerMode, String lightTime) {
            this.time = time;
            this.sortNum = sortNum;
            this.color = color;
            this.triggerMode = triggerMode;
            this.lightTime = lightTime;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getSortNum() {
            return sortNum;
        }

        public void setSortNum(String sortNum) {
            this.sortNum = sortNum;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getTriggerMode() {
            return triggerMode;
        }

        public void setTriggerMode(String triggerMode) {
            this.triggerMode = triggerMode;
        }

        public String getLightTime() {
            return lightTime;
        }

        public void setLightTime(String lightTime) {
            this.lightTime = lightTime;
        }
    }

    public void setTimeNodeList(List<TimeNodeBean> timeNodeList) {
        this.timeNodeList = timeNodeList;
    }

    public List<TimeNodeBean> getTimeNodeList() {
        return timeNodeList;
    }

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

    public void setTimeoutTimes(String timeoutTimes) {
        this.timeoutTimes = timeoutTimes;
    }

    public String getTimeoutTimes() {
        return timeoutTimes;
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
