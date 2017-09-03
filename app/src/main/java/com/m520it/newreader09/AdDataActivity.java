package com.m520it.newreader09;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AdDataActivity extends AppCompatActivity {

    public static final String AD_DETAIL_URL = "AD_DETAIL_URL";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_data);
        mWebView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        String linkUrl = "";
        if (intent != null){
            linkUrl = intent.getStringExtra(AD_DETAIL_URL);
            Log.e(getClass().getSimpleName()+" xmg", "onCreate: "+linkUrl);
        }
        //如果页面跳转时出现了重定向(自动跳转到 另一个页面),默认就会调用其它的应用来展示
        mWebView.setWebViewClient(new WebViewClient());
        //打开javascript的使用,不在禁止
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(linkUrl);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()){
            //如果webView能够后退到上一个页面,就后退
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
