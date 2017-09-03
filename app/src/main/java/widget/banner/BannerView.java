package widget.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.newreader09.R;

import java.util.ArrayList;

import util.ImageUtil;

/**
 * Created by 17612 on 2017/7/23.
 */

public class BannerView extends LinearLayout{
    private Handler mHandler;
    private ViewPager mViewPagerBanner;
    private TextView mTvBanner;
    private LinearLayout mLlDot;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private int time = 2000;
    private void init() {
        //第三种自定义控件的时候,打气筒第三个参数设置为this,将xml中的控件全部放到当前自定义控件中来显示
        View view = View.inflate(getContext(), R.layout.view_banner,this);
        mViewPagerBanner = (ViewPager) view.findViewById(R.id.viewPager_banner);
        mTvBanner = (TextView) view.findViewById(R.id.tv_banner);
        mLlDot = (LinearLayout) view.findViewById(R.id.ll_dot);

        mViewPagerBanner.addOnPageChangeListener(new MyPageChangeListener());
        //翻到下页
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //翻到下页
                int currentItem = mViewPagerBanner.getCurrentItem();
                currentItem++;
                mViewPagerBanner.setCurrentItem(currentItem);

                this.sendEmptyMessageDelayed(0,time);
            }
        };
    }

    private  ArrayList<String> mTitles;
    private  ArrayList<String> mPicUrls;
    public void setData(ArrayList<String> titles, ArrayList<String> picUrls) {
        mTitles = titles;
        mPicUrls = picUrls;
        //初始化轮播图中的图片
        initImage();
        //初始化轮播图中的小点指示器
        initDot();
        //默认选中第一个点
        selectDot(0);
        mTvBanner.setText(mTitles.get(0));
        int currentItem = Integer.MAX_VALUE / 2;
        currentItem = currentItem - currentItem%mPicUrls.size();
        mViewPagerBanner.setCurrentItem(currentItem);
        //开始自动翻页
        mHandler.sendEmptyMessageDelayed(0,time);
    }


    private void initDot() {
        //先移除掉llDot布局控件中所有子控件
        mLlDot.removeAllViews();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,-2);
        layoutParams.setMargins(0,0,10,0);
        for (int i = 0; i < mTitles.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.bg_dot);
            mLlDot.addView(imageView,layoutParams);
        }
    }

    private int mLastIndex = 0;
    private void selectDot(int i) {
        //取消之前选中的结果
        ImageView child = (ImageView) mLlDot.getChildAt(mLastIndex);
        child.setImageResource(R.drawable.bg_dot);
        mLastIndex = i;
        child = (ImageView) mLlDot.getChildAt(i);
        child.setImageResource(R.drawable.bg_dot_selected);
    }


    private void initImage() {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < mPicUrls.size(); i++) {
            String picUrl = mPicUrls.get(i);
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtil.getSingleton().showImage(picUrl,imageView);
            imageViews.add(imageView);
        }
        BannerAdapter bannerAdapter = new BannerAdapter(imageViews);
        mViewPagerBanner.setAdapter(bannerAdapter);
    }
    //用来跟新数据
    public void updateData(ArrayList<String> titles, ArrayList<String> picUrls){
        Log.e(getClass().getSimpleName()+" xmg", "updateData: "+"更新数据");
        mTitles = titles;
        mPicUrls = picUrls;
        //初始化轮播图中的图片
        initImage();
        //初始化轮播图中的小点指示器
        initDot();
        //默认选中第一个点
        selectDot(0);
        mTvBanner.setText(mTitles.get(0));
        //开始自动翻页
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(0,time);
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        //读取余数就不会越界
            position = position%mPicUrls.size();

            selectDot(position);
            mTvBanner.setText(mTitles.get(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //需要在不影响先前的触摸逻辑的前提下,添加一些新的功能,可以考虑在分发的方法中来写

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int evenAction = ev.getAction();
        switch (evenAction){
            case MotionEvent.ACTION_DOWN:
                Log.e(getClass().getSimpleName()+" xmg", "dispatchTouchEvent: "+"down");
                mHandler.removeCallbacksAndMessages(null);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(getClass().getSimpleName()+" xmg", "dispatchTouchEvent: "+"move");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e(getClass().getSimpleName()+" xmg", "dispatchTouchEvent: "+"up");
                mHandler.sendEmptyMessageDelayed(0,time);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
