package com.eb.homecode.managersystem.service;

import com.eb.homecode.managersystem.model.UserInfo;
import com.eb.homecode.managersystem.vo.UserParam;

public interface UserService {
    void save(UserParam param);

//    Map<Integer, UserInfo> queryAllUser();

    UserInfo queryUserById(Integer userId);
}
