package com.example.songpengfei.myapplication.banner;
import android.view.View;

/**
 * Created by wsl on 16-4-13.
 */
public class BannerViewHolder {

    View itemView;
    int position;

    public BannerViewHolder(View itemView) {
        this.itemView = itemView;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}