package com.mr.service.impl;

import com.mr.mapper.SkuMapper;
import com.mr.model.OBJECTSku;
import com.mr.model.TMallSkuAttrValueVO;
import com.mr.service.SkuService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/6.
 */
@Service
public class SkuServiceImpl implements SkuService{
    @Autowired
    private SkuMapper skuMapper;

    @Override
    public List<OBJECTSku> queryListSku(Integer flbh2) {
        return skuMapper.queryListSku(flbh2);
    }

    @Override
    public List<OBJECTSku> listSkuByAttr(TMallSkuAttrValueVO attrValueList, Integer flbh2) {
        Map<String,Object>map= new HashMap<>();
        map.put("attrValueList",attrValueList.getAttrValueList());
        map.put("flbh2",flbh2);
        return skuMapper.listSkuByAttr(map);
    }
}
