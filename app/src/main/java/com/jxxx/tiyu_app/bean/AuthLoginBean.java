package com.jxxx.tiyu_app.bean;

public class AuthLoginBean {

    /**
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbklkIjoic3lzX3VzZXI6MSIsInJuIjoickhWOHBkSDJVZVBSUFp0MFRVMzFSUzhvdmEwMDBDSk8ifQ.eXpFL8A8lUUdxSV1G9PGTQ3j_jbFhKcPeUr8zsV1pYo
     */

    private String access_token;
    private String teacherId;
    private String schoolId;
    private String userId;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
