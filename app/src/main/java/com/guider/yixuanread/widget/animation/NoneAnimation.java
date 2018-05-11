package com.guider.yixuanread.widget.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.Scroller;

/**
 * Created by zt on 2018/5/10.
 */

public class NoneAnimation extends AnimationProvider {
    public NoneAnimation(Bitmap mCurPageBitmap, Bitmap mNextPageBitmap, int width, int height) {
        super(mCurPageBitmap, mNextPageBitmap, width, height);
    }

    @Override
    public void drawMove(Canvas canvas) {
        if (getCancel()){
            canvas.drawBitmap(mCurPageBitmap,0,0,null);
        } else {
            canvas.drawBitmap(mNextPageBitmap,0,0,null);
        }
    }

    @Override
    public void drawStatic(Canvas canvas) {
        if (getCancel()){
            canvas.drawBitmap(mCurPageBitmap,0,0,null);
        } else {
            canvas.drawBitmap(mNextPageBitmap,0,0,null);
        }
    }

    @Override
    public void startAnimation(Scroller scroller) {

    }
}
