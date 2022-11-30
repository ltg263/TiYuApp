package com.jxxx.tiyu_app.bean;

import java.util.List;

public class SceduleCourseBean {

    /**
     * id : 11
     * classScheduleCardId : 2
     * courseId : 0
     * orderDate : 2022-10-18 00:00:00
     * sectionList : null
     * classScheduleCard : {"id":2,"week":2,"classId":37,"teacherId":4,"sort":3,"beginTime":"09:45","endTime":"10:30","className":"501","teacherName":"翁","studentCount":null,"queueNum":12,"queuePersonNum":1,"grade":"5"}
     * teacherId : 4
     * course : null
     * teacherName : 翁
     */

    private String id;
    private String classScheduleCardId;
    private String courseId;
    private String orderDate;
    private List<SchoolCourseBean.CourseSectionVoListBean> sectionList;
    private ClassScheduleCardBean classScheduleCard;
    private String teacherId;
    private SchoolCourseBean course;
    private String teacherName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassScheduleCardId() {
        return classScheduleCardId;
    }

    public void setClassScheduleCardId(String classScheduleCardId) {
        this.classScheduleCardId = classScheduleCardId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<SchoolCourseBean.CourseSectionVoListBean> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SchoolCourseBean.CourseSectionVoListBean> sectionList) {
        this.sectionList = sectionList;
    }

    public ClassScheduleCardBean getClassScheduleCard() {
        return classScheduleCard;
    }

    public void setClassScheduleCard(ClassScheduleCardBean classScheduleCard) {
        this.classScheduleCard = classScheduleCard;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public SchoolCourseBean getCourse() {
        return course;
    }

    public void setCourse(SchoolCourseBean course) {
        this.course = course;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public static class ClassScheduleCardBean {
        /**
         * id : 2
         * week : 2
         * classId : 37
         * teacherId : 4
         * sort : 3
         * beginTime : 09:45
         * endTime : 10:30
         * className : 501
         * teacherName : 翁
         * studentCount : null
         * queueNum : 12
         * queuePersonNum : 1
         * grade : 5
         */

        private String id;
        private String week;
        private String classId;
        private String teacherId;
        private String sort;
        private String beginTime;
        private String endTime;
        private String className;
        private String teacherName;
        private Object studentCount;
        private String queueNum;
        private String queuePersonNum;
        private String grade;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public Object getStudentCount() {
            return studentCount;
        }

        public void setStudentCount(Object studentCount) {
            this.studentCount = studentCount;
        }

        public String getQueueNum() {
            return queueNum;
        }

        public void setQueueNum(String queueNum) {
            this.queueNum = queueNum;
        }

        public String getQueuePersonNum() {
            return queuePersonNum;
        }

        public void setQueuePersonNum(String queuePersonNum) {
            this.queuePersonNum = queuePersonNum;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }
}
