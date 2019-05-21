package com.zcf.bananavideohannan.bean.OfflineCacheBean;

import java.util.List;

public class OffCacheBean {

    /**
     * code : 200
     * data : {"total":3,"per_page":10,"current_page":1,"last_page":1,"data":[{"video_id":44,"create_time":"1551859644","image":"http://154.223.38.130/uploads/20190211/15b943a309d31caf12fceb02035980e2.jpg","name":"111","score":"8.3","video":"http://154.223.38.130/upload/1b5da831b2b070f31010eb688ba6551f.mp4","views":112,"create_time_str":"2019-03-06 16:07:24"},{"video_id":45,"create_time":"1551859644","image":"http://154.223.38.130/uploads/20190215/ec3a11d5c7e10ac46e618d82ef2d88d4.png","name":"测","score":"8.3","video":"http://154.223.38.130/upload/1b5da831b2b070f31010eb688ba6551f.mp4","views":123,"create_time_str":"2019-03-06 16:07:24"},{"video_id":42,"create_time":"1551859644","image":"http://154.223.38.130/uploads/20190105/491fe9c2c14b1472ad5cf9114041a107.png","name":"周星驰","score":"8.3","video":"http://154.223.38.130/upload/c96571e89bec1d42cc7c6e28264eb317.mp4","views":123,"create_time_str":"2019-03-06 16:07:24"}]}
     */

    private int code;
    private DataBeanX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 3
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"video_id":44,"create_time":"1551859644","image":"http://154.223.38.130/uploads/20190211/15b943a309d31caf12fceb02035980e2.jpg","name":"111","score":"8.3","video":"http://154.223.38.130/upload/1b5da831b2b070f31010eb688ba6551f.mp4","views":112,"create_time_str":"2019-03-06 16:07:24"},{"video_id":45,"create_time":"1551859644","image":"http://154.223.38.130/uploads/20190215/ec3a11d5c7e10ac46e618d82ef2d88d4.png","name":"测","score":"8.3","video":"http://154.223.38.130/upload/1b5da831b2b070f31010eb688ba6551f.mp4","views":123,"create_time_str":"2019-03-06 16:07:24"},{"video_id":42,"create_time":"1551859644","image":"http://154.223.38.130/uploads/20190105/491fe9c2c14b1472ad5cf9114041a107.png","name":"周星驰","score":"8.3","video":"http://154.223.38.130/upload/c96571e89bec1d42cc7c6e28264eb317.mp4","views":123,"create_time_str":"2019-03-06 16:07:24"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * video_id : 44
             * create_time : 1551859644
             * image : http://154.223.38.130/uploads/20190211/15b943a309d31caf12fceb02035980e2.jpg
             * name : 111
             * score : 8.3
             * video : http://154.223.38.130/upload/1b5da831b2b070f31010eb688ba6551f.mp4
             * views : 112
             * create_time_str : 2019-03-06 16:07:24
             */

            private int video_id;
            private String create_time;
            private String image;
            private String name;
            private String score;
            private String video;
            private int views;
            private String create_time_str;

            public int getVideo_id() {
                return video_id;
            }

            public void setVideo_id(int video_id) {
                this.video_id = video_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public String getCreate_time_str() {
                return create_time_str;
            }

            public void setCreate_time_str(String create_time_str) {
                this.create_time_str = create_time_str;
            }
        }
    }
}
