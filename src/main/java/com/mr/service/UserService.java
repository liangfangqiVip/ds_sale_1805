package com.mr.service;

import com.mr.model.TMallUserAccount;

/**
 * Created by LFQ on 2018/11/5.
 */
public interface UserService {


    TMallUserAccount login(String userName, String passwork);
}
