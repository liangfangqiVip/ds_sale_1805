package com.mr.controller;

import com.mr.model.*;
import com.mr.service.CartService;
import com.mr.service.OrderService;
import com.mr.util.MyDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by LFQ on 2018/11/12.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CartService cartService;

    @RequestMapping("toCheckOrder")
    public String toCheckOrder(HttpSession session, ModelMap map){
        //判断是否登录
        TMallUserAccount user = (TMallUserAccount)session.getAttribute("user");

        if(user==null){
            return "redirect:toLoginPage.do?url=toCartListPage";
        }else{
            //通过当前用户查询地址
            List<TMallAddress> addressList = orderService.listAddressByYhId(user.getId());
            //获取到购物车中 被选中的对象

            //从redis中获取当前用户的购物车数据
            List<TMallShoppingCar> cartList =
                    (List<TMallShoppingCar>)redisTemplate.opsForValue().get("redisCartListUser"+user.getId());
            //定义放回的集合
            List<TMallShoppingCar> checkOrderList = new ArrayList<>();
            //判断redis是否为空
            if(cartList==null || cartList.size()==0){//为空则从数据库查
                cartList = cartService.listCartByUserId(user.getId());
            }
            //循环获取被选中的数据
            for (int i = 0; i < cartList.size(); i++) {
                TMallShoppingCar cart = cartList.get(i);
                if(cart.getShfxz().equals("1")){
                    checkOrderList.add(cart);
                }
            }
            map.put("addressList",addressList);
            map.put("checkOrderList",checkOrderList);
            map.put("sum",CartController.getSum(cartList));
        }
        return "checkOrder";
    }

    @RequestMapping("saveOrder")
    public String saveOrder(TMallAddress address,HttpSession session) {
        TMallUserAccount user = (TMallUserAccount) session.getAttribute("user");
        TMallFlowVO flowVO=null;
        Set<String> flowSet=null;
        //实体类：一个订单，多个物流信息，每个物流信息中有多个订单详情
        TMallOrderVO orderVO=null;
        //物流信息
        List<TMallFlowVO> flowList=null;
        //订单详情集合
        List<TMallOrderInfo> infoList=null;
        //从redis中获取当前用户的购物车数据
        List<TMallShoppingCar> cartList =
                (List<TMallShoppingCar>) redisTemplate.opsForValue().get("redisCartListUser" + user.getId());
        for (int i = 0; i < cartList.size(); i++) {
            if(cartList.get(i).getShfxz().equals("1")){
            //实体类：一个订单，多个物流信息，每个物流信息中有多个订单详情
            orderVO = new TMallOrderVO();
            orderVO.setJdh(1);
            orderVO.setZje(CartController.getSum(cartList).doubleValue());
            orderVO.setYhId(user.getId());
            orderVO.setDzhId(address.getId());
            orderVO.setDzhMch(address.getDzMch());
            orderVO.setShhr(address.getShjr());
                //物流信息
                flowList = new ArrayList<>();
                //存放库存地址的集合
                flowSet = new HashSet<>();
                //拆单：根据不用的库存地址来进行拆分
                String kcdz = cartList.get(i).getKcdz();
                flowSet.add(kcdz);
                Iterator<String> iterator = flowSet.iterator();
                while (iterator.hasNext()) {
                String nextKcdz = iterator.next();
                flowVO = new TMallFlowVO();
                flowVO.setPsfsh("顺丰物流");
                flowVO.setPsshj(MyDateUtil.getMyDateD(new Date(), 1));
                flowVO.setPsmsh("配送描述：风里雨里，东门等你！");
                flowVO.setYhId(user.getId());
                infoList = new ArrayList<>();
                TMallOrderInfo info = new TMallOrderInfo();
                TMallShoppingCar car = cartList.get(i);
                    if (car.getKcdz().equals(nextKcdz)) {
                        info.setSkuJg(car.getSkuJg());
                        info.setSkuShl(car.getTjshl());
                        info.setSkuKcdz(car.getKcdz());
                        info.setGwchId(car.getId());
                        info.setSkuId(car.getSkuId());
                        info.setSkuMch(car.getSkuMch());
                        info.setShpTp(car.getShpTp());
                        infoList.add(info);
                    }
                }
            }
        }
        //在物流信息中添加info信息
        flowVO.setInfoList(infoList);
        //将每一个符合地址的数据存放在物流集合中
        flowList.add(flowVO);
        orderVO.setFlowList(flowList);
        orderService.saveOrder(session,orderVO);
        //更新 reids
        redisTemplate.delete("redisCartListUser"+user.getId());
        //跳转到支付页面
        return "redirect:pay.do";
    }
    @RequestMapping("pay")
    public String pay(){

        return "pay";
    }

}
