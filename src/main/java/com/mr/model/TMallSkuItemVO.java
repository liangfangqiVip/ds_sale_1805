package com.mr.model;

import java.util.List;

/**
 * Created by LFQ on 2018/11/7.
 */
public class TMallSkuItemVO extends TMallSku {

    private List<TMallProductImage> imgList;

    private List<OBJECTAVOV> avList;

    public List<TMallProductImage> getImgList() {
        return imgList;
    }

    public void setImgList(List<TMallProductImage> imgList) {
        this.imgList = imgList;
    }

    public List<OBJECTAVOV> getAvList() {
        return avList;
    }

    public void setAvList(List<OBJECTAVOV> avList) {
        this.avList = avList;
    }
}
