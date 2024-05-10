package com.eb.homecode.managersystem.dao;

import com.eb.homecode.managersystem.model.UserInfo;

import java.util.Map;

public interface UserDao {
    void save(Map<Integer, UserInfo> userInfoCache);

    Map<Integer, UserInfo> queryAllUser();
}
