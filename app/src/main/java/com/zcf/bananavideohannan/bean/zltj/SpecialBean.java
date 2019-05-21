package com.zcf.bananavideohannan.bean.zltj;

import com.zcf.bananavideohannan.domain.DefFilmDomain;

import java.util.List;

public class SpecialBean {
//       "special_id":3,
//               "special":"主页专题一",
//               "intro":"这是主页专题一的简介",
//               "icon":"http://47.100.160.168:829/recommend/20181211/d6392d4d07854acf12faed69714cead1.jpg",
//               "image":"http://47.100.160.168:829/recommend/20181211/b85c9568dbbf919ec404b0a25659aa65.jpg"

    private int code;
    private List<DefFilmDomain> data;
    private Special special;

    public Special getSpecial() {
        return special;
    }

    public void setSpecial(Special special) {
        this.special = special;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DefFilmDomain> getData() {
        return data;
    }

    public void setData(List<DefFilmDomain> data) {
        this.data = data;
    }


    public class Special {
        private int special_id;
        private String special;
        private String intro;
        private String icon;
        private String image;

        public int getSpecial_id() {
            return special_id;
        }

        public void setSpecial_id(int special_id) {
            this.special_id = special_id;
        }

        public String getSpecial() {
            return special;
        }

        public void setSpecial(String special) {
            this.special = special;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

}
