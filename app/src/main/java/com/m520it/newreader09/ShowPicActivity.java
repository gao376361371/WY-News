package com.m520it.newreader09;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import adapter.ShowPicAdapter;
import bean.NewsDetailImageBean;
import uk.co.senab.photoview.PhotoView;
import util.ImageUtil;

public class ShowPicActivity extends AppCompatActivity {

    public static final String SHOW_PIC_INDEX="SHOW_PIC_INDEX";
    public static final String SHOW_PIC_LIST="SHOW_PIC_LIST";
    private ArrayList<NewsDetailImageBean> mImgArrayList;

    private RelativeLayout mActivityShowPic;
    private TextView mTvIndex;
    private TextView mTvTotal;
    private ViewPager mViewPagerShowPic;
    private int mIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        Intent intent = getIntent();
        if(intent!=null){
            mIndex = intent.getIntExtra(SHOW_PIC_INDEX, 0);
            mImgArrayList = (ArrayList<NewsDetailImageBean>) intent.getSerializableExtra(SHOW_PIC_LIST);
            Log.e(getClass().getSimpleName()+" xmg", "onCreate: "+mIndex+" mImgArrayList "+mImgArrayList.size());
        }
        initView();
        initData();
    }

    private void initData(){
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < mImgArrayList.size(); i++) {
            PhotoView photoView = new PhotoView(getApplicationContext());
            NewsDetailImageBean newsDetailImageBean = mImgArrayList.get(i);
            ImageUtil.getSingleton().showImage(newsDetailImageBean.getSrc(),photoView);
            imageViews.add(photoView);
        }
        ShowPicAdapter showPicAdapter = new ShowPicAdapter(imageViews);
        mViewPagerShowPic.setAdapter(showPicAdapter);

        mTvIndex.setText(mIndex+1+"/");
        mTvTotal.setText(mImgArrayList.size()+"");

        mViewPagerShowPic.setCurrentItem(mIndex);
    }

    private void initView() {
        mActivityShowPic = (RelativeLayout) findViewById(R.id.activity_show_pic);
        mTvIndex = (TextView) findViewById(R.id.tv_index);
        mTvTotal = (TextView) findViewById(R.id.tv_total);
        mViewPagerShowPic = (ViewPager) findViewById(R.id.viewPager_show_pic);

        mViewPagerShowPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvIndex.setText(position+1+"/");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
