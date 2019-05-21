package com.zcf.bananavideohannan.util;

import com.zcf.bananavideohannan.dbmodel.PlayHistoryDBModel;

import java.util.ArrayList;
import java.util.List;

public class DataTestUtils {
    public static List<PlayHistoryDBModel> getTestData() {
        List<PlayHistoryDBModel> data = new ArrayList<>();
        data.add(new PlayHistoryDBModel("1", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3825513487,3107224386&fm=27&gp=0.jpg", "电影1", "10:00"));
        data.add(new PlayHistoryDBModel("2", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3276687670,1724541020&fm=11&gp=0.jpg", "电影2", "123:32"));
        data.add(new PlayHistoryDBModel("3", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3699579472,3995360432&fm=27&gp=0.jpg", "电影3", "30:34"));
        data.add(new PlayHistoryDBModel("4", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1817217022,1602694170&fm=27&gp=0.jpg", "电影4", "25:11"));
        data.add(new PlayHistoryDBModel("5", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3699579472,3995360432&fm=27&gp=0.jpg", "电影5", "11:11"));
        data.add(new PlayHistoryDBModel("6", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1817217022,1602694170&fm=27&gp=0.jpg", "电影6", "22:22"));
        return data;
    }

}
