package com.jxxx.tiyu_app.bean;

public class VersionResponse {
    /**
     * clientType : 1
     * createTime : 1625902147000
     * downloadPath :
     * id : 12
     * versionNo : 1.1.1
     */

    private int clientType;
    private String createTime;
    private String downloadPath;
    private int id;
    private String versionNo;
    private String updateContent;

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
}
