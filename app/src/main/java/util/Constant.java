package util;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class Constant {
    public static final String AD_URL = "http://g1.163.com/madr?app=7A16FBB6&platform=android&category=STARTUP&location=1";
    public static final String SP_NAME = "config";
    public static final String HOT_NEWS_URL = "http://c.m.163.com/nc/article/headline/T1348647909107/START-END.html?from=toutiao&size=10&passport=&devId=bMo6EQztO2ZGFBurrbgcMQ%3D%3D&net=wifi";
    public static final String NEWS_DETAIL_URL = "http://c.m.163.com/nc/article/NEWS_ID/full.html";
    public static final String NEWS_COMMENT_URL = "http://comment.api.163.com/api/v1/" +
            "products/a2869674571f77b5a0867c3d71db5856/threads/NEWS_ID/app/" +
            "comments/hotList?offset=0&limit=10&showLevelThreshold=10&headLimit=2&tailLimit=2";

    public static String getHotNewsUrl(int start , int end){
        String hot_news_url = HOT_NEWS_URL.replace("START",start+"").replace("END",end+"");
        return hot_news_url;
    }

    public static String getNewsDetailUrl(String newsId) {
        String url = NEWS_DETAIL_URL.replace("NEWS_ID", newsId);
        return url;
    }
    public static String getNewsCommentUrl(String newsId) {
        String url = NEWS_COMMENT_URL.replace("NEWS_ID", newsId);
        return url;
    }
}
