package com.jxxx.tiyu_app.bean;

import java.util.List;

public class UserInfoProfileBean {

    /**
     * postGroup : 董事长
     * user : {"searchValue":null,"createBy":"admin","createTime":"2022-03-03 10:59:14","updateBy":null,"updateTime":null,"params":{},"userId":1,"deptId":103,"userName":"admin","nickName":"疯狂的狮子Li","userType":"sys_user","email":"crazyLionLi@163.com","phonenumber":"15888888888","sex":"1","avatar":"","status":"0","delFlag":"0","loginIp":"127.0.0.1","loginDate":"2022-03-03 10:59:14","remark":"管理员","dept":{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"params":{},"parentName":null,"parentId":101,"children":[],"deptId":103,"deptName":"研发部门","orderNum":1,"leader":"若依","phone":null,"email":null,"status":"0","delFlag":null,"ancestors":null},"roles":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"params":{},"roleId":1,"roleName":"超级管理员","roleKey":"admin","roleSort":"1","dataScope":"1","menuCheckStrictly":false,"deptCheckStrictly":false,"status":"0","delFlag":null,"remark":null,"flag":false,"menuIds":null,"deptIds":null,"admin":true}],"roleIds":null,"postIds":null,"roleId":null,"admin":true}
     * roleGroup : 超级管理员
     */

    private String postGroup;
    private UserBean user;
    private String roleGroup;

    public String getPostGroup() {
        return postGroup;
    }

