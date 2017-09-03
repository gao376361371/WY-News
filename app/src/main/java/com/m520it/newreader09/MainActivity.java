package com.m520it.newreader09;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import bean.AdsBean;
import bean.AdsListBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import util.Constant;
import util.HashCodeUtil;
import util.JsonUtil;
import util.SPUtils;
import widget.SkipView;

import static com.m520it.newreader09.AdDataActivity.AD_DETAIL_URL;
import static com.m520it.newreader09.DownloadIntentService.DOWNLOAD_SERVICE_DATA;

public class MainActivity extends AppCompatActivity {

    private static final String AD_PIC_INDEX = "AD_PIC_INDEX";
    private ImageView mIvAd;
    public static final String AD_JSON = "AD_JSON";
    public static final String OUT_DATE_TIME = "OUT_DATE_TIME";
    private SkipView mSkipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mIvAd = (ImageView) findViewById(R.id.iv_ad);
        mSkipView = (SkipView) findViewById(R.id.skipView);
        String name = Thread.currentThread().getName();
        Log.e(getClass().getSimpleName()+"xmg","onCreate: "+name);

        initData();
    }

    private void initData() {
        //如果缓存里有广告json字符串,并且该字符串没有过期,直接读取缓存,然后展示广告图
        String json = SPUtils.getString(MainActivity.this,AD_JSON);
        Long out_date_time = SPUtils.getLong(MainActivity.this,OUT_DATE_TIME);
        Long currentTimeMillis = System.currentTimeMillis();
        if (TextUtils.isEmpty(json)||currentTimeMillis>out_date_time){
            //否则就去请求数据
            requestDataForOKHttp();
            Log.e(getClass().getName()+"xmg","initData:"+"没有缓存或者缓存已过期,开始请求数据");
            return;
        }
        //有数据的话开始加载广告图进行显示
        Log.e(getClass().getName()+"xmg","initData:"+"有缓存,不需要请求网络");
        AdsListBean listBean = JsonUtil.parseJson(json,AdsListBean.class);
        List<AdsBean> ads = listBean.getAds();
        int index = SPUtils.getInt(MainActivity.this,AD_PIC_INDEX);
        //先展示第一张
        //使用前先使用%取余,这样就可以防止数组越界
        index = index % ads.size();
        AdsBean adsBean = ads.get(index);
        String picUrl = adsBean.getRes_url()[0];
        String fileName = getExternalCacheDir()+"/"+ HashCodeUtil.getHashCodeFileName(picUrl) + ".jpg";
        //有可能文件不存在(被某些不知名的的原因导致图片删除)
        File file = new File(fileName);
        if (file.exists() && file.length()>0){
            Bitmap bitmap = BitmapFactory.decodeFile(fileName);
            mIvAd.setImageBitmap(bitmap);

            //设置SkipView的跳转
            mSkipView.setVisibility(View.VISIBLE);
            mSkipView.setOnSkipListener(new SkipView.OnSkipListener() {
                @Override
                public void onSkip() {
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                }
            });
            mSkipView.start();

            index++;
            SPUtils.setInt(MainActivity.this,AD_PIC_INDEX,index);
            final String link_url = adsBean.getAction_params().getLink_url();
            //如果广告地址为空,就不做点击设置
            if (TextUtils.isEmpty(link_url)){
                return;
            }
            mIvAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSkipView.stop();
                    Intent intent = new Intent(getApplicationContext(),AdDataActivity.class);
                    intent.putExtra(AD_DETAIL_URL,link_url);
                    Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
                    Intent[] intents = {intent1,intent};
                    //通过startActivites 开启多个页面Activity
                    startActivities(intents);
                    finish();
                }
            });
        }
    }

    private void requestDataForOKHttp() {
        //OKhTTPCLient
        OkHttpClient okHttpClient = new OkHttpClient();
        //准备一个请求
        Request request = new Request.Builder().url(Constant.AD_URL).build();
        //准备一个Call对象
        okhttp3.Call call = okHttpClient.newCall(request);
        //异步enqueue
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(getClass().getSimpleName() + " xmg", "onFailure: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean successful = response.isSuccessful();
                if (!successful){
                    onFailure(call,new IOException("响应未成功"));
                    return;
                }
                //成功拿到数据 响应体,数据都在他里面
                ResponseBody body = response.body();
                String result = body.string();

                AdsListBean adsListBean = JsonUtil.parseJson(result,AdsListBean.class);
                Log.e(getClass().getSimpleName() + " xmg", "onResponse: " + adsListBean);
                startDownLoadInBackground(adsListBean);
            }
        });
    }
    //开始后台下载图片
    private void startDownLoadInBackground(AdsListBean adsListBean) {
        //先把javaBean通过GSON转换成json字符串,将其保存到缓存,
        Gson gson = new Gson();
        String json = gson.toJson(adsListBean);
        //通过SP将其保存
        SPUtils.setString(MainActivity.this,AD_JSON,json);
        //保存一下过期的时间
        Long outDateTime = System.currentTimeMillis() + adsListBean.getNext_req() * 60 * 100;
        SPUtils.setLong(MainActivity.this,OUT_DATE_TIME,outDateTime);
        //后台的进行下载,使用servive来下载
        //使用IntentService和servive的区别
        //构造方法不同,需要设置传递一个String的作为它内部的后台线程的线程名
        //可以直接执行一些后台任务,执行完任务后会自行销毁
        //注意:传递的JavaBean要能够序列化,JavaBean中嵌套的JavaBean也要能够序列化
        Intent intent = new Intent(getApplicationContext(),DownloadIntentService.class);
        intent.putExtra(DOWNLOAD_SERVICE_DATA,adsListBean);
        startService(intent);
    }



    //使用原生的方式解析json
    private void parseJsonForNeture(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            //opt 和 get区别 前者即使拿不到数据会返回一个默认值给你 后者会直接抛异常
            JSONArray ads = jsonObject.getJSONArray("ads");
            for (int i = 0; i < ads.length(); i++){
                //遍历
                JSONObject adJsonObject = ads.getJSONObject(i);
                //取出action_params
                JSONObject action_params = adJsonObject.getJSONObject("action_params");
                //取出action_params中的字段link_url
                String link_url = action_params.optString("link_url");
                //拿resurl
                JSONArray res_url = adJsonObject.getJSONArray("res_url");
                String picUrl = res_url.getString(0);
                Log.e(getClass().getSimpleName() + " xmg", "onResponse:  linkUrl " + link_url
                        + " picUrl " + picUrl);
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    private void reuqestData2() {
        //httpUrlConnection
        //HttpClient            6.0版本 已经被删除了
        //Volley            网络请求库 google  内部集成了httpUrlConnection HttpClient
        try {
            URL url = new URL(Constant.AD_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                //有数据返回
                InputStream inputStream =
                        urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append(str);
                }
                bufferedReader.close();
                String result = stringBuilder.toString();
                Log.e(getClass().getSimpleName() + " xmg", "initData: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
