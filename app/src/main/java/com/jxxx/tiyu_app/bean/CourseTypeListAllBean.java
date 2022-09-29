package com.jxxx.tiyu_app.bean;

import java.util.List;

public class CourseTypeListAllBean {

    /**
     * id : 1
     * parentId : 0
     * typeName : 基础体能
     * children : [{"id":6,"parentId":1,"typeName":"走","children":null},{"id":7,"parentId":1,"typeName":"跑","children":null},{"id":8,"parentId":1,"typeName":"跳","children":null},{"id":9,"parentId":1,"typeName":"投掷","children":null}]
     */

    private String id;
    private String parentId;
    private String typeName;
    private boolean isSelect = false;
    private List<CourseTypeListAllBean> children;

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<CourseTypeListAllBean> getChildren() {
        return children;
    }

    public void setChildren(List<CourseTypeListAllBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 6
         * parentId : 1
         * typeName : 走
         * children : null
         */

        private String id;
        private String parentId;
        private String typeName;
        private Object children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Object getChildren() {
            return children;
        }

        public void setChildren(Object children) {
            this.children = children;
        }
    }
}
