package com.mr.mapper;

import com.mr.model.TMallAddress;
import com.mr.model.TMallOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by LFQ on 2018/11/12.
 */
public interface OrderMapper {
    List<TMallAddress> listAddressByYhId(Integer userId);

    void saveOrder(TMallOrderVO orderVO);

    void saveFlow(Map flowMap);

    void saveInfo(Map infoMap);

    void deleteCartsByCartIds(@Param("cartIds")List<Integer> cartIds);
}
