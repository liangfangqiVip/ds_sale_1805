package com.mr.service;

import com.mr.model.TMallShoppingCar;

import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/7.
 */
public interface CartService {

    List<TMallShoppingCar> listCartByUserId(Integer id);

    void saveCart(TMallShoppingCar cart);

    void updateCartByUserIdAndsh(Map<String, Object> cartMap);

    TMallShoppingCar findCartBySkuIdAndUserId(Integer skuId, Integer id);

    void updateShfxzByskuIdAnduserId(int skuId, Integer id, String shfxz);
}
