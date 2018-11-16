package com.mr.model;

import java.util.List;

/**
 * Created by LFQ on 2018/10/31.
 */
public class TMallSkuVo extends  TMallSku {

    private List <TMallSkuAttrValue> skuValue;

    public List<TMallSkuAttrValue> getSkuValue() {
        return skuValue;
    }

    public void setSkuValue(List<TMallSkuAttrValue> skuValue) {
        this.skuValue = skuValue;
    }
}
