package com.jxxx.tiyu_app.bean;

import java.util.List;

public class SchoolClassBean {

    /**
     * classGroupList : [{"classId":0,"id":0,"studentIds":""}]
     * className : 
     * classSchedule : 
     * grade : 
     * id : 0
     * schoolId : 0
     * studentNum : 0
     */

    private String className;
    private String classSchedule;
    private String grade;
    private String id;
    private String schoolId;
    private int studentNum;
    private int queueNum;
    private int queuePersonNum;

    public void setQueueNum(int queueNum) {
        this.queueNum = queueNum;
    }

    public void setQueuePersonNum(int queuePersonNum) {
        this.queuePersonNum = queuePersonNum;
    }

    public int getQueueNum() {
        return queueNum;
    }

    public int getQueuePersonNum() {
        return queuePersonNum;
    }

    private List<ClassGroupListBean> classGroupList;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(String classSchedule) {
        this.classSchedule = classSchedule;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public List<ClassGroupListBean> getClassGroupList() {
        return classGroupList;
    }

    public void setClassGroupList(List<ClassGroupListBean> classGroupList) {
        this.classGroupList = classGroupList;
    }

    public static class ClassGroupListBean {
        /**
         * classId : 0
         * id : 0
         * studentIds : 
         */

        private String classId;
        private String id;
        private String studentIds;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStudentIds() {
            return studentIds;
        }

        public void setStudentIds(String studentIds) {
            this.studentIds = studentIds;
        }
    }
}
