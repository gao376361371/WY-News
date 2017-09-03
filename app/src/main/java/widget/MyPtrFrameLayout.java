package widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by 17612 on 2017/7/25.
 */

public class MyPtrFrameLayout extends PtrClassicFrameLayout {
    public MyPtrFrameLayout(Context context) {
        super(context);
    }

    public MyPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    float mStartX = 0;
    float mStartY = 0;

    float mSumX = 0;//x方向的偏移量
    float mSumY = 0;//y方向上的偏移量

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //如果是x方向的移动方向,就还是把事件发下去了    想发事件的话,就需要调用父类的父类的dispatch方法,
        // 也就是调用dispatchTouchEventSupper方法即可
        int eventAction = event.getAction();

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:

                mStartX = event.getX();
                mStartY = event.getY();
                mSumX = 0;//不让上一次的触摸造成影响,先初始化
                mSumY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = event.getX();
                float newY = event.getY();
                float dx = newX - mStartX;
                float dy = newY - mStartY;
                //// TODO:
                mSumX += dx;
                mSumY += dy;
                if (Math.abs(mSumX) > ViewConfiguration.getTouchSlop()
                        && Math.abs(mSumX) > Math.abs(mSumY)) {
                    //代表X方向的偏移
                    return dispatchTouchEventSupper(event);
                }
                mStartX = newX;
                mStartY = newY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        //如果不是,那就还是按照先前的下拉刷新控件的逻辑
        // 按照父类的逻辑来,也就还是super.dispatchTouchEvent(e)
        return super.dispatchTouchEvent(event);
    }
}
