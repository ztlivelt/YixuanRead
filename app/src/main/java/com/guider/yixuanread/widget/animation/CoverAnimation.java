package com.guider.yixuanread.widget.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.widget.Scroller;

/**
 * Created by zt on 2018/5/10.
 */

public class CoverAnimation extends AnimationProvider {
    private Rect mSrcRect;
    private Rect mDestRect;
    private GradientDrawable mBackShadowDrawableLR;
    public CoverAnimation(Bitmap mCurPageBitmap, Bitmap mNextPageBitmap, int width, int height) {
        super(mCurPageBitmap, mNextPageBitmap, width, height);
        mSrcRect = new Rect(0, 0, mScreenWidth, mScreenHeight);
        mDestRect = new Rect(0, 0, mScreenWidth, mScreenHeight);
        int[] mBackShadowColors = new int[] { 0x66000000,0x00000000};
        mBackShadowDrawableLR = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors);
        mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    }

    @Override
    public void drawMove(Canvas canvas) {
        switch (getDirection()){
            case next:
                int dis = (int) (mScreenWidth - myStartX + mTouch.x);
                if (dis > mScreenWidth){
                    dis = mScreenWidth;
                }
                //计算bitmap截取的区域
                mSrcRect.left = mScreenWidth - dis;
                //计算bitmap在canvas显示的区域
                mDestRect.right = dis;
                canvas.drawBitmap(mNextPageBitmap,0,0,null);
                canvas.drawBitmap(mCurPageBitmap,mSrcRect,mDestRect,null);
                addShadow(dis,canvas);
                break;
            default:
                mSrcRect.left = (int) (mScreenWidth - mTouch.x);
                mDestRect.right = (int) mTouch.x;
                canvas.drawBitmap(mCurPageBitmap,0,0,null);
                canvas.drawBitmap(mNextPageBitmap,mSrcRect,mDestRect,null);
                addShadow((int) mTouch.x,canvas);
        }
    }

    @Override
    public void drawStatic(Canvas canvas) {
        if (getCancel()){
            canvas.drawBitmap(mCurPageBitmap, 0, 0, null);
        }else {
            canvas.drawBitmap(mNextPageBitmap, 0, 0, null);
        }
    }

    @Override
    public void startAnimation(Scroller scroller) {
        int dx = 0;
        if (getDirection().equals(Direction.next)){
            if (getCancel()){
                int dis = (int) ((mScreenWidth - myStartX) + mTouch.x);
                if (dis > mScreenWidth){
                    dis = mScreenWidth;
                }
                dx = mScreenWidth - dis;
            }else{
//                dx = (int) - (mTouch.x + myStartX);
                dx = (int) - (mTouch.x + (mScreenWidth - myStartX));
            }
        }else{
            if (getCancel()){
                dx = (int) - mTouch.x;
            }else{
                dx = (int) (mScreenWidth - mTouch.x);
            }
        }
        //滑动速度保持一致
        int duration =  (400 * Math.abs(dx)) / mScreenWidth;
        scroller.startScroll((int) mTouch.x, 0, dx, 0, duration);

    }
    //添加阴影
    public void addShadow(int left,Canvas canvas) {
        mBackShadowDrawableLR.setBounds(left, 0, left + 30 , mScreenHeight);
        mBackShadowDrawableLR.draw(canvas);
    }
}
