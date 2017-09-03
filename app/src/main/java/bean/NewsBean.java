package bean;

import java.util.ArrayList;

/**
 * Created by 17612 on 2017/7/22.
 */

public class NewsBean {
    String img;
    String source;
    String title;
    String replyCount;//回帖数
    String interest; //为S代表置顶
    String id;//需要ID请求新闻详情

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    ArrayList<BannerBean> ads;//轮播图数据

    public ArrayList<BannerBean> getAds() {
        return ads;
    }

    public void setAds(ArrayList<BannerBean> ads) {
        this.ads = ads;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }


}
