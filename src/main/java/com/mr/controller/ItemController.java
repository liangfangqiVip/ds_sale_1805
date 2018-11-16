package com.mr.controller;

import com.mr.model.TMallSku;
import com.mr.model.TMallSkuItemVO;
import com.mr.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by LFQ on 2018/11/7.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("toItemPage")
    public String toItemPage(Integer skuId, Integer spuId, ModelMap map){
        //根据supId查询sku的数据
        List<TMallSku> skuList=itemService.querySkuBySpuId(spuId);
        //根据skpId查询sku的整个数据
        TMallSkuItemVO itemvo=itemService.querySkuBySkuId(skuId);

        map.put("skuList",skuList);
        map.put("itemvo",itemvo);
        return "item";
    }
}
