package com.mr.service.impl;

import com.mr.mapper.AttrMapper;
import com.mr.model.TMallValueVO;
import com.mr.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LFQ on 2018/11/6.
 */
@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    private AttrMapper attrMapperr;


    @Override
    public List<TMallValueVO> queryAttrList(Integer flbh2) {

        return attrMapperr.queryAttrList(flbh2);
    }
}
