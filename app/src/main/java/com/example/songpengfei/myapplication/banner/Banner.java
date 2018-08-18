package com.example.songpengfei.myapplication.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

public class Banner extends RelativeLayout {

    private static final String TAG = Banner.class.getSimpleName();

    private static final int SCROLL_WHAT = 100000;
    private static final int DEFAULT_LOOP_DELAY = 3000;

    private BannerViewPager bannerViewPager;
    private BannerAdapter bannerAdapter;
    private LoopHandler loopHandler;
    private boolean canLoop;
    private boolean isLooping;
    private boolean loop;
    private BannerIndicator mBannerIndicator;


    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initViews(context);

    }

    private void init() {
        this.loopHandler = new LoopHandler(this);
        this.canLoop = false;
        this.isLooping = false;
    }

    private void initViews(Context context) {
        LayoutParams params0 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        bannerViewPager = new BannerViewPager(context);
        bannerViewPager.setLayoutParams(params0);
        addView(bannerViewPager);
    }

    public void setIndicator(BannerIndicator indicator){
        mBannerIndicator = indicator;
        if (bannerAdapter == null){
            return;
        }
        removeView((View) indicator);
        indicator.bindBanner(this);
    }

    public void setAdapter(BannerAdapter adapter) {
        if (adapter == null) {
            return;
        }
        if (bannerAdapter != null) {
            throw new IllegalStateException("DdBanner set adapter only once");
        }
        bannerAdapter = adapter;
        bannerAdapter.setBanner(this);
        bannerViewPager.setAdapter(bannerAdapter);
        if (mBannerIndicator!=null){
            setIndicator(mBannerIndicator);
        }
        if (isLoop()) {
            startLoop();
        }
    }

    public BannerViewPager getViewPager() {
        return bannerViewPager;
    }

    public BannerAdapter getAdapter() {
        return bannerAdapter;
    }

    void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isLooping() {
        return isLooping;
    }

    void setIsLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }

    public void startLoop() {
        if (isLoop() && isCanLoop() && !isLooping()) {
            setIsLooping(true);
            sendLoopMessage();
        }
    }

    public void stopLoop() {
        if (isLoop() && isCanLoop() && isLooping()) {
            setIsLooping(false);
            removeLoopMessage();
        }
    }

    private void scrollToNext() {
        int index = bannerViewPager.getCurrentItem();
        bannerViewPager.setCurrentItem(index + 1);
    }

    private void sendLoopMessage() {
        loopHandler.sendEmptyMessageDelayed(SCROLL_WHAT, DEFAULT_LOOP_DELAY);
    }

    private void removeLoopMessage() {
        loopHandler.removeMessages(SCROLL_WHAT);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopLoop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startLoop();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startLoop();
        } else if (visibility == INVISIBLE) {
            stopLoop();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (canLoop) {
            stopLoop();
        }
    }

    private static class LoopHandler extends Handler {

        private final WeakReference<Banner> reference;

        public LoopHandler(Banner banner) {
            this.reference = new WeakReference<>(banner);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCROLL_WHAT:
                    Banner banner = this.reference.get();
                    if (banner != null) {
                        banner.scrollToNext();
                        banner.sendLoopMessage();
                    }
                default:
                    break;
            }
        }
    }
}
