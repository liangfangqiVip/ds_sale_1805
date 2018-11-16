package com.mr.model;

import java.util.List;

/**
 * Created by LFQ on 2018/10/30.
 */
public class TMallValueVO extends TMallAttr{

    private List<TMallValue> valueList;

    public List<TMallValue> getValueList() {
        return valueList;
    }

    public void setValueList(List<TMallValue> valueList) {
        this.valueList = valueList;
    }

    @Override
    public String toString() {
        return "OBJECTTMallAttr{" +
                "valueList=" + valueList +
                '}';
    }
}
