package bean;

import java.util.List;

/**
 * Created by 17612 on 2017/7/25.
 */

public class NewsDetailBean {
    String body;//正文
    String title;//标题
    String source;//来源
    String ptime;//发布时间
    int replyCount;

    List<NewsDetailImageBean> img;

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<NewsDetailImageBean> getImg() {
        return img;
    }

    public void setImg(List<NewsDetailImageBean> img) {
        this.img = img;
    }
}
