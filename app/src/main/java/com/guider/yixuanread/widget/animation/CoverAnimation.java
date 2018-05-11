package com.guider.yixuanread.widget.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.Scroller;

/**
 * Created by zt on 2018/5/10.
 */

public class CoverAnimation extends AnimationProvider {
    public CoverAnimation(Bitmap mCurPageBitmap, Bitmap mNextPageBitmap, int width, int height) {
        super(mCurPageBitmap, mNextPageBitmap, width, height);
    }

    @Override
    public void drawMove(Canvas canvas) {

    }

    @Override
    public void drawStatic(Canvas canvas) {

    }

    @Override
    public void startAnimation(Scroller scroller) {

    }
}
