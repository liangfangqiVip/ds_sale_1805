package com.mr.service.impl;

import com.mr.mapper.ItemMapper;
import com.mr.model.TMallSku;
import com.mr.model.TMallSkuItemVO;
import com.mr.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LFQ on 2018/11/7.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<TMallSku> querySkuBySpuId(Integer supId) {
        return itemMapper.querySkuBySpuId(supId);
    }

    @Override
    public TMallSkuItemVO querySkuBySkuId(Integer skuId) {
        return itemMapper.querySkuBySkuId(skuId);
    }
}
