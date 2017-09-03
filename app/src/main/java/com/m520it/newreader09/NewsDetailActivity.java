package com.m520it.newreader09;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import bean.NewsDetailBean;
import bean.NewsDetailImageBean;
import http.HttpHelper;
import http.StringCallback;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import util.Constant;
import util.JsonUtil;

import static com.m520it.newreader09.ShowPicActivity.SHOW_PIC_INDEX;
import static com.m520it.newreader09.ShowPicActivity.SHOW_PIC_LIST;

public class NewsDetailActivity extends SwipeBackActivity {

    public static final String NEWS_ID = "NEWS_ID";
    private String mNews_id;
    private NewsDetailBean webView;
    private WebView mWebView;
    private EditText mEtReply;
    private TextView mTvReply;
    private ImageView mIvShare;
    private TextView mTvSendReply;
    private ArrayList<NewsDetailImageBean> mImageBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent intent = getIntent();
        mNews_id = " ";
        if (intent!=null){
            mNews_id = intent.getStringExtra(NEWS_ID);
        }
        Log.e(getClass().getSimpleName()+" xmg", "onCreate: "+ mNews_id);
        initData();
        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        mEtReply = (EditText) findViewById(R.id.et_reply);
        mTvReply = (TextView) findViewById(R.id.tv_reply);
        mIvShare = (ImageView) findViewById(R.id.iv_share);
        mTvSendReply = (TextView) findViewById(R.id.tv_send_reply);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(NewsDetailActivity.this,"demo");
        final Drawable drawableft = getResources().getDrawable(R.drawable.icon_edit_icon);
        final Drawable drawableBottom = getResources().getDrawable(R.drawable.bg_edit_text);
        //设置一下边界区域大小,不然没没东西显示
        drawableft.setBounds(0,0,drawableft.getIntrinsicWidth(),drawableft.getIntrinsicHeight());
        drawableBottom.setBounds(0,0,drawableBottom.getIntrinsicWidth(),drawableBottom.getIntrinsicHeight());
        //输入框设置监听
        mEtReply.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mIsFocused = hasFocus;
                if (hasFocus){
                    //隐藏左边的图片
                    mEtReply.setCompoundDrawables(null,null,null,drawableBottom);
                    //展示一些view和隐藏一些view
                    mIvShare.setVisibility(View.GONE);
                    mTvReply.setVisibility(View.GONE);
                    mTvSendReply.setVisibility(View.VISIBLE);
                }else{
                    //展示左边的图片

                    mEtReply.setCompoundDrawables(drawableft,null,null,drawableBottom);
                    //展示一些view和隐藏一些view
                    mIvShare.setVisibility(View.VISIBLE);
                    mTvReply.setVisibility(View.VISIBLE);
                    mTvSendReply.setVisibility(View.GONE);
                }

            }
        });
        //设置文本的点击时间
        mTvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CommentActivity.class);
                intent.putExtra(NEWS_ID,mNews_id);
                startActivity(intent);
            }
        });
    }
    private boolean mIsFocused = false;

    @Override
    public void onBackPressed() {
        if (mIsFocused){
            //取消焦点
            mWebView.requestFocus();
        }else {
            super.onBackPressed();
        }
    }

    private void initData() {
        String url = Constant.getNewsDetailUrl(mNews_id);
        HttpHelper.getInstence().requestForAsyncGET(url, new StringCallback() {
            @Override
            public void onFail(IOException e) {

            }

            @Override
            public void onSuccess(String result) {
                //最为曾的键名会一直变,用原生解析
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String string = jsonObject.getString(mNews_id);
                    //原生解析到里面json字符串后,就可以使用第三方来解析了
                    NewsDetailBean newsDetailBean = JsonUtil.parseJson(string, NewsDetailBean.class);
                    Log.e(getClass().getSimpleName() + " xmg", "onSuccess: ");
                    mTvReply.setText(newsDetailBean.getReplyCount()+"");
                    setWebView(newsDetailBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setWebView(NewsDetailBean newsDetailBean) {
        String body = newsDetailBean.getBody();

        //加载标题
        String titleHTML = "<p style = \"margin:25px 0px 0px 0px\"><span style='font-size:22px;'><strong>" + newsDetailBean.getTitle() + "</strong></span></p>";// 标题
        titleHTML = titleHTML+ "<p><span style='color:#999999;font-size:14px;'>"+newsDetailBean.getSource()+"&nbsp&nbsp"+newsDetailBean.getPtime()+"</span></p>";//来源与时间
        titleHTML = titleHTML+"<div style=\"border-top:1px dotted #999999;margin:20px 0px\"></div>";//加条虚线
        //设置正文的字体
        body = "<div style='line-height:180%;'><span style='font-size:15px;'>"+body+"</span></div>";
        //替换body中图片标签,用来进行图片展示
        mImageBeanList = (ArrayList<NewsDetailImageBean>) newsDetailBean.getImg();
        for (int i = 0; i < mImageBeanList.size(); i++) {
            NewsDetailImageBean newsDetailImageBean = mImageBeanList.get(i);
            String ref = newsDetailImageBean.getRef();
            String src = newsDetailImageBean.getSrc();
            body = body.replace(ref,"<img onClick=\"show("+i+")\" src=\""+src+"\"/>");
        }

        body = "<html><head><style>img{width:100%}</style>" +
                "<script type=\"text/javascript\">function show(i){window.demo.showPic(i);}</script>"+
                "</head>"+titleHTML+body+"</html>";
        mWebView.loadData(body,"text/html;charset=UTF-8",null);
    }
    @JavascriptInterface
    public void showPic(int index){
        Intent intent = new Intent(getApplicationContext(),ShowPicActivity.class);
        intent.putExtra(SHOW_PIC_INDEX,index);
        intent.putExtra(SHOW_PIC_LIST,mImageBeanList);
        startActivity(intent);
    }
}
