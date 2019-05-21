package com.zcf.bananavideohannan.bean.bqsx;

import java.util.List;

public class BqsxparentBean {
    private int label_id;
    private int label_pid;
    private String label;
    private List<BqsxChildBean> children;

    public List<BqsxChildBean> getChildren() {
        return children;
    }

    public void setChildren(List<BqsxChildBean> children) {
        this.children = children;
    }

    public int getLabel_id() {
        return label_id;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public int getLabel_pid() {
        return label_pid;
    }

    public void setLabel_pid(int label_pid) {
        this.label_pid = label_pid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
