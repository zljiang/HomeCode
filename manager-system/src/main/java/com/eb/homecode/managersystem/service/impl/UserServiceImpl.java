package com.eb.homecode.managersystem.service.impl;

import com.eb.homecode.managersystem.config.Role;
import com.eb.homecode.managersystem.dao.UserDao;
import com.eb.homecode.managersystem.model.UserInfo;
import com.eb.homecode.managersystem.service.ResourceService;
import com.eb.homecode.managersystem.service.UserService;
import com.eb.homecode.managersystem.vo.UserParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@EnableScheduling
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResourceService resourceService;

    // <userId, UserInfo>
    private Map<Integer, UserInfo> userInfoCache;

    @PostConstruct
    @Scheduled(fixedRate = 2 * 60 * 1000)
    private void init() {
        synchronized (this) {
            LOGGER.info("refresh userInfoCache...");
            userInfoCache = userDao.queryAllUser();
            if (null == userInfoCache) {
                userInfoCache= new HashMap<>();
            }
        }
    }

    @Override
    public void save(@RequestBody UserParam param) {
        synchronized (this) {
            Integer userId = param.getUserId();
            UserInfo userInfo = new UserInfo(userId, userId.toString(), Role.user.name());

            resourceService.updateResources(param.getEndpoint(), userId);
            userInfoCache.put(userId, userInfo);
            userDao.save(userInfoCache);
        }
    }

    @Override
    public UserInfo queryUserById(Integer userId) {
        return userInfoCache.get(userId);
    }
}
