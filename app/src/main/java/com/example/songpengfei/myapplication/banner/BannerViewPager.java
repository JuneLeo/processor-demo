package com.example.songpengfei.myapplication.banner;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;



public class BannerViewPager extends ViewPager {
    private static final String FIELD_SCROLLER = "mScroller";
    private boolean mScrollable = true;

    float xDistance = 0f;
    float yDistance = 0f;
    float xLast = 0f;
    float yLast = 0f;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAllowUserScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            if(getChildCount() <= 1){
                return super.onInterceptTouchEvent(ev);
            }
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDistance = yDistance = 0f;
                    xLast = ev.getX();
                    yLast = ev.getY();
                    getParent().requestDisallowInterceptTouchEvent(true);
                    try {
                        super.onTouchEvent(ev);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                case MotionEvent.ACTION_MOVE:
                    final float curX = ev.getX();
                    final float curY = ev.getY();

                    xDistance += Math.abs(curX - xLast);
                    yDistance += Math.abs(curY - yLast);
                    xLast = curX;
                    yLast = curY;
                    int disallowOffset = dip2px(5);
                    if (xDistance - disallowOffset > yDistance) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }else if(yDistance - disallowOffset > xDistance){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    try {
                        super.onTouchEvent(ev);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
