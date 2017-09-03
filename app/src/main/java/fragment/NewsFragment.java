package fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.m520it.newreader09.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import adapter.MyNewsPagerAdapter;
import fragment.news.EmptyFragment;
import fragment.news.HotNewsFragment;

/**
 * Created by 17612 on 2017/7/20.
 */

public class NewsFragment extends LogFragment{
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;
    private ImageButton mIbtnArrow;
    private TextView mTvChangeDone;
    private ValueAnimator mValueAimUp;
    private ValueAnimator mValueAimDown;
    private FrameLayout mFlChangeTitle;
    private TranslateAnimation translateAnimShow;
    private TranslateAnimation translateAnimHide;
    private TextView tvChangeTip;








    @Override
    public View onChildCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag_news, container, false);
        mViewPager = (ViewPager) inflate.findViewById(R.id.viewPager);
        mSmartTabLayout = (SmartTabLayout) inflate.findViewById(R.id.viewpagertab);
        mIbtnArrow = (ImageButton) inflate.findViewById(R.id.ibtn_arrow);
        mTvChangeDone = (TextView) inflate.findViewById(R.id.tv_change_done);
        mFlChangeTitle = (FrameLayout) inflate.findViewById(R.id.fl_change_title);
        tvChangeTip = (TextView) inflate.findViewById(R.id.tv_change_tip);
        initView();
        initAnim();
        return inflate;
    }

    private void initView() {
        mIbtnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //必须动画播放完毕,才可以继续执行逻辑代码
                if (!isFinish){
                    return;
                }
                //播放一个动画,并且要去展示或者隐藏白屏的遮盖区域
                //如果箭头是向下的就播放up
                if (isDown){
                    mValueAimUp.start();
                    //展示白屏区域
                    mFlChangeTitle.setVisibility(View.VISIBLE);
                    tvChangeTip.setVisibility(View.VISIBLE);
                    mFlChangeTitle.startAnimation(translateAnimShow);

                }else {
                    //否则就播放Down
                    mValueAimDown.start();
                    //隐藏白屏区域
                    mFlChangeTitle.setVisibility(View.GONE);
                    mFlChangeTitle.startAnimation(translateAnimHide);
                }
                    isDown = !isDown;

            }
        });
    }

    private boolean isDown = true;//代表箭头是否向下
    private boolean isFinish = true; //代表动画是否播放完了
    private void initAnim() {
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedvalue = (float) animation.getAnimatedValue();
                //对控件做些修改
                mIbtnArrow.setRotation(animatedvalue);
            }
        };
        mValueAimUp = ValueAnimator.ofFloat(0,180).setDuration(500);
        mValueAimUp.addUpdateListener(animatorUpdateListener);

        mValueAimDown = ValueAnimator.ofFloat(180, 0).setDuration(500);
        mValueAimDown.addUpdateListener(animatorUpdateListener);

        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isFinish = false;
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                isFinish = true;
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        };
        mValueAimDown.addListener(animatorListener);
        mValueAimUp.addListener(animatorListener);

        //补间动画位移
        translateAnimShow = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -1,
                TranslateAnimation.RELATIVE_TO_SELF, 0);
        translateAnimShow.setDuration(500);

        translateAnimHide = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1);
        translateAnimHide.setDuration(500);
    }

    @Override
    protected void initData() {
        //请求网络准备数据
        //准备一个Fragment集合
        ArrayList<Fragment> fragments = new ArrayList<>();
        //从strings文件中拿标题数据
        String[] stringArray = getResources().getStringArray(R.array.news_titles);
        for (int i = 0; i < stringArray.length; i++) {
            if (i==0){
                fragments.add(new HotNewsFragment());
            } else {
                fragments.add(new EmptyFragment());
            }
        }
        MyNewsPagerAdapter myNewsPagerAdapter = new MyNewsPagerAdapter(getChildFragmentManager(),fragments,stringArray);
        mViewPager.setAdapter(myNewsPagerAdapter);
        //设置一个标题语
        //关联一下下方的ViewPager
        mSmartTabLayout.setViewPager(mViewPager);
    }
}
