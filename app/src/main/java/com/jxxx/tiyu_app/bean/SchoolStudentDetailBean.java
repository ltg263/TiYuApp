package com.jxxx.tiyu_app.bean;

import java.util.ArrayList;
import java.util.List;

public class SchoolStudentDetailBean {

    /**
     * id : 240
     * schoolId : 8
     * classId : 57
     * studentName : 施瑾睿
     * studentNo : shxj04216
     * gender : 1
     * age : 8
     * birthday : 2014-05-19 00:00:00
     * height : 143.00
     * weight : 63.00
     * imgUrl : null
     * parentPhone : 13156484534
     * studentClassRecords : [{"id":4729,"studentId":240,"classRecordId":271,"status":1,"classRecord":null,"studentResultsList":[{"id":1936,"studentId":240,"courseId":null,"smallCourseId":115,"timeUse":"22.00","times":1,"createTime":"2022-11-21 12:03:21","speed":"11.00","finishTimes":2,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}],"courseName":"课程二","imgUrl":"http://mingzhou.nbqichen.com:8888/assets/icon_course.png","labels":"三年级 | 课内 | 主教材 | 心肺耐力"},{"id":4765,"studentId":240,"classRecordId":272,"status":1,"classRecord":null,"studentResultsList":[{"id":1954,"studentId":240,"courseId":null,"smallCourseId":115,"timeUse":"0.00","times":0,"createTime":"2022-11-21 12:30:03","speed":"0.00","finishTimes":0,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}],"courseName":"课程二","imgUrl":"http://mingzhou.nbqichen.com:8888/assets/icon_course.png","labels":"三年级 | 课内 | 主教材 | 心肺耐力"},{"id":4801,"studentId":240,"classRecordId":273,"status":1,"classRecord":null,"studentResultsList":[{"id":1972,"studentId":240,"courseId":null,"smallCourseId":109,"timeUse":"44.00","times":1,"createTime":"2022-11-21 14:19:54","speed":"8.00","finishTimes":5,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}],"courseName":"课程一","imgUrl":"http://mingzhou.nbqichen.com:8888/assets/icon_course.png","labels":"三年级 | 课内 | 搭配教材 | 心肺耐力"},{"id":4837,"studentId":240,"classRecordId":274,"status":1,"classRecord":null,"studentResultsList":[{"id":1990,"studentId":240,"courseId":null,"smallCourseId":115,"timeUse":"29.00","times":1,"createTime":"2022-11-21 14:48:33","speed":"14.00","finishTimes":2,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}],"courseName":"课程二","imgUrl":"http://mingzhou.nbqichen.com:8888/assets/icon_course.png","labels":"三年级 | 课内 | 主教材 | 心肺耐力"},{"id":4873,"studentId":240,"classRecordId":275,"status":1,"classRecord":null,"studentResultsList":[{"id":2008,"studentId":240,"courseId":null,"smallCourseId":115,"timeUse":"21.00","times":1,"createTime":"2022-11-21 14:49:57","speed":"10.00","finishTimes":2,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}],"courseName":"课程二","imgUrl":"http://mingzhou.nbqichen.com:8888/assets/icon_course.png","labels":"三年级 | 课内 | 主教材 | 心肺耐力"},{"id":4909,"studentId":240,"classRecordId":276,"status":1,"classRecord":null,"studentResultsList":[{"id":2026,"studentId":240,"courseId":null,"smallCourseId":115,"timeUse":"22.00","times":1,"createTime":"2022-11-21 18:19:22","speed":"11.00","finishTimes":2,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}],"courseName":"课程二","imgUrl":"http://mingzhou.nbqichen.com:8888/assets/icon_course.png","labels":"三年级 | 课内 | 主教材 | 心肺耐力"},{"id":4997,"studentId":240,"classRecordId":279,"status":1,"classRecord":null,"studentResultsList":[{"id":2080,"studentId":240,"courseId":null,"smallCourseId":109,"timeUse":"37.00","times":1,"createTime":"2022-11-21 18:21:29","speed":"4.00","finishTimes":8,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}],"courseName":"课程一","imgUrl":"http://mingzhou.nbqichen.com:8888/assets/icon_course.png","labels":"三年级 | 课内 | 搭配教材 | 心肺耐力"},{"id":5342,"studentId":240,"classRecordId":288,"status":1,"classRecord":null,"studentResultsList":[{"id":2368,"studentId":240,"courseId":null,"smallCourseId":115,"timeUse":"0.00","times":0,"createTime":"2022-11-23 09:42:16","speed":"0.00","finishTimes":1,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}],"courseName":"课程二","imgUrl":"http://mingzhou.nbqichen.com:8888/assets/icon_course.png","labels":"三年级 | 课内 | 主教材 | 心肺耐力"}]
     */