    public void setPostGroup(String postGroup) {
        this.postGroup = postGroup;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getRoleGroup() {
        return roleGroup;
    }

    public void setRoleGroup(String roleGroup) {
        this.roleGroup = roleGroup;
    }

    public static class UserBean {
        /**
         * searchValue : null
         * createBy : admin
         * createTime : 2022-03-03 10:59:14
         * updateBy : null
         * updateTime : null
         * params : {}
         * userId : 1
         * deptId : 103
         * userName : admin
         * nickName : 疯狂的狮子Li
         * userType : sys_user
         * email : crazyLionLi@163.com
         * phonenumber : 15888888888
         * sex : 1
         * avatar :
         * status : 0
         * delFlag : 0
         * loginIp : 127.0.0.1
         * loginDate : 2022-03-03 10:59:14
         * remark : 管理员
         * dept : {"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"params":{},"parentName":null,"parentId":101,"children":[],"deptId":103,"deptName":"研发部门","orderNum":1,"leader":"若依","phone":null,"email":null,"status":"0","delFlag":null,"ancestors":null}
         * roles : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"params":{},"roleId":1,"roleName":"超级管理员","roleKey":"admin","roleSort":"1","dataScope":"1","menuCheckStrictly":false,"deptCheckStrictly":false,"status":"0","delFlag":null,"remark":null,"flag":false,"menuIds":null,"deptIds":null,"admin":true}]
         * roleIds : null
         * postIds : null
         * roleId : null
         * admin : true
         */

        private Object searchValue;
        private String createBy;
        private String createTime;
        private Object updateBy;
        private Object updateTime;
        private ParamsBean params;
        private String userId;
        private String deptId;
        private String userName;
        private String nickName;
        private String userType;
        private String email;
        private String phonenumber;
        private String sex;
        private String avatar;
        private String status;
        private String delFlag;
        private String loginIp;
        private String loginDate;
        private String remark;
        private DeptBean dept;
        private Object roleIds;
        private Object postIds;
        private Object roleId;
        private boolean admin;
        private List<RolesBean> roles;

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(String loginIp) {
            this.loginIp = loginIp;
        }

        public String getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(String loginDate) {
            this.loginDate = loginDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public DeptBean getDept() {
            return dept;
        }

        public void setDept(DeptBean dept) {
            this.dept = dept;
        }

        public Object getRoleIds() {
            return roleIds;
        }

        public void setRoleIds(Object roleIds) {
            this.roleIds = roleIds;
        }

        public Object getPostIds() {
            return postIds;
        }

        public void setPostIds(Object postIds) {
            this.postIds = postIds;
        }

        public Object getRoleId() {
            return roleId;
        }

        public void setRoleId(Object roleId) {
            this.roleId = roleId;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public List<RolesBean> getRoles() {
            return roles;
        }

        public void setRoles(List<RolesBean> roles) {
            this.roles = roles;
        }

        public static class ParamsBean {
        }

        public static class DeptBean {
            /**
             * searchValue : null
             * createBy : null
             * createTime : null
             * updateBy : null
             * updateTime : null
             * params : {}
             * parentName : null
             * parentId : 101
             * children : []
             * deptId : 103
             * deptName : 研发部门
             * orderNum : 1
             * leader : 若依
             * phone : null
             * email : null
             * status : 0
             * delFlag : null
             * ancestors : null
             */

            private Object searchValue;
            private Object createBy;
            private Object createTime;
            private Object updateBy;
            private Object updateTime;
            private ParamsBeanX params;
            private Object parentName;
            private String parentId;
            private String deptId;
            private String deptName;
            private String orderNum;
            private String leader;
            private Object phone;
            private Object email;
            private String status;
            private Object delFlag;
            private Object ancestors;
            private List<?> children;

            public Object getSearchValue() {
                return searchValue;
            }

            public void setSearchValue(Object searchValue) {
                this.searchValue = searchValue;
            }

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public ParamsBeanX getParams() {
                return params;
            }

            public void setParams(ParamsBeanX params) {
                this.params = params;
            }

            public Object getParentName() {
                return parentName;
            }

            public void setParentName(Object parentName) {
                this.parentName = parentName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getDeptId() {
                return deptId;
            }

            public void setDeptId(String deptId) {
                this.deptId = deptId;
            }

            public String getDeptName() {
                return deptName;
            }

            public void setDeptName(String deptName) {
                this.deptName = deptName;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getLeader() {
                return leader;
            }

            public void setLeader(String leader) {
                this.leader = leader;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(Object delFlag) {
                this.delFlag = delFlag;
            }

            public Object getAncestors() {
                return ancestors;
            }

            public void setAncestors(Object ancestors) {
                this.ancestors = ancestors;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }

            public static class ParamsBeanX {
            }
        }

        public static class RolesBean {
            /**
             * searchValue : null
             * createBy : null
             * createTime : null
             * updateBy : null
             * updateTime : null
             * params : {}
             * roleId : 1
             * roleName : 超级管理员
             * roleKey : admin
             * roleSort : 1
             * dataScope : 1
             * menuCheckStrictly : false
             * deptCheckStrictly : false
             * status : 0
             * delFlag : null
             * remark : null
             * flag : false
             * menuIds : null
             * deptIds : null
             * admin : true
             */

            private Object searchValue;
            private Object createBy;
            private Object createTime;
            private Object updateBy;
            private Object updateTime;
            private ParamsBeanXX params;
            private String roleId;
            private String roleName;
            private String roleKey;
            private String roleSort;
            private String dataScope;
            private boolean menuCheckStrictly;
            private boolean deptCheckStrictly;
            private String status;
            private Object delFlag;
            private Object remark;
            private boolean flag;
            private Object menuIds;
            private Object deptIds;
            private boolean admin;

            public Object getSearchValue() {
                return searchValue;
            }

            public void setSearchValue(Object searchValue) {
                this.searchValue = searchValue;
            }

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public ParamsBeanXX getParams() {
                return params;
            }

            public void setParams(ParamsBeanXX params) {
                this.params = params;
            }

            public String getRoleId() {
                return roleId;
            }

            public void setRoleId(String roleId) {
                this.roleId = roleId;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public String getRoleKey() {
                return roleKey;
            }

            public void setRoleKey(String roleKey) {
                this.roleKey = roleKey;
            }

            public String getRoleSort() {
                return roleSort;
            }

            public void setRoleSort(String roleSort) {
                this.roleSort = roleSort;
            }

            public String getDataScope() {
                return dataScope;
            }

            public void setDataScope(String dataScope) {
                this.dataScope = dataScope;
            }

            public boolean isMenuCheckStrictly() {
                return menuCheckStrictly;
            }

            public void setMenuCheckStrictly(boolean menuCheckStrictly) {
                this.menuCheckStrictly = menuCheckStrictly;
            }

            public boolean isDeptCheckStrictly() {
                return deptCheckStrictly;
            }

            public void setDeptCheckStrictly(boolean deptCheckStrictly) {
                this.deptCheckStrictly = deptCheckStrictly;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(Object delFlag) {
                this.delFlag = delFlag;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public Object getMenuIds() {
                return menuIds;
            }

            public void setMenuIds(Object menuIds) {
                this.menuIds = menuIds;
            }

            public Object getDeptIds() {
                return deptIds;
            }

            public void setDeptIds(Object deptIds) {
                this.deptIds = deptIds;
            }

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }

            public static class ParamsBeanXX {
            }
        }
    }
}

