package com.zcf.bananavideohannan.bean;

public class VxInfoResultBean {
    private int code;
    private VxInfoBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public VxInfoBean getData() {
        return data;
    }

    public void setData(VxInfoBean data) {
        this.data = data;
    }

    public class VxInfoBean {
        private int id;
        private String image;
        private String wx_num;
        private String explain;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getWx_num() {
            return wx_num;
        }

        public void setWx_num(String wx_num) {
            this.wx_num = wx_num;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }
    }
}
