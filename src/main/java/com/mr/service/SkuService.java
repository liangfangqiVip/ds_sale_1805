package com.mr.service;

import com.mr.model.OBJECTSku;
import com.mr.model.TMallSkuAttrValueVO;

import java.util.List;

/**
 * Created by LFQ on 2018/11/6.
 */
public interface SkuService {

    List<OBJECTSku> queryListSku(Integer flbh2);

    List<OBJECTSku> listSkuByAttr(TMallSkuAttrValueVO attrValueList, Integer flbh2);


}

