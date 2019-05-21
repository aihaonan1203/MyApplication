package com.zcf.bananavideohannan.bean;

public class IssueBean {


    /**
     * id : 1
     * ask : APP无法启动怎么办?
     * answer : 请进入官方交流群,询问管理员
     * status : normal
     * create_time : 1554358423
     * update_time : 1554358423
     */

    private int id;
    private String ask;
    private String answer;
    private String status;
    private int create_time;
    private int update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }
}
