package com.mr.mapper;

import com.mr.model.OBJECTSku;

import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/6.
 */
public interface SkuMapper {

    List<OBJECTSku> queryListSku(Integer flbh2);

    List<OBJECTSku> listSkuByAttr(Map<String, Object> map);
}
