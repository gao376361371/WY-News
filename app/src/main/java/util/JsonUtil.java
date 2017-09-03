package util;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by 17612 on 2017/7/18.
 */

public class JsonUtil {
    static Gson gson = new Gson();

    public static <T> T parseJson(String json,Class<T> clazz){
        if (TextUtils.isEmpty(json)){
            return null;
        }
        T listBean = gson.fromJson(json,clazz);
        return listBean;
    }
}
