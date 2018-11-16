package com.mr.mapper;

import com.mr.model.TMallValueVO;

import java.util.List;

/**
 * Created by LFQ on 2018/11/6.
 */
public interface AttrMapper {


    List<TMallValueVO> queryAttrList(Integer flbh2);
}
