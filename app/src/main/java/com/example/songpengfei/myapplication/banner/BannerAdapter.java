package com.example.songpengfei.myapplication.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BannerAdapter<T extends BannerModel> extends PagerAdapter {
    private List<T> data;

    private Context context;
    private LayoutInflater inflater;
    private Banner cmBanner;
    private ViewHolderPool holderPools = new ViewHolderPool();

    protected abstract <T extends BannerModel> View onCreateView(LayoutInflater inflater, ViewGroup parent,T t);

    protected abstract <T extends BannerModel> BannerViewHolder onCreateHolder(View itemView, T t);

    protected abstract <T extends BannerModel> void onBindView(BannerViewHolder viewHolder, T t);

    public BannerAdapter(Context context) {
        this(context, null);
    }

    public BannerAdapter(Context context, List<T> list) {
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            this.data.addAll(list);
        }
    }

    public void setBanner(Banner banner){
        cmBanner = banner;
        configBanner();
    }

    protected Context getContext() {
        return context;
    }

    protected T getItem(int position) {
        return data.get(position);
    }

    public void update(List<T> list) {
        if (list == null) {
            return;
        }
        this.data.clear();
        this.data.addAll(list);
        this.notifyDataSetChanged();
        configBanner();
    }

    public List<T> getData() {
        return this.data;
    }

    @Override
    public int getCount() {
        int size = data.size();
        if (!isLoop()) {
            return size;
        } else {
            if (size <= 1) {
                return size;
            }
            return Integer.MAX_VALUE - size;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return getView(position, container);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        BannerViewHolder vh = (BannerViewHolder) ((View) object).getTag();
        int temp = getPositionOffset(position);
        holderPools.put(data.get(temp).getClass(),vh);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;//fix a bug, replace one web url to another web url
    }

    private int getPositionOffset(int position) {
        if (!isLoop()) {
            return position;
        }
        int size = data.size();
        if (size == 0) {
            return 0;
        }
        return position % size;
    }

    private View getView(int position, ViewGroup parent) {
        View child;
        int temp = getPositionOffset(position);
        BannerModel model = data.get(temp);
        if (!holderPools.contains(model.getClass())){
            child = onCreateView(inflater, parent,model);
            BannerViewHolder viewHolder = onCreateHolder(child,model);
            child.setTag(viewHolder);
        }else{
            BannerViewHolder vh = holderPools.getVH(model.getClass());
            child = vh.itemView;
        }

        parent.addView(child);

        return child;
    }


    private void configBanner() {
        this.cmBanner.setCanLoop(data.size() > 1);
        if (cmBanner.isLoop()) {
            cmBanner.startLoop();
        }
    }

    private boolean isLoop() {
        if (cmBanner == null) {
            return false;
        }
        return cmBanner.isLoop();
    }

    private static class ViewHolderPool{
        public List<BannerViewHolder> pools;
        public List<String> models;

        public ViewHolderPool() {
            pools = new ArrayList<>();
            models = new ArrayList<>();
        }

        public boolean contains(@NonNull Class clz){
            return models.contains(clz.getName());
        }

        public synchronized BannerViewHolder getVH(Class clz){
            int i = models.indexOf(clz.getName());
            if (i == -1){
                return null;
            }
            models.remove(i);
            return pools.remove(i);
        }

        public synchronized void put(Class clz,BannerViewHolder vh){
            models.add(clz.getName());
            pools.add(vh);
        }
    }
}
