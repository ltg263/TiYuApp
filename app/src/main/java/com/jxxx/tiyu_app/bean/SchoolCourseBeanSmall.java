package com.jxxx.tiyu_app.bean;

import java.util.ArrayList;
import java.util.List;

public class SchoolCourseBeanSmall {

    /**
     * id : 4
     * courseName : 50米往返跑
     * ballNum : 0
     * plateNum : 0
     * imgUrl : https://www.nbqichen.com/upload/20220803/202208031630575804.png
     * videoUrl : null
     * courseSectionId : null
     * stepGroups : [{"id":26,"smallCourseId":4,"courseStepList":[{"id":59,"stepGroupId":26,"sortNum":0,"deviceSetList":[{"id":67,"deviceType":1,"deviceId":null,"color":0,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":26,"courseStepId":59,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":60,"stepGroupId":26,"sortNum":0,"deviceSetList":[{"id":68,"deviceType":1,"deviceId":null,"color":0,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":26,"courseStepId":60,"smallCourseId":4,"sortNum":2,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":61,"stepGroupId":26,"sortNum":0,"deviceSetList":[{"id":69,"deviceType":1,"deviceId":null,"color":0,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":26,"courseStepId":61,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null}]},{"id":27,"smallCourseId":4,"courseStepList":[{"id":62,"stepGroupId":27,"sortNum":0,"deviceSetList":[{"id":70,"deviceType":1,"deviceId":null,"color":1,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":27,"courseStepId":62,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":63,"stepGroupId":27,"sortNum":0,"deviceSetList":[{"id":71,"deviceType":1,"deviceId":null,"color":1,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":27,"courseStepId":63,"smallCourseId":4,"sortNum":2,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":64,"stepGroupId":27,"sortNum":0,"deviceSetList":[{"id":72,"deviceType":1,"deviceId":null,"color":1,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":27,"courseStepId":64,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null}]},{"id":28,"smallCourseId":4,"courseStepList":[{"id":65,"stepGroupId":28,"sortNum":0,"deviceSetList":[{"id":73,"deviceType":1,"deviceId":null,"color":3,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":28,"courseStepId":65,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":66,"stepGroupId":28,"sortNum":0,"deviceSetList":[{"id":74,"deviceType":1,"deviceId":null,"color":3,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":28,"courseStepId":66,"smallCourseId":4,"sortNum":2,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":67,"stepGroupId":28,"sortNum":0,"deviceSetList":[{"id":75,"deviceType":1,"deviceId":null,"color":3,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":28,"courseStepId":67,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null}]},{"id":29,"smallCourseId":4,"courseStepList":[{"id":68,"stepGroupId":29,"sortNum":0,"deviceSetList":[{"id":76,"deviceType":1,"deviceId":null,"color":4,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":29,"courseStepId":68,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":69,"stepGroupId":29,"sortNum":0,"deviceSetList":[{"id":77,"deviceType":1,"deviceId":null,"color":4,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":29,"courseStepId":69,"smallCourseId":4,"sortNum":2,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":70,"stepGroupId":29,"sortNum":0,"deviceSetList":[{"id":78,"deviceType":1,"deviceId":null,"color":4,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":29,"courseStepId":70,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null}]}]
     * courseSteps : null
     * lables : null
     * groupNum : null
     * queueInfo : null
     * ageRange : 0
     * trainPart : 2
     * trainType : 99
     * teacherId : null
     * randomFlag : 0
     * stepNum : 12
     * actionInfo : [{"groupNo":0,"steps":[{"stepNo":0,"sets":[["1","0","0","61","3","4"]]},{"stepNo":1,"sets":[["2","0","0","61","3","4"]]},{"stepNo":2,"sets":[["1","0","0","61","3","4"]]}]},{"groupNo":1,"steps":[{"stepNo":0,"sets":[["1","1","0","61","3","4"]]},{"stepNo":1,"sets":[["2","1","0","61","3","4"]]},{"stepNo":2,"sets":[["1","1","0","61","3","4"]]}]},{"groupNo":2,"steps":[{"stepNo":0,"sets":[["1","3","0","61","3","4"]]},{"stepNo":1,"sets":[["2","3","0","61","3","4"]]},{"stepNo":2,"sets":[["1","3","0","61","3","4"]]}]},{"groupNo":3,"steps":[{"stepNo":0,"sets":[["1","4","0","61","3","4"]]},{"stepNo":1,"sets":[["2","4","0","61","3","4"]]},{"stepNo":2,"sets":[["1","4","0","61","3","4"]]}]}]
     */

