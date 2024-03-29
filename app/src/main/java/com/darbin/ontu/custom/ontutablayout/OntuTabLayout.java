package com.darbin.ontu.custom.ontutablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.darbin.ontu.R;
import com.darbin.ontu.enums.IndicatorType;
import com.darbin.ontu.utils.DeviceUtil;
import com.darbin.ontu.utils.DeviceUtils;
import com.google.android.material.tabs.TabLayout;

/**
 * Created by Aakib
 * Date : 07/July/2021
 * Project : Ontu
 */

public class OntuTabLayout extends TabLayout implements ViewPager.OnPageChangeListener {

    private static final int DEFAULT_HEIGHT_DP = 6;

    private int mIndicatorColor;
    private int mIndicatorHeight;

    private int mCurrentPosition;

    private boolean mCenterAlign;

    private LinearLayout mTabStrip;

    private IndicatorType mAnimatedIndicatorType;
    private IndicatorInterface mAnimatedIndicator;

    private int mTempPosition, mTempPositionOffsetPixels;
    private float mTempPositionOffset;

    public OntuTabLayout(Context context) {
        this(context, null);
    }

    public OntuTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OntuTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        super.setSelectedTabIndicatorHeight(0);

        mTabStrip = (LinearLayout) super.getChildAt(0);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.OntuTabLayout);

        this.mIndicatorHeight = ta.getDimensionPixelSize(R.styleable.OntuTabLayout_IndicatorHeight, DeviceUtil.dpToPx(DEFAULT_HEIGHT_DP));
        this.mIndicatorColor = ta.getColor(R.styleable.OntuTabLayout_IndicatorColor, Color.WHITE);
        this.mCenterAlign = ta.getBoolean(R.styleable.OntuTabLayout_CenterAlign, false);
        this.mAnimatedIndicatorType = IndicatorType.values()[ta.getInt(R.styleable.OntuTabLayout_AnimatedIndicator, 0)];

        ta.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (mCenterAlign) {
            View firstTab = ((ViewGroup) getChildAt(0)).getChildAt(0);
            View lastTab = ((ViewGroup) getChildAt(0)).getChildAt(((ViewGroup) getChildAt(0)).getChildCount() - 1);
            ViewCompat.setPaddingRelative(getChildAt(0),
                    (getWidth() / 2) - (firstTab.getWidth() / 2),
                    0,
                    (getWidth() / 2) - (lastTab.getWidth() / 2),
                    0);
        }

        if (mAnimatedIndicator == null) {
            setupAnimatedIndicator();
        }

        onPageScrolled(mTempPosition, mTempPositionOffset, mTempPositionOffsetPixels);
    }

    private void setupAnimatedIndicator() {
        switch (mAnimatedIndicatorType) {
            case LINE_MOVE:
                setAnimatedIndicator(new LineMoveIndicator(this));
                break;
        }
    }

    public void setAnimatedIndicator(IndicatorInterface animatedIndicator) {
        this.mAnimatedIndicator = animatedIndicator;

        animatedIndicator.setSelectedTabIndicatorColor(mIndicatorColor);
        animatedIndicator.setSelectedTabIndicatorHeight(mIndicatorHeight);

        invalidate();
    }

    @Override
    public void setSelectedTabIndicatorColor(@ColorInt int color) {
        this.mIndicatorColor = color;
        if (mAnimatedIndicator != null) {
            mAnimatedIndicator.setSelectedTabIndicatorColor(color);

            invalidate();
        }
    }

    @Override
    public void setSelectedTabIndicatorHeight(int height) {
        this.mIndicatorHeight = height;
        if (mAnimatedIndicator != null) {
            mAnimatedIndicator.setSelectedTabIndicatorHeight(height);

            invalidate();
        }

    }

    public void setCenterAlign(boolean centerAlign) {
        this.mCenterAlign = centerAlign;

        requestLayout();
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        this.setupWithViewPager(viewPager, true);
    }

    @Override
    public void setupWithViewPager(@Nullable final ViewPager viewPager, boolean autoRefresh) {
        super.setupWithViewPager(viewPager, autoRefresh);

        //TODO
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this);
            viewPager.addOnPageChangeListener(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (mAnimatedIndicator != null)
            mAnimatedIndicator.draw(canvas);

    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset,
                               final int positionOffsetPixels) {
        this.mTempPosition = position;
        this.mTempPositionOffset = positionOffset;
        this.mTempPositionOffsetPixels = positionOffsetPixels;

        if ((position > mCurrentPosition) || (position + 1 < mCurrentPosition)) {
            mCurrentPosition = position;
        }

        int mStartXLeft, mStartXCenter, mStartXRight, mEndXLeft, mEndXCenter, mEndXRight;

        if (position != mCurrentPosition) {

            mStartXLeft = (int) getChildXLeft(mCurrentPosition);
            mStartXCenter = (int) getChildXCenter(mCurrentPosition);
            mStartXRight = (int) getChildXRight(mCurrentPosition);

            mEndXLeft = (int) getChildXLeft(position);
            mEndXRight = (int) getChildXRight(position);
            mEndXCenter = (int) getChildXCenter(position);

            if (mAnimatedIndicator != null) {
                mAnimatedIndicator.setIntValues(mStartXLeft, mEndXLeft,
                        mStartXCenter, mEndXCenter,
                        mStartXRight, mEndXRight);
                mAnimatedIndicator.setCurrentPlayTime((long) ((1 - positionOffset) * (int) mAnimatedIndicator.getDuration()));
            }

        } else {

            mStartXLeft = (int) getChildXLeft(mCurrentPosition);
            mStartXCenter = (int) getChildXCenter(mCurrentPosition);
            mStartXRight = (int) getChildXRight(mCurrentPosition);

            if (mTabStrip.getChildAt(position + 1) != null) {

                mEndXLeft = (int) getChildXLeft(position + 1);
                mEndXCenter = (int) getChildXCenter(position + 1);
                mEndXRight = (int) getChildXRight(position + 1);

            } else {
                mEndXLeft = (int) getChildXLeft(position);
                mEndXCenter = (int) getChildXCenter(position);
                mEndXRight = (int) getChildXRight(position);
            }

            if (mAnimatedIndicator != null) {
                mAnimatedIndicator.setIntValues(mStartXLeft, mEndXLeft,
                        mStartXCenter, mEndXCenter,
                        mStartXRight, mEndXRight);
                mAnimatedIndicator.setCurrentPlayTime((long) (positionOffset * (int) mAnimatedIndicator.getDuration()));
            }

        }

        if (positionOffset == 0) {
            mCurrentPosition = position;
        }

    }

    @Override
    public void onPageSelected(final int position) {
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public float getChildXLeft(int position) {
        View tab = mTabStrip.getChildAt(position);
        if (tab != null)
            return tab.getX();
        else
            return 0;
    }

    public float getChildXCenter(int position) {
        View tab = mTabStrip.getChildAt(position);
        if (tab != null) {
            int width = tab.getWidth() == 0 ? tab.getMeasuredWidth() : tab.getWidth();
            return tab.getX() + width / 2;
        } else
            return 0;
    }

    public float getChildXRight(int position) {
        View tab = mTabStrip.getChildAt(position);
        if (tab != null) {
            int width = tab.getWidth() == 0 ? tab.getMeasuredWidth() : tab.getWidth();
            return tab.getX() + width;
        } else
            return 0;
    }

    public IndicatorInterface getAnimatedIndicator() {
        return mAnimatedIndicator;
    }
}

