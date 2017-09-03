package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 17612 on 2017/7/18.
 */

public class AdsListBean implements Serializable {
    List<AdsBean> ads;//属性字段的名字要和键名相同
    int next_req;//代表下次请求的时间

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }
}
