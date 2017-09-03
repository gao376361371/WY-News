package com.m520it.newreader09;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import bean.NewsCommentAdapter;
import bean.NewsCommentBean;
import http.HttpHelper;
import http.StringCallback;
import util.Constant;
import util.JsonUtil;

import static com.m520it.newreader09.NewsDetailActivity.NEWS_ID;


public class CommentActivity extends AppCompatActivity {
    private LinearLayout mActivityKeybroadMan;
    private ListView mListViewReply;
    private TextView mTvSendReply;
    private EditText mEtReply;
    private String mNews_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        mNews_id = "";
        if(intent!=null){
            mNews_id = intent.getStringExtra(NEWS_ID);
        }
        initView();
        initData();
    }

    private void initData() {
        String url = Constant.getNewsCommentUrl(mNews_id);
        Log.e(getClass().getSimpleName()+" xmg", "initData: "+url);
        HttpHelper.getInstence().requestForAsyncGET(url, new StringCallback() {
            @Override
            public void onFail(IOException e) {

            }

            @Override
            public void onSuccess(String result) {
                Log.e(getClass().getSimpleName()+" xmg", "onSuccess: ");
                parseJson(result);
            }
        });
    }

    private void parseJson(String result) {
        //0 准备一个集合
        ArrayList<NewsCommentBean> beanArrayList = new ArrayList<>();

        //1 先准备出来最外层所对应的jsonObject
        try {
            JSONObject jsonObject = new JSONObject(result);
            //2 将jsonObject中的id数组以及下方de包含各个评论的comments的json对象
            JSONArray commentIds = jsonObject.getJSONArray("commentIds");
            JSONObject comments = jsonObject.getJSONObject("comments");
            //3 遍历id数组,将每个id取出来(如果id包含逗号,拿就只取最后一个逗号后面的id出来)
            for (int i = 0; i < commentIds.length(); i++) {
                String commentId = commentIds.getString(i);
                if(commentId.contains(",")){
                    //就只取最后一个逗号后面的id出来
                    int lastIndexOf = commentId.lastIndexOf(",");
                    commentId = commentId.substring(lastIndexOf+1);
                }
                //4 通过id作为key,去comments的json对象里取出对应的评论json字符串出来
                JSONObject comment = comments.getJSONObject(commentId);
                Log.e(getClass().getSimpleName()+" xmg", "parseJson: "+comment.toString());
                //5 通过gson将第四步取出来的json字符串来进行解析为javaBean
                NewsCommentBean commentBean = JsonUtil.parseJson(comment.toString(), NewsCommentBean.class);
                beanArrayList.add(commentBean);
            }
            Log.e(getClass().getSimpleName()+" xmg", "parseJson: ");

            //设置adapter
            NewsCommentAdapter newsCommentAdapter = new NewsCommentAdapter(beanArrayList);
            mListViewReply.setAdapter(newsCommentAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        mActivityKeybroadMan = (LinearLayout) findViewById(R.id.activity_keybroad_man);
        mListViewReply = (ListView) findViewById(R.id.listView_reply);
        mTvSendReply = (TextView) findViewById(R.id.tv_send_reply);
        mEtReply = (EditText) findViewById(R.id.et_reply);
    }
}
