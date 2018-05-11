package com.guider.yixuanread.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.guider.yixuanread.base.Config;
import com.guider.yixuanread.widget.animation.AnimationProvider;
import com.guider.yixuanread.widget.animation.CoverAnimation;
import com.guider.yixuanread.widget.animation.NoneAnimation;
import com.guider.yixuanread.widget.animation.SimulationAnimation;
import com.guider.yixuanread.widget.animation.SlideAnimation;

/**
 * Created by zt on 2018/5/9.
 */

public class PageWidget extends View {
    private final static String TAG = "PageWidget";
    private int mScreenWidth = 0; //屏幕宽度
    private int mScreenHeight = 0; //屏幕高度
    private Context mContext;

    private boolean isMove = false; //是否移动了
    private boolean isNext = false; //是否翻到下一页
    private boolean cancelPage = false; //是否取消翻页
    private boolean noNext = false; //是否没有下一页或者上一页
    private int downX = 0;
    private int downY = 0;

    private int moveX = 0;
    private int moveY = 0;

    private boolean isRuning = false; //翻页动画是否在执行

    Bitmap mCurPageBitmap = null;
    Bitmap mNextPageBitmap = null;

    private AnimationProvider mAnimationProvider;
    Scroller mScroller;
    private int mBgColor = 0xFFCEC29C;
    private TouchListener mTouchListener;
    public PageWidget(Context context) {
        this(context,null);
    }

    public PageWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPage();
        mScroller = new Scroller(getContext(),new LinearInterpolator());
        mAnimationProvider = new NoneAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
    }
    private void initPage(){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        mCurPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.RGB_565);      //android:LargeHeap=true  use in  manifest application
        mNextPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.RGB_565);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawColor(mBgColor);
        if (isRuning){
            mAnimationProvider.drawMove(canvas);
        } else {
            mAnimationProvider.drawStatic(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //1.
        int x = (int) event.getX();
        int y = (int) event.getY();
        mAnimationProvider.setTouchPoint(x,y);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                moveX = 0;
                moveY = 0;
                isMove = false;
                noNext = false;
                isNext = false;
                isRuning = false;
                mAnimationProvider.setStartPoint(downX,downY);
                abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (!isMove){
                    isMove = Math.abs(downX - x) > slop || Math.abs(downY - y) > slop;
                }
                if (isMove){
                    isMove = true;
                    if (moveX == 0 && moveY == 0){
                        //判断翻得是上一页还是下一页
                        if (x - downX > 0){
                            isNext = false;
                        } else {
                            isNext = true;
                        }
                        cancelPage = false;
                        if (isNext){
                            boolean isNext = mTouchListener.nextPage();
                            mAnimationProvider.setDirection(AnimationProvider.Direction.next);
                            if (!isNext){
                                noNext = true;
                                return true;
                            }
                        } else {
                            boolean isPre = mTouchListener.prePage();
                            mAnimationProvider.setDirection(AnimationProvider.Direction.pre);
                            if (!isPre){
                                noNext = true;
                                return true;
                            }
                        }
                    } else {
                        //判断是否取消翻页
                        if (isNext){
                            if (x - moveX > 0){
                                cancelPage = true;
                                mAnimationProvider.setCancel(true);
                            } else {
                                cancelPage = false;
                                mAnimationProvider.setCancel(false);
                            }
                        }else {
                            if (x - moveX < 0){
                                cancelPage = true;
                                mAnimationProvider.setCancel(true);
                            }else {
                                cancelPage = false;
                                mAnimationProvider.setCancel(false);
                            }
                        }
                    }
                    moveX = x;
                    moveY = y;
                    this.postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isMove){
                    cancelPage = false;
                    //是否点击了中间
                    if (downX > mScreenWidth / 5 && downX < mScreenWidth * 4 / 5
                            && downY > mScreenHeight / 3 && downY < mScreenHeight * 2 / 3 ){
                        if (mTouchListener != null){
                            mTouchListener.center();
                        }
                    } else if ( x < mScreenWidth / 2){
                        isNext = false;
                    } else {
                        isNext = true;
                    }
                    if (isNext){
                        boolean isNext = mTouchListener.nextPage();
                        mAnimationProvider.setDirection(AnimationProvider.Direction.next);
                        if (!isNext) {
                            return true;
                        }
                    } else {
                        boolean isPre = mTouchListener.prePage();
                        mAnimationProvider.setDirection(AnimationProvider.Direction.pre);
                        if (!isPre) {
                            return true;
                        }
                    }
                }
                if (cancelPage && mTouchListener != null){
                    mTouchListener.cancel();
                }
                if (!noNext){
                    isRuning = true;
                    mAnimationProvider.startAnimation(mScroller);
                    this.postInvalidate();
                }
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();
            mAnimationProvider.setTouchPoint(x,y);
            if (mScroller.getFinalX() == x && mScroller.getFinalY() == y){
                isRuning = false;
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    public void abortAnimation(){
        if (!mScroller.isFinished()){
            mScroller.abortAnimation();
            mAnimationProvider.setTouchPoint(mScroller.getFinalX(),mScroller.getFinalY());
            postInvalidate();
        }
    }
    public void setPageMode(int pageMode){
        switch (pageMode){
            case Config.PAGE_MODE_SIMULATION:
                mAnimationProvider = new SimulationAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
                break;
            case Config.PAGE_MODE_COVER:
                mAnimationProvider = new CoverAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
                break;
            case Config.PAGE_MODE_SLIDE:
                mAnimationProvider = new SlideAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
                break;
            case Config.PAGE_MODE_NONE:
                mAnimationProvider = new NoneAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
                break;
            default:
                mAnimationProvider = new NoneAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
        }
    }
    public Bitmap getCurPage(){
        return mCurPageBitmap;
    }

    public Bitmap getNextPage(){
        return mNextPageBitmap;
    }

    public void setBgColor(int color){
        mBgColor = color;
    }

    public boolean isRunning(){
        return isRuning;
    }

    public void setTouchListener(TouchListener mTouchListener){
        this.mTouchListener = mTouchListener;
    }

    public interface TouchListener{
        void center();
        boolean prePage();
        boolean nextPage();
        void cancel();
    }

}
