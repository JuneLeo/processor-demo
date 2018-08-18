package com.example.songpengfei.myapplication.banner;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.cleanmaster.mguard.R;

import java.util.List;

public class CustomAdapter extends BannerAdapter<BannerModel> {


    public CustomAdapter(Context context) {
        super(context);
        init();
    }

    private void init() {
        BannerModel one = new OneModel();
        BannerModel two = new TwoModel();
        getData().add(one);
        getData().add(two);
    }

    public CustomAdapter(Context context, List<BannerModel> list) {
        super(context, list);
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup parent, BannerModel bannerModel) {
        Log.d("adapter","build "+bannerModel.getClass().getName());
        if (bannerModel instanceof OneModel){
            return inflater.inflate(R.layout.item_banner_main_earn_entrance,parent,false);
        }else if (bannerModel instanceof TwoModel){
            return inflater.inflate(R.layout.item_banner_main_earn_lottery,parent,false);
        }

        return null;
    }

    @Override
    protected BannerViewHolder onCreateHolder(View itemView, BannerModel bannerModel) {
        if (itemView == null){
            return null;
        }
        if (bannerModel instanceof OneModel){
            return new OneVH(itemView);
        }else if (bannerModel instanceof TwoModel){
            return new TwoVH(itemView);
        }
        return null;
    }

    @Override
    protected void onBindView(BannerViewHolder viewHolder, BannerModel bannerModel) {
        if (viewHolder == null){
            return;
        }


    }

    private static class OneVH extends BannerViewHolder {

        public OneVH(View itemView) {
            super(itemView);
        }
    }

    private static class TwoVH extends BannerViewHolder {

        public TwoVH(View itemView) {
            super(itemView);
        }
    }

    public static class OneModel extends BannerModel {

    }

    public static class TwoModel extends BannerModel {
    }

}