    private String id;
    private String courseName;
    private int ballNum;
    private int plateNum;
    private int totalDuration;
    private String imgUrl;
    private String videoUrl;
    private String courseSectionId;
    private String lables;
    private int loopNum;
    private int groupNum;
    private int queueNum;
    private String queueInfo;
    private String ageRange;
    private String trainPart;
    private String trainType;
    private String teacherId;
    private int randomFlag;
    private int stepNum;
    private String actionInfo;
    private String sortNumSet;
    private List<StepGroupsBean> stepGroups;

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setLoopNum(int loopNum) {
        this.loopNum = loopNum;
    }

    public int getLoopNum() {
        return loopNum;
    }

    public void setQueueNum(int queueNum) {
        this.queueNum = queueNum;
    }

    public int getQueueNum() {
        return queueNum;
    }

    public void setSortNumSet(String sortNumSet) {
        this.sortNumSet = sortNumSet;
    }

    public String getSortNumSet() {
        return sortNumSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getBallNum() {
        return 4;
    }

    public void setBallNum(int ballNum) {
        this.ballNum = ballNum;
    }

    public int getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(int plateNum) {
        this.plateNum = plateNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(String courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public String getLables() {
        return lables;
    }

    public void setLables(String lables) {
        this.lables = lables;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public String getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(String queueInfo) {
        this.queueInfo = queueInfo;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getTrainPart() {
        return trainPart;
    }

    public void setTrainPart(String trainPart) {
        this.trainPart = trainPart;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public int getRandomFlag() {
        return randomFlag;
    }

    public void setRandomFlag(int randomFlag) {
        this.randomFlag = randomFlag;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    public String getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(String actionInfo) {
        this.actionInfo = actionInfo;
    }

    public List<StepGroupsBean> getStepGroups() {
        if(stepGroups==null){
            return new ArrayList<>();
        }
        return stepGroups;
    }

    public void setStepGroups(List<StepGroupsBean> stepGroups) {
        this.stepGroups = stepGroups;
    }

    public static class StepGroupsBean {
        /**
         * id : 26
         * smallCourseId : 4
         * courseStepList : [{"id":59,"stepGroupId":26,"sortNum":0,"deviceSetList":[{"id":67,"deviceType":1,"deviceId":null,"color":0,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":26,"courseStepId":59,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":60,"stepGroupId":26,"sortNum":0,"deviceSetList":[{"id":68,"deviceType":1,"deviceId":null,"color":0,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":26,"courseStepId":60,"smallCourseId":4,"sortNum":2,"triggerAfter":4}],"smallCourseId":4,"actionMode":null},{"id":61,"stepGroupId":26,"sortNum":0,"deviceSetList":[{"id":69,"deviceType":1,"deviceId":null,"color":0,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":26,"courseStepId":61,"smallCourseId":4,"sortNum":1,"triggerAfter":4}],"smallCourseId":4,"actionMode":null}]
         */

        private String id;
        private String smallCourseId;
        private List<CourseStepListBean> courseStepList;

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

        public List<CourseStepListBean> getCourseStepList() {
            return courseStepList;
        }

        public void setCourseStepList(List<CourseStepListBean> courseStepList) {
            this.courseStepList = courseStepList;
        }

        public static class CourseStepListBean {
            /**
             * id : 59
             * stepGroupId : 26
             * sortNum : 0
             * deviceSetList : [{"id":67,"deviceType":1,"deviceId":null,"color":0,"triggerMode":3,"flickering":0,"lightTime":61,"stepGroupId":26,"courseStepId":59,"smallCourseId":4,"sortNum":1,"triggerAfter":4}]
             * smallCourseId : 4
             * actionMode : null
             */

            private String duilie_pos;
            private String buzhou_pos;
            private String id;
            private String stepGroupId;
            private String sortNum;
            private String smallCourseId;
            private String actionMode;
            private int loopNum;
            private int randomNum;
            private byte flickering;
            private byte lightTime;
            private byte lightMode;
            private byte triggerMode;
            private String randomColor;

            public void setLightMode(byte lightMode) {
                this.lightMode = lightMode;
            }

            public byte getLightMode() {
                return lightMode;
            }

            public void setTriggerMode(byte triggerMode) {
                this.triggerMode = triggerMode;
            }

            public byte getTriggerMode() {
                return triggerMode;
            }

            public void setLightTime(byte lightTime) {
                this.lightTime = lightTime;
            }

            public byte getLightTime() {
                return lightTime;
            }

            public void setFlickering(byte flickering) {
                this.flickering = flickering;
            }

            public byte getFlickering() {
                return flickering;
            }

            public void setRandomColor(String randomColor) {
                this.randomColor = randomColor;
            }

            public String getRandomColor() {
                return randomColor;
            }

            public int getRandomNum() {
                return randomNum;
            }

            public void setRandomNum(int randomNum) {
                this.randomNum = randomNum;
            }

            public int getLoopNum() {
                return loopNum;
            }

            public void setLoopNum(int loopNum) {
                this.loopNum = loopNum;
            }

            private List<DeviceSetListBean> deviceSetList;

            public void setBuzhou_pos(String buzhou_pos) {
                this.buzhou_pos = buzhou_pos;
            }

            public void setDuilie_pos(String duilie_pos) {
                this.duilie_pos = duilie_pos;
            }

            public String getBuzhou_pos() {
                return buzhou_pos;
            }

            public String getDuilie_pos() {
                return duilie_pos;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStepGroupId() {
                return stepGroupId;
            }

            public void setStepGroupId(String stepGroupId) {
                this.stepGroupId = stepGroupId;
            }

            public String getSortNum() {
                return sortNum;
            }

            public void setSortNum(String sortNum) {
                this.sortNum = sortNum;
            }

            public String getSmallCourseId() {
                return smallCourseId;
            }

            public void setSmallCourseId(String smallCourseId) {
                this.smallCourseId = smallCourseId;
            }

            public String getActionMode() {
                return actionMode;
            }

            public void setActionMode(String actionMode) {
                this.actionMode = actionMode;
            }

            public List<DeviceSetListBean> getDeviceSetList() {
                return deviceSetList;
            }

            public void setDeviceSetList(List<DeviceSetListBean> deviceSetList) {
                this.deviceSetList = deviceSetList;
            }

            public static class DeviceSetListBean {
                /**
                 * id : 67
                 * deviceType : 1
                 * deviceId : null
                 * color : 0
                 * triggerMode : 3
                 * flickering : 0
                 * lightTime : 61
                 * stepGroupId : 26
                 * courseStepId : 59
                 * smallCourseId : 4
                 * sortNum : 1
                 * triggerAfter : 4
                 */

                private String id;
                private String deviceType;
                private String deviceId;
                private String color;
                private String triggerMode;
                private String flickering;
                private String lightTime;
                private String stepGroupId;
                private String courseStepId;
                private String smallCourseId;
                private String sortNum;
                private String triggerAfter;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getDeviceType() {
                    return deviceType;
                }

                public void setDeviceType(String deviceType) {
                    this.deviceType = deviceType;
                }

                public String getDeviceId() {
                    return deviceId;
                }

                public void setDeviceId(String deviceId) {
                    this.deviceId = deviceId;
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

                public String getFlickering() {
                    return flickering;
                }

                public void setFlickering(String flickering) {
                    this.flickering = flickering;
                }

                public String getLightTime() {
                    return lightTime;
                }

                public void setLightTime(String lightTime) {
                    this.lightTime = lightTime;
                }

                public String getStepGroupId() {
                    return stepGroupId;
                }

                public void setStepGroupId(String stepGroupId) {
                    this.stepGroupId = stepGroupId;
                }

                public String getCourseStepId() {
                    return courseStepId;
                }

                public void setCourseStepId(String courseStepId) {
                    this.courseStepId = courseStepId;
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

                public String getTriggerAfter() {
                    return triggerAfter;
                }

                public void setTriggerAfter(String triggerAfter) {
                    this.triggerAfter = triggerAfter;
                }
            }
        }
    }
}
