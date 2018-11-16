package com.mr.service.impl;

import com.mr.mapper.CartMapper;
import com.mr.model.TMallShoppingCar;
import com.mr.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/7.
 */
@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<TMallShoppingCar> listCartByUserId(Integer userId) {
        return cartMapper.listCartByUserId(userId);
    }

    @Override
    public void saveCart(TMallShoppingCar cart) {
            cartMapper.saveCart(cart);
    }

    @Override
    public void updateCartByUserIdAndsh(Map<String, Object> cartMap) {
        cartMapper.updateCartByUserIdAndsh(cartMap);
    }

    @Override
    public TMallShoppingCar findCartBySkuIdAndUserId(Integer skuId, Integer userId) {
        return cartMapper.findCartBySkuIdAndUserId(skuId,userId);
    }

    @Override
    public void updateShfxzByskuIdAnduserId(int skuId, Integer userId, String shfxz) {
        Map<String,Object>map=new HashMap<>();
        map.put("skuId",skuId);
        map.put("userId",userId);
        map.put("shfxz",shfxz);
        cartMapper.updateShfxzByskuIdAnduserId(map);
    }
}
