package com.mr.model;

import java.util.List;

/**
 * Created by LFQ on 2018/10/30.
 */
public class TMallAttrVO {
    private List<TMallValueVO> attrList;

    public List<TMallValueVO> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<TMallValueVO> attrList) {
        this.attrList = attrList;
    }

    @Override
    public String toString() {
        return "TMallAttrVO{" +
                "attrList=" + attrList +
                '}';
    }
}
