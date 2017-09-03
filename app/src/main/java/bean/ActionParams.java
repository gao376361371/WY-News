package bean;

import java.io.Serializable;

/**
 * Created by 17612 on 2017/7/18.
 */

public class ActionParams implements Serializable{
    String link_url;

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }
}
