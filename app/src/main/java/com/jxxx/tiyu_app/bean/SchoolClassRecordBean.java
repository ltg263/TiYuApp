package com.jxxx.tiyu_app.bean;

import java.util.List;

public class SchoolClassRecordBean {

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
    private String sortStr;
    private String traineesNum;
    private String absenteesNum;
    private String overtimeNum;
    private List<StudentResultsListBean> studentResultsList;

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

    public String getSortStr() {
        return sortStr;
    }

    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }

    public String getTraineesNum() {
        return traineesNum;
    }

    public void setTraineesNum(String traineesNum) {
        this.traineesNum = traineesNum;
    }

    public String getAbsenteesNum() {
        return absenteesNum;
    }

    public void setAbsenteesNum(String absenteesNum) {
        this.absenteesNum = absenteesNum;
    }

    public String getOvertimeNum() {
        return overtimeNum;
    }

    public void setOvertimeNum(String overtimeNum) {
        this.overtimeNum = overtimeNum;
    }

    public List<StudentResultsListBean> getStudentResultsList() {
        return studentResultsList;
    }

    public void setStudentResultsList(List<StudentResultsListBean> studentResultsList) {
        this.studentResultsList = studentResultsList;
    }

    public static class StudentResultsListBean {
        /**
         * id : 1
         * studentId : 70
         * courseId : null
         * smallCourseId : 70
         * timeUse : 6.00
         * times : 1
         * createTime : 2022-11-01 10:27:43
         * speed : 3.00
         * finishTimes : 2
         * studentName : 韦1
         * courseName : null
         */

        private String id;
        private String studentId;
        private String courseId;
        private String smallCourseId;
        private long timeUse;
        private String times;
        private String createTime;
        private String speed;
        private String finishTimes;
        private String studentName;
        private String courseName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
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

        public long getTimeUse() {
            return timeUse;
        }

        public void setTimeUse(long timeUse) {
            this.timeUse = timeUse;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getFinishTimes() {
            return finishTimes;
        }

        public void setFinishTimes(String finishTimes) {
            this.finishTimes = finishTimes;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }
}
