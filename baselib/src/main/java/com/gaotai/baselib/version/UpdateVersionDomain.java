package com.gaotai.baselib.version;

/**
 * 版本获取信息
 */
public class UpdateVersionDomain {
    private String filePath = "";// apk文件下载地址
    private String version = "";//最新版本号
    private String versionDesc = "";//版本说明
    private String versionCode = "";//最新版本apk MD5校验值
    private String filesPatch = "";//差分包下载地址
    private String isupdateapk = "";//是否强制更新


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getFilesPatch() {
        return filesPatch;
    }

    public void setFilesPatch(String filesPatch) {
        this.filesPatch = filesPatch;
    }

    public String getIsupdateapk() {
        return isupdateapk;
    }

    public void setIsupdateapk(String isupdateapk) {
        this.isupdateapk = isupdateapk;
    }
}
