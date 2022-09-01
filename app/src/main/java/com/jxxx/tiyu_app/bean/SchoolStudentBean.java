package com.jxxx.tiyu_app.bean;

import java.util.ArrayList;
import java.util.List;

public class SchoolStudentBean {

    /**
     * classGroupList : [{"classId":0,"id":0,"studentIds":""}]
     * className : 
     * classSchedule : 
     * grade : 
     * id : 0
     * schoolId : 0
     * studentNum : 0
     */

    private String age;
    private String className;
    private String classSchedule;
    private String gender;
    private String id;
    private String schoolId;
    private String imgUrl;
    private String studentNo;
    private String studentName;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    private List<SchoolCourseBeanSmallActionInfoJson.StepsBean> steps;
    private int postWccs;
    private int postZjzs;
    private long postZys;
    private double postPjsd;

    public void setSteps(List<SchoolCourseBeanSmallActionInfoJson.StepsBean> steps) {
        this.steps = steps;
    }

    public List<SchoolCourseBeanSmallActionInfoJson.StepsBean> getSteps() {
        return steps;
    }

    public int getPostWccs() {
        return postWccs;
    }

    public void setPostWccs(int postWccs) {
        this.postWccs = postWccs;
    }

    public int getPostZjzs() {
        return postZjzs;
    }

    public void setPostZjzs(int postZjzs) {
        this.postZjzs = postZjzs;
    }

    public long getPostZys() {
        return postZys;
    }

    public void setPostZys(long postZys) {
        this.postZys = postZys;
    }

    public double getPostPjsd() {
        return postPjsd;
    }

    public void setPostPjsd(double postPjsd) {
        this.postPjsd = postPjsd;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
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
