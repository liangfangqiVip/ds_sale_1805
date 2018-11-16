package com.mr.controller;

import com.mr.model.*;
import com.mr.service.AttrService;
import com.mr.service.SkuService;
import com.mr.util.MyHttpclient;
import com.mr.util.MyJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/6.
 */
@Controller
public class ListController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private SkuService skuService;

    @RequestMapping("toListPage")
    public String toListPage(Integer flbh2 , ModelMap map){
        //根据flbh2查询属性
        List<TMallValueVO> attrList=attrService.queryAttrList(flbh2);
        //查询SKU集合
        List<OBJECTSku> skuList=skuService.queryListSku(flbh2);

        map.put("attrList",attrList);
        map.put("skuList",skuList);
        map.put("flbh2",flbh2);
        return "list";
    }

    @RequestMapping("listSkuByAttr")
    public String listSkuByAttr(TMallSkuAttrValueVO attrValueList, Integer flbh2, ModelMap map){
        List<OBJECTSku> list=skuService.listSkuByAttr(attrValueList,flbh2);
        map.put("skuList",list);
        return "list-Sbox";
    }

    @RequestMapping("solrByskuMch")
    public String solrByskuMch(String key,ModelMap map){
        Map<String,String>keyMap=new HashMap<String,String>();
        keyMap.put("key",key);
        String s = MyHttpclient.doGet("http://localhost:8186/querySkuListByName.do", keyMap);
        List<TMallSku> skuList = MyJsonUtil.jsonToList(s, TMallSku.class);
        map.put("skuList",skuList);
        map.put("key",key);
        return "list";
    }
}
