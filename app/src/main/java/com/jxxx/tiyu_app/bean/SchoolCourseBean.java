package com.jxxx.tiyu_app.bean;

import com.jxxx.tiyu_app.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SchoolCourseBean {


    /**
     * ballNum : 0
     * courseName : 
     * courseSectionVoList : [{"actionInfo":"","courseId":0,"id":0,"loopNum":0,"sectionName":"","smallCourseId":0,"sortNum":0}]
     * delTf : 0
     * id : 0
     * imgUrl : 
     * lables : 
     * plateNum : 0
     * status : 0
     * teacherId : 0
     * teacherName : 
     * videoUrl : 
     */

    private int ballNum;
    private String courseName;
    private String delTf;
    private String id;
    private String imgUrl;
    private String lables;
    private int plateNum;
    private String status;
    private String teacherId;
    private String teacherName;
    private String videoUrl;
    private String queueInfo;
    private int groupNum;

    public void setQueueInfo(String queueInfo) {
        this.queueInfo = queueInfo;
    }

    public String getQueueInfo() {
        return queueInfo;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getGroupNum() {
        return groupNum;
    }

    private List<CourseSectionVoListBean> courseSectionVoList;

    public int getBallNum() {
        return ballNum;
    }

    public void setBallNum(int ballNum) {
        this.ballNum = ballNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDelTf() {
        return delTf;
    }

    public void setDelTf(String delTf) {
        this.delTf = delTf;
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

    public String getLables() {
        return lables;
    }

    public void setLables(String lables) {
        this.lables = lables;
    }

    public int getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(int plateNum) {
        this.plateNum = plateNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<CourseSectionVoListBean> getCourseSectionVoList() {
        if(courseSectionVoList==null){
            return new ArrayList<>();
        }
        return courseSectionVoList;
    }

    public void setCourseSectionVoList(List<CourseSectionVoListBean> courseSectionVoList) {
        this.courseSectionVoList = courseSectionVoList;
    }

    public static class CourseSectionVoListBean {
        /**
         * actionInfo : 
         * courseId : 0
         * id : 0
         * loopNum : 0
         * smallCourseId : 0
         * sortNum : 0
         */

        private String actionInfo;
        private String courseId;
        private String id;
        private int queueingNum= 1;
        private int loopNum;
        private String smallCourseId;
        private String sortNum;
        private SchoolCourseBeanSmall smallCourseVo;

        public void setQueueingNum(int queueingNum) {
            this.queueingNum = queueingNum;
        }

        public int getQueueingNum() {
            return queueingNum;
        }

        public void setSmallCourseVo(SchoolCourseBeanSmall smallCourseVo) {
            this.smallCourseVo = smallCourseVo;
        }

        public SchoolCourseBeanSmall getSmallCourseVo() {
            return smallCourseVo;
        }

        public String getActionInfo() {
            return actionInfo;
        }

        public void setActionInfo(String actionInfo) {
            this.actionInfo = actionInfo;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLoopNum() {
            return loopNum;
        }

        public void setLoopNum(int loopNum) {
            this.loopNum = loopNum;
        }

        public String getSmallCourseId() {
            return smallCourseId;
        }

        public void setSmallCourseId(String smallCourseId) {
            this.smallCourseId = smallCourseId;
        }

        public String getSortNum() {
            return sortNum;
        }

        public void setSortNum(String sortNum) {
            this.sortNum = sortNum;
        }
    }
}
