package com.example.a15017206.adelineapp2;

import android.widget.ImageView;

/**
 * Created by 15017206 on 12/12/2017.
 */

class SearchResult {
    private int imageView;
    private String tvTitle;
    private String tvSubtitle;
    private String tvPrice;
    private String tvShipping;

    public SearchResult(int imageView, String tvTitle, String tvSubtitle, String tvPrice, String tvShipping) {
        this.imageView = imageView;
        this.tvTitle = tvTitle;
        this.tvSubtitle = tvSubtitle;
        this.tvPrice = tvPrice;
        this.tvShipping = tvShipping;
    }

    public SearchResult() {
    }

    public int getImageView() {
        return imageView;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public String getTvSubtitle() {
        return tvSubtitle;
    }

    public String getTvPrice() {
        return tvPrice;
    }

    public String getTvShipping() {
        return tvShipping;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public void setTvSubtitle(String tvSubtitle) {
        this.tvSubtitle = tvSubtitle;
    }

    public void setTvPrice(String tvPrice) {
        this.tvPrice = tvPrice;
    }

    public void setTvShipping(String tvShipping) {
        this.tvShipping = tvShipping;
    }
}
