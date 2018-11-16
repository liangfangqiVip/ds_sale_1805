package com.mr.controller;

import com.mr.model.TMallShoppingCar;
import com.mr.model.TMallUserAccount;
import com.mr.service.CartService;
import com.mr.service.UserService;
import com.mr.util.MyCookieUtils;
import com.mr.util.MyJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/5.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("login")
    public String login(String url,
            String userName, String password, HttpSession session,
            HttpServletRequest request, HttpServletResponse response,
            @CookieValue(value = "cookieCartList",required = false) String cookieCartList){

        TMallUserAccount user=userService.login(userName,password);

        if(user==null){
            return "redirect:toLoginPage.do";
        }else{
            //放入session中
            session.setAttribute("user",user);
            String yhMch=user.getYhMch();

            //放入cookie中
            MyCookieUtils.setCookie(request,response,"yhMch",yhMch,60*60,true);

            //判断cookie中是否有数据
            if(!StringUtils.isBlank(cookieCartList)){//有数据

                //获取cooike中的购物车集合
                List<TMallShoppingCar> cartListCookie = MyJsonUtil.jsonToList(cookieCartList, TMallShoppingCar.class);

                //循环cookie
                for (int i = 0; i < cartListCookie.size(); i++) {
                    //根据当前对象的skuid和用户id查询数据
                    TMallShoppingCar cart = cartService.findCartBySkuIdAndUserId(cartListCookie.get(i).getSkuId(),user.getId());
                    //判断cookie中的数据是否与存在于数据库
                    //如果数据库中存在则数据存在
                    if(cart != null){//数据库在存在则更新
                        Map<String, Object> cartMap = new HashMap<>();
                        cartListCookie.get(i).setTjshl(cartListCookie.get(i).getTjshl() + cart.getTjshl());
                        cartMap.put("skuId",cartListCookie.get(i).getSkuId());
                        cartMap.put("userId",user.getId());
                        cartMap.put("tjshl",cartListCookie.get(i).getTjshl());
                        cartMap.put("hj",CartController.getHj(cartListCookie.get(i)));
                        cartService.updateCartByUserIdAndsh(cartMap);
                    }else{//数据库中没有则 直接加入数据库
                        //添加当前对象
                        cartListCookie.get(i).setYhId(user.getId());
                        cartService.saveCart(cartListCookie.get(i));

                    }
                }
                //登录后清空cookie
                //清空cookie中的购物车
                MyCookieUtils.deleteCookie(request, response, "cookieCartList");
                //清空redis
                //更新 redis(清除redis中cart的list,当前用户)
                redisTemplate.delete("redisCartListUser"+user.getId());
            }
            if(!StringUtils.isBlank(url)){
                return "redirect:"+url+".do";
            }
            return "redirect:toMainPage.do";
        }
    }
    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:toLoginPage.do";
    }

}
