package com.mr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LFQ on 2018/11/5.
 */
@Controller
public class IndexController {

    @RequestMapping("toMainPage")
    public String toMainPage(){
        return "index";
    }
    @RequestMapping("toLoginPage")
    public String toLoginPage(String url, ModelMap map){
        map.put("url",url);
        return "login";
    }
    @RequestMapping("toRegPage")
    public String toRegPage(){
        return "reg";
    }
}
