package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 17612 on 2017/7/20.
 */

public class SkipView extends View{

    private static final int TEXT_SIZE = 30;//文字大小
    private static final int TEXT_MARGIN = 10; //文字距离中间圆边框的间距
    private static final int ARC_WIDTH = 5;//外围弧度宽度
    private Paint mTextPaint;
    private Paint mCirclePaint;
    private Paint mArcPaint;
    private float mMeasureTextWidth;
    private float mCircleDoubleRadius;
    private float mArcDoubleRadius;
    private RectF mRectf;
    private OnSkipListener mOnSkipListener;
    private Handler mHandler;

    public SkipView(Context context) {
        super(context);
    }

    public SkipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //文本的画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(TEXT_SIZE);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);

        //中间圆的画笔
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.GRAY);
        mCirclePaint.setAntiAlias(true);

        //外部圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setColor(Color.RED);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(ARC_WIDTH);

        //文字的宽度
        //文字距离中间圆边框的边距
        //外部圆弧的笔画宽度
        mMeasureTextWidth = mTextPaint.measureText("跳过");
        //计算中间圆的直径
        mCircleDoubleRadius = mMeasureTextWidth + TEXT_MARGIN * 2;
        //计算外部的圆弧的直径
        mArcDoubleRadius = mCircleDoubleRadius + 2 * ARC_WIDTH;
        //准备一个矩形,画外部圆弧时用
        mRectf = new RectF(0+ARC_WIDTH/2, 0+ARC_WIDTH/2,
                mArcDoubleRadius-ARC_WIDTH/2, mArcDoubleRadius-ARC_WIDTH/2);
        //接收到会更新一下角度,重绘
        //当角度百分之百了,就不再发消息了
        //继续发延时消息给自己
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //接收到会更新一下角度,重绘
                currentTime += REFRESH_TIME;
                //当角度百分之百了,就不再发消息了
                if (currentTime>totalShowTime){
                    if (mOnSkipListener!=null){
                        mOnSkipListener.onSkip();
                    }
                    return;
                }
                invalidate();
                //继续发延时消息给自己
                this.sendEmptyMessageDelayed(0,REFRESH_TIME);
            }

        };

    }
    public static final int REFRESH_TIME = 100;
    public float currentTime = 0;
    public float totalShowTime = 2500;

    public void start(){
        mHandler.sendEmptyMessageDelayed(0,REFRESH_TIME);
    }
    public void stop(){
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                //让handler的还未处理的消息被移除
                mHandler.removeCallbacksAndMessages(null);
                setAlpha(1f);
                //跳转页面
                /*
                Toast.makeText(getContext()
                        , "跳转A页面", Toast.LENGTH_SHORT).show();*/
                //在哪个页面中用到了这个控件,就在哪个页面写具体的逻辑
                if(mOnSkipListener!=null){
                    mOnSkipListener.onSkip();
                }
                break;
        }
        return true;
    }

    //1 定义一个接口
    public interface OnSkipListener{
        void onSkip();
    }


    //2 来一个Set方法,传递接口实例过来
    public void setOnSkipListener(OnSkipListener listener){
        mOnSkipListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制三个东西
        //外部圆弧
        canvas.save();
        canvas.rotate(-90,getMeasuredWidth()/2,getMeasuredHeight()/2);
        float angle = currentTime/totalShowTime*360;
        canvas.drawArc(mRectf,0,angle,false,mArcPaint);
        canvas.restore();
        //中间的圆
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,mCircleDoubleRadius/2,mCirclePaint);
        //内部的文字
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        Log.e(getClass().getSimpleName()+" xmg", "onDraw: "+" top "+top
                +" ascent "+ascent+" descent "+descent+" bottom "+bottom);
        //文字居中的公式
        float baseLine = getMeasuredHeight()/2-(top+bottom)/2;

        canvas.drawText("跳过",getMeasuredWidth()/2-mMeasureTextWidth/2,
                baseLine,mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //大小通过计算外部圆弧的直径
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST){
            widthSize = (int) mArcDoubleRadius;
        } if (heightMode == MeasureSpec.AT_MOST){
            heightSize = (int) mArcDoubleRadius;
        }setMeasuredDimension(widthSize,heightSize);
    }
}
