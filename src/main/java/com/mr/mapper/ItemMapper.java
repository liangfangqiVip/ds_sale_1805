package com.mr.mapper;

import com.mr.model.TMallSku;
import com.mr.model.TMallSkuItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by LFQ on 2018/11/7.
 */
public interface ItemMapper {

    List<TMallSku> querySkuBySpuId(@Param("spuId") Integer spuId);

    TMallSkuItemVO querySkuBySkuId(@Param("skuId") Integer skuId);

}
