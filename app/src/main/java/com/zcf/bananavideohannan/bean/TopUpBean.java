package com.zcf.bananavideohannan.bean;

public class TopUpBean {


    /**
     * code : 200
     * msg : 请求成功
     * time : 1557644448
     * data : {"code":0,"msg":"success","data":{"preId":"324966197680541696","thirdOrderId":"8b006ace6da0a98534c78c7378d075a0","legalCode":"CNY","legalAmount":298,"symbol":"HKT","supportPay":"1","requestType":"1","userMobile":"12345612345","accountName":null,"account":null,"bankName":null,"imgUrl":null,"status":"1","pushUrl":"http://www.baidu.com","rewriteUrl":"http://www.baidu.com","result":"匹配成功","createDate":"2019-05-12 15:00:44","token":null,"tokenExpireTime":null}}
     */

    private int code;
    private String msg;
    private String time;
    private DataBeanX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * code : 0
         * msg : success
         * data : {"preId":"324966197680541696","thirdOrderId":"8b006ace6da0a98534c78c7378d075a0","legalCode":"CNY","legalAmount":298,"symbol":"HKT","supportPay":"1","requestType":"1","userMobile":"12345612345","accountName":null,"account":null,"bankName":null,"imgUrl":null,"status":"1","pushUrl":"http://www.baidu.com","rewriteUrl":"http://www.baidu.com","result":"匹配成功","createDate":"2019-05-12 15:00:44","token":null,"tokenExpireTime":null}
         */

        private int code;
        private String msg;
        private DataBean data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * preId : 324966197680541696
             * thirdOrderId : 8b006ace6da0a98534c78c7378d075a0
             * legalCode : CNY
             * legalAmount : 298
             * symbol : HKT
             * supportPay : 1
             * requestType : 1
             * userMobile : 12345612345
             * accountName : null
             * account : null
             * bankName : null
             * imgUrl : null
             * status : 1
             * pushUrl : http://www.baidu.com
             * rewriteUrl : http://www.baidu.com
             * result : 匹配成功
             * createDate : 2019-05-12 15:00:44
             * token : null
             * tokenExpireTime : null
             */

            private String preId;
            private String thirdOrderId;
            private String legalCode;
            private int legalAmount;
            private String symbol;
            private String supportPay;
            private String requestType;
            private String userMobile;
            private Object accountName;
            private Object account;
            private Object bankName;
            private Object imgUrl;
            private String status;
            private String pushUrl;
            private String rewriteUrl;
            private String result;
            private String createDate;
            private Object token;
            private Object tokenExpireTime;

            public String getPreId() {
                return preId;
            }

            public void setPreId(String preId) {
                this.preId = preId;
            }

            public String getThirdOrderId() {
                return thirdOrderId;
            }

            public void setThirdOrderId(String thirdOrderId) {
                this.thirdOrderId = thirdOrderId;
            }

            public String getLegalCode() {
                return legalCode;
            }

            public void setLegalCode(String legalCode) {
                this.legalCode = legalCode;
            }

            public int getLegalAmount() {
                return legalAmount;
            }

            public void setLegalAmount(int legalAmount) {
                this.legalAmount = legalAmount;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public String getSupportPay() {
                return supportPay;
            }

            public void setSupportPay(String supportPay) {
                this.supportPay = supportPay;
            }

            public String getRequestType() {
                return requestType;
            }

            public void setRequestType(String requestType) {
                this.requestType = requestType;
            }

            public String getUserMobile() {
                return userMobile;
            }

            public void setUserMobile(String userMobile) {
                this.userMobile = userMobile;
            }

            public Object getAccountName() {
                return accountName;
            }

            public void setAccountName(Object accountName) {
                this.accountName = accountName;
            }

            public Object getAccount() {
                return account;
            }

            public void setAccount(Object account) {
                this.account = account;
            }

            public Object getBankName() {
                return bankName;
            }

            public void setBankName(Object bankName) {
                this.bankName = bankName;
            }

            public Object getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(Object imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPushUrl() {
                return pushUrl;
            }

            public void setPushUrl(String pushUrl) {
                this.pushUrl = pushUrl;
            }

            public String getRewriteUrl() {
                return rewriteUrl;
            }

            public void setRewriteUrl(String rewriteUrl) {
                this.rewriteUrl = rewriteUrl;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public Object getToken() {
                return token;
            }

            public void setToken(Object token) {
                this.token = token;
            }

            public Object getTokenExpireTime() {
                return tokenExpireTime;
            }

            public void setTokenExpireTime(Object tokenExpireTime) {
                this.tokenExpireTime = tokenExpireTime;
            }
        }
    }
}
