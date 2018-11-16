package com.mr.service;

import com.mr.model.TMallAddress;
import com.mr.model.TMallOrderVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by LFQ on 2018/11/12.
 */
public interface OrderService {

    List<TMallAddress> listAddressByYhId(Integer id);

    void saveOrder(HttpSession session, TMallOrderVO orderVO);

}
