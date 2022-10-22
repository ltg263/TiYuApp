package com.jxxx.tiyu_app.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchoolCourseBeanSmallActionInfoJson {

    /**
     * groupNo : 0
     * steps : [{"sets":[["1","0","0","61","3","4"]],"stepNo":0},{"sets":[["2","0","0","61","3","4"]],"stepNo":1},{"sets":[["1","0","0","61","3","4"]],"stepNo":2}]
     */

    private int groupNo;
    private List<StepsBean> steps;

    public int getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(int groupNo) {
        this.groupNo = groupNo;
    }

    public List<StepsBean> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsBean> steps) {
        this.steps = steps;
    }

    public static class StepsBean {
        /**
         * sets : [["1","0","0","61","3","4"]]
         * stepNo : 0
         */

        private int stepNo;
        private int stepNoOkNum;//执行过的数量 sets
        private List<List<Byte>> sets;
        private List<List<Byte>> sets_cz = new ArrayList<>();


        public void setStepNoOkNum(int stepNoOkNum) {
            this.stepNoOkNum = stepNoOkNum;
        }

        public int getStepNoOkNum() {
            return stepNoOkNum;
        }

        public void setSets_cz(List<List<Byte>> sets_cz) {
            this.sets_cz = sets_cz;
        }

        public List<List<Byte>> getSets_cz() {
            return sets_cz;
        }

        public int getStepNo() {
            return stepNo;
        }

        public void setStepNo(int stepNo) {
            this.stepNo = stepNo;
        }

        public List<List<Byte>> getSets() {
            return sets;
        }

        public void setSets(List<List<Byte>> sets) {
            this.sets = sets;
        }
    }
}
