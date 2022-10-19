package com.jxxx.tiyu_app.bean;

import java.util.List;

public class SchoolStudentDetailBean {


    /**
     * age : 0
     * birthday :
     * classId : 0
     * gender : 0
     * height : 0
     * id : 0
     * imgUrl :
     * parentPhone :
     * schoolId : 0
     * studentClassRecords : [{"classRecord":{"classDate":"","classId":0,"classSceduleCardId":0,"courseId":0,"courseName":"","id":0,"imgUrl":"","labels":"","smallCourseId":0,"teacherId":0},"classRecordId":0,"id":0,"status":0,"studentId":0,"studentResultsList":[{"courseId":0,"createTime":"","finishTimes":0,"id":0,"smallCourseId":0,"speed":0,"studentId":0,"studentName":"","timeUse":0,"times":0}]}]
     * studentName :
     * studentNo :
     * weight : 0
     */

    private int age;
    private String birthday;
    private String classId;
    private int gender;
    private String height;
    private String id;
    private String imgUrl;
    private String parentPhone;
    private String schoolId;
    private String studentName;
    private String studentNo;
    private String weight;
    private List<StudentClassRecordsBean> studentClassRecords;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<StudentClassRecordsBean> getStudentClassRecords() {
        return studentClassRecords;
    }

    public void setStudentClassRecords(List<StudentClassRecordsBean> studentClassRecords) {
        this.studentClassRecords = studentClassRecords;
    }

    public static class StudentClassRecordsBean {
        /**
         * classRecord : {"classDate":"","classId":0,"classSceduleCardId":0,"courseId":0,"courseName":"","id":0,"imgUrl":"","labels":"","smallCourseId":0,"teacherId":0}
         * classRecordId : 0
         * id : 0
         * status : 0
         * studentId : 0
         * studentResultsList : [{"courseId":0,"createTime":"","finishTimes":0,"id":0,"smallCourseId":0,"speed":0,"studentId":0,"studentName":"","timeUse":0,"times":0}]
         */

        private ClassRecordBean classRecord;
        private String classRecordId;
        private String id;
        private String status;
        private String studentId;
        private List<StudentResultsListBean> studentResultsList;

        public ClassRecordBean getClassRecord() {
            return classRecord;
        }

        public void setClassRecord(ClassRecordBean classRecord) {
            this.classRecord = classRecord;
        }

        public String getClassRecordId() {
            return classRecordId;
        }

        public void setClassRecordId(String classRecordId) {
            this.classRecordId = classRecordId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public List<StudentResultsListBean> getStudentResultsList() {
            return studentResultsList;
        }

        public void setStudentResultsList(List<StudentResultsListBean> studentResultsList) {
            this.studentResultsList = studentResultsList;
        }

        public static class ClassRecordBean {
            /**
             * classDate :
             * classId : 0
             * classSceduleCardId : 0
             * courseId : 0
             * courseName :
             * id : 0
             * imgUrl :
             * labels :
             * smallCourseId : 0
             * teacherId : 0
             */

            private String classDate;
            private String classId;
            private String classSceduleCardId;
            private String courseId;
            private String courseName;
            private String id;
            private String imgUrl;
            private String labels;
            private String smallCourseId;
            private String teacherId;

            public String getClassDate() {
                return classDate;
            }

            public void setClassDate(String classDate) {
                this.classDate = classDate;
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getSmallCourseId() {
                return smallCourseId;
            }

            public void setSmallCourseId(String smallCourseId) {
                this.smallCourseId = smallCourseId;
            }

            public String getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(String teacherId) {
                this.teacherId = teacherId;
            }
        }

        public static class StudentResultsListBean {
            /**
             * courseId : 0
             * createTime :
             * finishTimes : 0
             * id : 0
             * smallCourseId : 0
             * speed : 0
             * studentId : 0
             * studentName :
             * timeUse : 0
             * times : 0
             */

            private String courseId;
            private String createTime;
            private String finishTimes;
            private String id;
            private String smallCourseId;
            private String speed;
            private String studentId;
            private String studentName;
            private String timeUse;
            private String times;

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getFinishTimes() {
                return finishTimes;
            }

            public void setFinishTimes(String finishTimes) {
                this.finishTimes = finishTimes;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getStudentName() {
                return studentName;
            }

            public void setStudentName(String studentName) {
                this.studentName = studentName;
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
        }
    }
}