    private String id;
    private String schoolId;
    private String classId;
    private String studentName;
    private String studentNo;
    private String gender;
    private String age;
    private String birthday;
    private String height;
    private String weight;
    private String imgUrl;
    private String parentPhone;
    private List<StudentClassRecordsBean> studentClassRecords;

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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public List<StudentClassRecordsBean> getStudentClassRecords() {
        return studentClassRecords;
    }

    public void setStudentClassRecords(List<StudentClassRecordsBean> studentClassRecords) {
        this.studentClassRecords = studentClassRecords;
    }

    public static class StudentClassRecordsBean {
        /**
         * id : 4729
         * studentId : 240
         * classRecordId : 271
         * status : 1
         * classRecord : null
         * studentResultsList : [{"id":1936,"studentId":240,"courseId":null,"smallCourseId":115,"timeUse":"22.00","times":1,"createTime":"2022-11-21 12:03:21","speed":"11.00","finishTimes":2,"studentName":"施瑾睿","courseName":null,"timeNode":null,"motionFrequency":[],"smallCourseVo":null,"highestFrequency":null,"averageFrequency":null,"minimumFrequency":null,"className":null}]
         * courseName : 课程二
         * imgUrl : http://mingzhou.nbqichen.com:8888/assets/icon_course.png
         * labels : 三年级 | 课内 | 主教材 | 心肺耐力
         */

        private String id;
        private String studentId;
        private String classRecordId;
        private String status;
        private String classRecord;
        private String courseName;
        private String imgUrl;
        private String labels;
        private List<StudentResultsListBean> studentResultsList;

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

        public String getClassRecordId() {
            return classRecordId;
        }

        public void setClassRecordId(String classRecordId) {
            this.classRecordId = classRecordId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getClassRecord() {
            return classRecord;
        }

        public void setClassRecord(String classRecord) {
            this.classRecord = classRecord;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        public List<StudentResultsListBean> getStudentResultsList() {
            return studentResultsList;
        }

        public void setStudentResultsList(List<StudentResultsListBean> studentResultsList) {
            this.studentResultsList = studentResultsList;
        }

        public static class StudentResultsListBean {
            /**
             * id : 1936
             * studentId : 240
             * courseId : null
             * smallCourseId : 115
             * timeUse : 22.00
             * times : 1
             * createTime : 2022-11-21 12:03:21
             * speed : 11.00
             * finishTimes : 2
             * studentName : 施瑾睿
             * courseName : null
             * timeNode : null
             * motionFrequency : []
             * smallCourseVo : null
             * highestFrequency : null
             * averageFrequency : null
             * minimumFrequency : null
             * className : null
             */

            private String id;
            private String studentId;
            private String courseId;
            private String smallCourseId;
            private int timeUse;
            private int times;
            private String createTime;
            private double speed;
            private int finishTimes;
            private String studentName;
            private String courseName;
            private String timeNode;
            private String smallCourseVo;
            private String highestFrequency;
            private String averageFrequency;
            private String minimumFrequency;
            private String className;
            private List<Float> motionFrequency;

            public void setMotionFrequency(List<Float> motionFrequency) {
                this.motionFrequency = motionFrequency;
            }

            public List<Float> getMotionFrequency() {
                return motionFrequency;
            }

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

            public int getTimeUse() {
                return timeUse;
            }

            public void setTimeUse(int timeUse) {
                this.timeUse = timeUse;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public double getSpeed() {
                return speed;
            }

            public void setSpeed(double speed) {
                this.speed = speed;
            }

            public int getFinishTimes() {
                return finishTimes;
            }

            public void setFinishTimes(int finishTimes) {
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

            public String getTimeNode() {
                return timeNode;
            }

            public void setTimeNode(String timeNode) {
                this.timeNode = timeNode;
            }

            public String getSmallCourseVo() {
                return smallCourseVo;
            }

            public void setSmallCourseVo(String smallCourseVo) {
                this.smallCourseVo = smallCourseVo;
            }

            public String getHighestFrequency() {
                return highestFrequency;
            }

            public void setHighestFrequency(String highestFrequency) {
                this.highestFrequency = highestFrequency;
            }

            public String getAverageFrequency() {
                return averageFrequency;
            }

            public void setAverageFrequency(String averageFrequency) {
                this.averageFrequency = averageFrequency;
            }

            public String getMinimumFrequency() {
                return minimumFrequency;
            }

            public void setMinimumFrequency(String minimumFrequency) {
                this.minimumFrequency = minimumFrequency;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }
        }
    }
}
