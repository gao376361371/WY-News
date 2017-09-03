package util;

import android.text.TextUtils;

/**
 * Created by 17612 on 2017/7/18.
 */

public class HashCodeUtil {
    public static String getHashCodeFileName(String url){
        String hashCode = "";
        if (!TextUtils.isEmpty(url)){
            int i = url.hashCode();
            hashCode = "" + i;
        }
        return hashCode;
    }
}
