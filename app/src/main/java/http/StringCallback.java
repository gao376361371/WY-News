package http;

import java.io.IOException;

/**
 * Created by 17612 on 2017/7/21.
 */

public interface StringCallback {
    void onFail(IOException e);
    void onSuccess(String result);
}
