package com.mr.mapper;

import com.mr.model.TMallUserAccount;
import org.apache.ibatis.annotations.Param;

/**
 * Created by LFQ on 2018/11/5.
 */
public interface UserMapper {

    TMallUserAccount login(@Param("userName") String userName,@Param("passwork") String passwork);

}
