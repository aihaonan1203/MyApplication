package com.zcf.bananavideohannan.dbmodel;

public class IdeasBean {
    private int bqid;
    private String msg;
    public boolean isSelect;

    public IdeasBean(int bqid, String msg) {
        this.bqid = bqid;
        this.msg = msg;
    }

    public int getBqid() {
        return bqid;
    }

    public void setBqid(int bqid) {
        this.bqid = bqid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getIsSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
