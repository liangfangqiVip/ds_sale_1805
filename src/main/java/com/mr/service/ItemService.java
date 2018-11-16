package com.mr.service;

import com.mr.model.TMallSku;
import com.mr.model.TMallSkuItemVO;

import java.util.List;

/**
 * Created by LFQ on 2018/11/7.
 */
public interface ItemService {

    List<TMallSku> querySkuBySpuId(Integer supId);

    TMallSkuItemVO querySkuBySkuId(Integer skuId);
}
