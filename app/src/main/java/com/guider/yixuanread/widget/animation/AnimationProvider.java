package com.guider.yixuanread.widget.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.widget.Scroller;

/**
 * Created by zt on 2018/5/10.
 */

public abstract class AnimationProvider {
    public static enum Direction {
        none(true),
        next(true),
        pre(true),
        up(false),
        down(false);
        public final boolean isHorizontal;

        Direction(boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
        }
    }

    public static enum Animation {
        none,
        curl,
        slide,
        shift;
    }

    protected Bitmap mCurPageBitmap;
    protected Bitmap mNextPageBitmap;
    protected float myStartX;
    protected float myStartY;
    protected int myEndX;
    protected int myEndY;
    protected Direction myDirection;
    protected float mySpeed;

    protected int mScreenWidth;
    protected int mScreenHeight;

    protected PointF mTouch = new PointF(); //拖拽点
    private Direction direction = Direction.none;
    private boolean isCancel = false;

    public AnimationProvider(Bitmap mCurPageBitmap, Bitmap mNextPageBitmap, int width, int height) {
        this.mCurPageBitmap = mCurPageBitmap;
        this.mNextPageBitmap = mNextPageBitmap;
        this.mScreenWidth = width;
        this.mScreenHeight = height;
    }

    /**
     * 绘制滑动页面
     * @param canvas
     */
    public abstract void drawMove(Canvas canvas);

    /**
     * 绘制不滑动页面
     * @param canvas
     */
    public abstract void drawStatic(Canvas canvas);

    /**
     * 开始动画
     * @param scroller
     */
    public abstract void startAnimation(Scroller scroller);



    /**
     * 开始拖拽点
     * @param x
     * @param y
     */
    public void setStartPoint(float x,float y){
        myStartX = x;
        myStartY = y;
    }

    /**
     * 设置拖拽点
     * @param x
     * @param y
     */
    public void setTouchPoint(float x,float y){
        mTouch.x = x;
        mTouch.y = y;
    }

    /**
     * 设置方向
     * @param direction
     */
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    public Direction getDirection(){
        return direction;
    }
    public void setCancel(boolean isCancel){
        this.isCancel = isCancel;
    }
    public boolean getCancel(){
        return isCancel;
    }
}
