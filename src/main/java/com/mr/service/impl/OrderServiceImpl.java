package com.mr.service.impl;

import com.mr.mapper.OrderMapper;
import com.mr.model.*;
import com.mr.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/12.
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<TMallAddress> listAddressByYhId(Integer userId) {
        return orderMapper.listAddressByYhId(userId);
    }


    public void saveOrder(HttpSession session,TMallOrderVO orderVO) {
        TMallUserAccount user = (TMallUserAccount) session.getAttribute("user");
        //删除购物车的集合
        List<Integer> cartIds = new ArrayList<>();
        //增加 order
        orderMapper.saveOrder(orderVO);
        //获取到orderId ，然后增加 List<flow>、for循环增加Flow
        List<TMallFlowVO> flowList = orderVO.getFlowList();
        for (int i = 0; i < flowList.size(); i++) {
            Map flowMap = new HashMap();
            TMallFlowVO flowvo = flowList.get(i);
            flowMap.put("flow",flowvo);
            flowMap.put("orderId",orderVO.getId());
            orderMapper.saveFlow(flowMap);

            //增加orderInfo 、批量增加
            List<TMallOrderInfo> infoList = flowList.get(i).getInfoList();
            Map infoMap = new HashMap();
            infoMap.put("infoList",infoList);
            infoMap.put("flowId",flowvo.getId());
            infoMap.put("orderId",orderVO.getId());
            orderMapper.saveInfo(infoMap);

        }
        //从redis中获取当前用户的购物车数据
        List<TMallShoppingCar> cartList =
                (List<TMallShoppingCar>)redisTemplate.opsForValue().get("redisCartListUser"+user.getId());
        //定义放回的集合

        //循环获取被选中的数据
        for (int i = 0; i < cartList.size(); i++) {
            TMallShoppingCar cart = cartList.get(i);
            if(cart.getShfxz().equals("1")){
                cartIds.add(cart.getId());
            }
        }
        //删除购物车
        orderMapper.deleteCartsByCartIds(cartIds);

    }
}
