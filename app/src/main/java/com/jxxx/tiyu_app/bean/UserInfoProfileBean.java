package com.jxxx.tiyu_app.bean;

import java.util.List;

public class UserInfoProfileBean {

    /**
     * age : 0
     * classList : [{"classGroupList":[{"classId":0,"id":0,"studentIds":""}],"className":"","classSchedule":"","classTimes":"","createTime":"","grade":"","id":0,"queueNum":0,"queuePersonNum":0,"schoolId":0,"schoolName":"","studentNum":0,"teacherId":0,"teacherName":""}]
     * classNames :
     * createTime :
     * gender : 0
     * id : 0
     * mobile :
     * schoolId : 0
     * schoolName :
     * status : 0
     * teacherName :
     * userId : 0
     * userName :
     */

    private int age;
    private String avatar;
    private String classNames;
    private String createTime;
    private int gender;
    private int classNum;
    private int lecturesNum;
    private int studentNum;
    private int id;
    private String mobile;
    private int schoolId;
    private String schoolName;
    private int status;
    private String teacherName;
    private int userId;
    private String userName;
    private List<ClassListBean> classList;

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public void setLecturesNum(int lecturesNum) {
        this.lecturesNum = lecturesNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public int getClassNum() {
        return classNum;
    }

    public int getLecturesNum() {
        return lecturesNum;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClassNames() {
        return classNames;
    }

    public void setClassNames(String classNames) {
        this.classNames = classNames;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ClassListBean> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassListBean> classList) {
        this.classList = classList;
    }

    public static class ClassListBean {
        /**
         * classGroupList : [{"classId":0,"id":0,"studentIds":""}]
         * className :
         * classSchedule :
         * classTimes :
         * createTime :
         * grade :
         * id : 0
         * queueNum : 0
         * queuePersonNum : 0
         * schoolId : 0
         * schoolName :
         * studentNum : 0
         * teacherId : 0
         * teacherName :
         */

        private String className;
        private String classSchedule;
        private String classTimes;
        private String createTime;
        private String grade;
        private int id;
        private int queueNum;
        private int queuePersonNum;
        private int schoolId;
        private String schoolName;
        private int studentNum;
        private int teacherId;
        private String teacherName;
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

        public String getClassTimes() {
            return classTimes;
        }

        public void setClassTimes(String classTimes) {
            this.classTimes = classTimes;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQueueNum() {
            return queueNum;
        }

        public void setQueueNum(int queueNum) {
            this.queueNum = queueNum;
        }

        public int getQueuePersonNum() {
            return queuePersonNum;
        }

        public void setQueuePersonNum(int queuePersonNum) {
            this.queuePersonNum = queuePersonNum;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public int getStudentNum() {
            return studentNum;
        }

        public void setStudentNum(int studentNum) {
            this.studentNum = studentNum;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
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

            private int classId;
            private int id;
            private String studentIds;

            public int getClassId() {
                return classId;
            }

            public void setClassId(int classId) {
                this.classId = classId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
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
}

