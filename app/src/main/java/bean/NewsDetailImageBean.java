package bean;

import java.io.Serializable;

/**
 * Created by 17612 on 2017/7/25.
 */

public class NewsDetailImageBean implements Serializable{
    String ref;
    String src;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
