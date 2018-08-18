package com.example.songpengfei.myapplication.banner;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cleanmaster.mguard.R;


public class BannerIndicator extends LinearLayout {

    private ViewPager mViewpager;
    private Banner mBanner;

    private int mLastPosition = -1;

    public BannerIndicator(Context context) {
        super(context);
    }

    public BannerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bindBanner(Banner banner) {
        this.mBanner = banner;
        bindView(banner);
        mViewpager = banner.getViewPager();
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.addOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.getAdapter().registerDataSetObserver(mInternalDataSetObserver);
            mInternalPageChangeListener.onPageSelected(mViewpager.getCurrentItem());
        }
    }

    public void bindView(Banner banner){
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params1.bottomMargin = dip2px(8);
        setGravity(Gravity.CENTER);
        setLayoutParams(params1);
        setOrientation(LinearLayout.HORIZONTAL);
        banner.addView(this);
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (mViewpager.getAdapter() == null) {
                return;
            }

            int count = getViewPagerCount();
            if (count <= 1) {
                return;
            }

            position = position % count;

            if (mLastPosition >= 0) {
                View currentIndicator = getChildAt(mLastPosition);
                currentIndicator.setSelected(false);
            }

            View selectedIndicator = getChildAt(position);
            selectedIndicator.setSelected(true);

            mLastPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();

            int newCount = getViewPagerCount();

            int currentCount = getChildCount();

            if (newCount == currentCount) {  // No change
                return;
            } else if (mLastPosition < newCount) {
                mLastPosition = getViewPagerCurrentItem();
            } else {
                mLastPosition = 0;
            }

            createIndicators();
        }
    };

    /**
     * @deprecated User ViewPager addOnPageChangeListener
     */
    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (mViewpager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        mViewpager.removeOnPageChangeListener(onPageChangeListener);
        mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    private void createIndicators() {
        removeAllViews();
        int count = getViewPagerCount();

        if (count <= 1) {
            return;
        }

        int currentItem = getViewPagerCurrentItem();

        for (int i = 0; i < count; i++) {
            addIndicator(currentItem == i);
        }
    }

    protected void addIndicator(boolean selected){
        //构建view

        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(R.drawable.dd_indicator_state);
        Indicator.setSelected(selected);
        addView(Indicator, 20, 20);
        LayoutParams lp = (LayoutParams) Indicator.getLayoutParams();
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        Indicator.setLayoutParams(lp);
    }

    private int getViewPagerCount() {
        int count = mViewpager.getAdapter().getCount();
        if (!mBanner.isLoop()) {
            return count;
        } else {
            if (count == 1) {
                return 1;
            }
            return Integer.MAX_VALUE - count;
        }
    }

    private int getViewPagerCurrentItem() {
        int count = getViewPagerCount();
        return getViewPagerCurrentItem(count);
    }

    private int getViewPagerCurrentItem(int count) {
        if (!mBanner.isLoop()) {
            return mViewpager.getCurrentItem();
        }
        return mViewpager.getCurrentItem() % count;
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
