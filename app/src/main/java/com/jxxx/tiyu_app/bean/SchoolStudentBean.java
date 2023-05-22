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

    private int age;
    private String classId;

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassId() {
        return classId;
    }

    private String className;
    private String classSchedule;
    private int gender;
    private String id;
    private String schoolId;
    private String imgUrl;
    private String studentNo;
    private String studentName;
    private List<Byte> allQiuNo;
    private int currentStepNo;//当前执行的步骤

    public void setCurrentStepNo(int currentStepNo) {
        this.currentStepNo = currentStepNo;
    }

    public int getCurrentStepNo() {
        return currentStepNo;
    }

    public void setAllQiuNo(List<Byte> allQiuNo) {
        this.allQiuNo = allQiuNo;
    }

    public List<Byte> getAllQiuNo() {
        return allQiuNo;
    }

    private List<byte[]> lists;

    public void setLists(List<byte[]> lists) {
        this.lists = lists;
    }

    public List<byte[]> getLists() {
        return lists;
    }

    private boolean isAskForLeave;

    public void setAskForLeave(boolean askForLeave) {
        isAskForLeave = askForLeave;
    }

    public boolean isAskForLeave() {
        return isAskForLeave;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    private List<SchoolCourseBeanSmallActionInfoJson.StepsBean> steps;
    private int postDqbz;//当前步骤循环的次数
    private int postWccs;//完成次数
    private int postZjzs;//总击中数
    private int postZcss;//总超时数
    private int postZfks;//总反馈数
    private long postZys;//总用时
    private double postPjsd;//平局速度
    private List<PostStudentResults.TimeNodeBean> timeNode;//总动击中数据
    private List<PostStudentResults.TimeNodeBean> timeoutTimeNode;//超时数据

    public void setPostDqbz(int postDqbz) {
        this.postDqbz = postDqbz;
    }

    public int getPostDqbz() {
        return postDqbz;
    }

    public void setPostZfks(int postZfks) {
        this.postZfks = postZfks;
    }

    public void setPostZcss(int postZcss) {
        this.postZcss = postZcss;
    }

    public int getPostZcss() {
        return postZcss;
    }

    public int getPostZfks() {
        return postZfks;
    }

    public void setTimeoutTimeNode(List<PostStudentResults.TimeNodeBean> timeoutTimeNode) {
        this.timeoutTimeNode = timeoutTimeNode;
    }

    public List<PostStudentResults.TimeNodeBean> getTimeoutTimeNode() {
        return timeoutTimeNode;
    }
    public void addTimeoutTimeNode(long time, String sortNum, String color, String triggerMode, String lightTime) {
        timeoutTimeNode.add(new PostStudentResults.TimeNodeBean(time,sortNum,color,triggerMode,lightTime));
    }


    public void setTimeNode(List<PostStudentResults.TimeNodeBean> timeNode) {
        this.timeNode = timeNode;
    }

    public void addTimeNode(long time, String sortNum, String color, String triggerMode, String lightTime) {
        timeNode.add(new PostStudentResults.TimeNodeBean(time,sortNum,color,triggerMode,lightTime));
    }

    public List<PostStudentResults.TimeNodeBean> getTimeNode() {
        return timeNode;
    }


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

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
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
