package com.mr.controller;

import com.mr.model.TMallShoppingCar;
import com.mr.model.TMallUserAccount;
import com.mr.service.CartService;
import com.mr.util.MyCookieUtils;
import com.mr.util.MyJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/7.
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 保存购物车
     * cookieName : cookieCartList
     * @CookieValue("key")：从cookie对象中获取名为key的cookie值
     *  将list对象转为json字符串 存放在cookie中
     *  cookie取出来的也是String类型的
     *    注意：用户存放在session的是yhMch
     * @return
     */

    //获取合计值
    //传过来一个TMallShoppingCar类型的值
    //放回一个种价格
    public static Double getHj(TMallShoppingCar cart){

        BigDecimal jg = new BigDecimal(cart.getSkuJg() + "");//转换为String类型的
        BigDecimal thShl = new BigDecimal(cart.getTjshl());

        double hj = thShl.multiply(jg).doubleValue();
        return hj;
    }

    @RequestMapping("queryCart")
    public String queryCart(TMallShoppingCar cart, HttpSession session,ModelMap map,
                            @CookieValue(value = "cookieCartList",required = false) String cookieCartList,
                            HttpServletRequest  request, HttpServletResponse response) {//加入cookie时使用
        cart.setHj(getHj(cart));
        //定义购物车集合
        List<TMallShoppingCar> cartList = new ArrayList<TMallShoppingCar>();
        //判断是否登录
        TMallUserAccount user = (TMallUserAccount) session.getAttribute("user");
        if (user == null) {//未登录   将数据放入cookie中
            if (StringUtils.isBlank(cookieCartList)) {//为空：直接添加在cookie中
                //给购物车集合添加数据
                cartList.add(cart);
            } else {//有数据，则需要判断更新
                //获取cookie中的值
                cartList = MyJsonUtil.jsonToList(cookieCartList, TMallShoppingCar.class);
                //定义一个boolean 定义一个为false 后面判断的时候使用
                boolean pd = false;
                for (int i = 0; i < cartList.size(); i++) {
                    //判断新提交的商品是否存在
                    if (cartList.get(i).getSkuId() == cart.getSkuId()) {
                        //如果新提交的商品存在   给boolean一个true
                        pd = true;
                    }
                }
                if (pd) {//存在
                    for (int i = 0; i < cartList.size(); i++) {
                        if (cartList.get(i).getSkuId() == cart.getSkuId()) {
                            cartList.get(i).setTjshl(cartList.get(i).getTjshl() + cart.getTjshl());
                            cartList.get(i).setHj(getHj(cartList.get(i)));//把总价放入cookie中
                        }
                    }
                } else {//cookie里不存在
                    cartList.add(cart);//直接加入cookie中就可以了
                }
            }//存放在cookie中
            MyCookieUtils.setCookie(request, response,"cookieCartList",
            MyJsonUtil.objectToJson(cartList),3*60*60, true);
        } else {//登录

            //判断数据库中是否有数据，当前用户
            //获取数据
            cartList = cartService.listCartByUserId(user.getId());
            if(cartList!=null && cartList.size()>0){//数据库有数据时
                boolean b2=false;//循环遍历
                for (int i = 0; i < cartList.size(); i++) {
                    //数据库中存在
                    if(cartList.get(i).getSkuId() == cart.getSkuId()){
                        b2=true;
                    }
                }
                if(b2){
                    //判断如果b2为true时说明数据库中存在则需要进行修改
                    for (int i = 0; i < cartList.size(); i++) {
                        if(cartList.get(i).getSkuId() == cart.getSkuId()){
                            //传一个Map方便接受
                            Map<String, Object> cartMap = new HashMap<>();
                            cartList.get(i).setTjshl(cartList.get(i).getTjshl() + cart.getTjshl());
                            cartMap.put("skuId",cartList.get(i).getSkuId());
                            cartMap.put("userId",user.getId());
                            cartMap.put("tjshl",cartList.get(i).getTjshl());
                            cartMap.put("hj",getHj(cartList.get(i)));
                            cartService.updateCartByUserIdAndsh(cartMap);
                        }
                    }
                }else{//没有数据时可以直接添加
                    cart.setYhId(user.getId());
                    cartService.saveCart(cart);
                }
            }else{//没有数据时可以直接添加
                cart.setYhId(user.getId());
                cartService.saveCart(cart);
            }
            //更新 redis(清除redis中cart的list,当前用户)
            redisTemplate.delete("redisCartListUser"+user.getId());
        }
        //返回订单提交完成页面
        map.put("cart",cart);
        return "cart-success";
    }
    @RequestMapping("findMiniCart")
    public String findMiniCart(HttpSession session, ModelMap map,
                               @CookieValue(value="cookieCartList",required = false) String cookieCartList){
        //定义购物车集合
        List<TMallShoppingCar> cartList = new ArrayList<>();
        //判断是否登录
        TMallUserAccount user = (TMallUserAccount)session.getAttribute("user");
        cartList = queryCartList(user, session, cookieCartList);
        Integer countNum = 0;
        if(cartList!=null){
            for (int i = 0; i < cartList.size(); i++) {
                countNum += cartList.get(i).getTjshl();
            }
        }
        map.put("countNum",countNum);
        map.put("hjSum",getSum(cartList));
        map.put("cartList",cartList);
        return "miniCartInner";
    }
    public static BigDecimal getSum(List<TMallShoppingCar> cartList){
        BigDecimal sum = new BigDecimal("0");
        for (int i = 0; i < cartList.size(); i++) {
            if(cartList.get(i).getShfxz().equals("1")){//如果选择
                sum = sum.add(new BigDecimal(cartList.get(i).getHj() + ""));
            }
        }
        return sum;
    }
    @RequestMapping("toCartListPage")
    public String toCartListPage(HttpSession session, ModelMap map,
                                 @CookieValue(value = "cookieCartList",required = false) String cookieCartList){
        //定义购物车集合
        List<TMallShoppingCar> cartList = new ArrayList<>();
        //判断是否登录
        TMallUserAccount user = (TMallUserAccount) session.getAttribute("user");
        cartList = queryCartList(user, session, cookieCartList);
        map.put("cartList",cartList);
        map.put("hjSum",getSum(cartList));
        return "carList";
    }
    @RequestMapping("changeShfxz")
    public String toCartListPage(HttpServletResponse response ,HttpServletRequest request,
                                 int skuId , String shfxz , ModelMap map,HttpSession session,
                                 @CookieValue(value = "cookieCartList",required = false) String cookieCartList){
        //定义集合
        List<TMallShoppingCar> cartList = new ArrayList<>();
        //判断是否登录
        TMallUserAccount user = (TMallUserAccount) session.getAttribute("user");
        if (user == null) {//未登录 未登录则从cookie中获取
            cartList = MyJsonUtil.jsonToList(cookieCartList, TMallShoppingCar.class);
            for (int i = 0; i < cartList.size(); i++) {
                if(cartList.get(i).getSkuId()==skuId){
                    cartList.get(i).setShfxz(shfxz);
                }
            }
            //更新cookie
            MyCookieUtils.setCookie(request,response,"cookieCartList", MyJsonUtil.objectToJson(cartList),3*60*60,true);
        }else {//登录则从 redis中获取
            //从redis中获取数据
            cartList = (List<TMallShoppingCar>)redisTemplate.opsForValue().get("redisCartListUser" + user.getId());
            for (int i = 0; i < cartList.size(); i++) {
                if(cartList.get(i).getSkuId()==skuId){
                    cartService.updateShfxzByskuIdAnduserId(skuId,user.getId(),shfxz);
                    //修改
                    cartList.get(i).setShfxz(shfxz);
                }
            }
            //同步redis中
            redisTemplate.opsForValue().set("redisCartListUser"+user.getId(),cartList);
        }
        map.put("cartList",cartList);
        map.put("hjSum",getSum(cartList));

        return "cartListInner";
    }


    //传一个user用户自动去判断 是否登录 未登录从cookie中去获取数据
    //登录则从数据库中去获取
    public List<TMallShoppingCar> queryCartList(TMallUserAccount user,HttpSession session,
                                                       @CookieValue(value = "cookieCartList",required = false) String cookieCartList){
        //定义购物车集合
        List<TMallShoppingCar> cartList = new ArrayList<>();
        //判断是否登录
        user = (TMallUserAccount) session.getAttribute("user");
        if (user == null) {//未登录 未登录则从cookie中获取
            cartList = MyJsonUtil.jsonToList(cookieCartList, TMallShoppingCar.class);
        }else{//登录则从 redis中获取
            //从redis中获取数据
            cartList = (List<TMallShoppingCar>)redisTemplate.opsForValue().get("redisCartListUser" + user.getId());
            //判断redis中是否有数据
            if(cartList==null || cartList.size()==0){
                //根据用户Id查询数据
                cartList = cartService.listCartByUserId(user.getId());
                //获取数据后放入redis
                redisTemplate.opsForValue().set("redisCartListUser"+user.getId(),cartList);
            }
        }
        return cartList;
    }
    //删除
    public String deleteCartById(){

        return "";
    }
}