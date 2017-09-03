package com.m520it.newreader09;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import fragment.MeFragment;
import fragment.NewsFragment;
import fragment.TopicFragment;
import fragment.VaFragment;
import widget.MyFragmentTabHost;

public class HomeActivity extends AppCompatActivity {

    private MyFragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTabHost = (MyFragmentTabHost) findViewById(R.id.tabHost);
        initTabHost();
    }
    public Class[] mFragments = new Class[]{NewsFragment.class, VaFragment.class,
            TopicFragment.class, MeFragment.class};
    public String[] mTabTexts = new String[]{"新闻","直播","话题","我"};
    public int[] mResIds = new int[]{R.drawable.tab_news,R.drawable.tab_va,
            R.drawable.tab_topic,R.drawable.tab_me};
    private void initTabHost() {
        //初始化
        mTabHost.setup(getApplicationContext(),getSupportFragmentManager(),R.id.fl_content);
        //添加tab
        for (int i = 0; i < mFragments.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i+"");
            View view = View.inflate(getApplicationContext(),R.layout.item_tab,null);

            ImageView ivTab = (ImageView) view.findViewById(R.id.iv_tab);
            TextView tv_Tab = (TextView) view.findViewById(R.id.tv_tab);

            tv_Tab.setText(mTabTexts[i]);
            ivTab.setImageResource(mResIds[i]);

            tabSpec.setIndicator(view);
            mTabHost.addTab(tabSpec,mFragments[i],null);
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(HomeActivity.this, "tabId:"+tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
