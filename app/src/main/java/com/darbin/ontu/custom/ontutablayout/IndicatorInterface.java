package com.darbin.ontu.custom.ontutablayout;

import android.graphics.Canvas;

import androidx.annotation.ColorInt;

/**
 * Created by Aakib
 * Date : 07/July/2021
 * Project : Ontu
 */

public interface IndicatorInterface {

    long DEFAULT_DURATION = 500;


    void setSelectedTabIndicatorColor(@ColorInt int color);



    void setSelectedTabIndicatorHeight(int height);


    void setIntValues(int startXLeft, int endXLeft,
                      int startXCenter, int endXCenter,
                      int startXRight, int endXRight);

    void setCurrentPlayTime(long currentPlayTime);


    void draw(Canvas canvas);

    long getDuration();
}