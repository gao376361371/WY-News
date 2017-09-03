package http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 17612 on 2017/7/21.
 */

public class HttpHelper {
    private final OkHttpClient mOkHttpClient;

    //用单利来写这个请求工具类
    public HttpHelper() {
        mOkHttpClient = new OkHttpClient();
    }

    static HttpHelper sHttpHelper;

    public static HttpHelper getInstence(){
        if (sHttpHelper == null){
            synchronized (HttpHelper.class) {
                if (sHttpHelper == null) {
                    sHttpHelper = new HttpHelper();
                }
            }
        }
        return sHttpHelper;
    }
    //提供一个方法进行网络请求
    public void requestForAsyncGET(String url, final StringCallback stringCallback) {
        //请求数据 OKHttp
        //1,OKHttpClient 在构造方法中去创建不必创建太多次
        //2,Request
        Request request = new Request.Builder().url(url).build();
        //3,call
        Call call = mOkHttpClient.newCall(request);
        //4,call.enqueue 异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(getClass().getSimpleName()+" xmg", "onFailure: "+e);
                //自己的接口的失败方法
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        stringCallback.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()){
                    onFailure(call,new IOException("响应失败"));
                    return;
                }
                //自己的接口的成功方法
                final String string = response.body().string();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        stringCallback.onSuccess(string);
                    }
                });

            }
        });
    }


}
