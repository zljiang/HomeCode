package com.eb.homecode.managersystem.service.impl;

import com.eb.homecode.managersystem.dao.ResourceDao;
import com.eb.homecode.managersystem.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
public class ResourceServiceImpl implements ResourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private ResourceDao resourceDao;

    // <Resource, List<UserId>>
    private Map<String, List<Integer>> resourceAuthorizedUsersCache;

    @PostConstruct
    @Scheduled(fixedRate = 3 * 60 * 1000)
    private void init() {
        synchronized (this) {
            LOGGER.info("refresh resourceAuthorizedUsersCache...");
            resourceAuthorizedUsersCache = resourceDao.queryAll();
            if (null == resourceAuthorizedUsersCache) {
                resourceAuthorizedUsersCache = new HashMap<>();
            }
        }
    }

    @Override
    public void updateResources(List<String> resources, Integer userId) {
        synchronized (this) {
            boolean changed = false;
            for (String resource : resources) {
                List<Integer> users = resourceAuthorizedUsersCache.get(resource);
                if (users == null) {
                    users = new ArrayList<>();
                }

                if (!users.contains(userId)) {
                    changed = true;
                    users.add(userId);
                    resourceAuthorizedUsersCache.put(resource, users);
                }
            }

            if (changed) {
                resourceDao.update(resourceAuthorizedUsersCache);
            }
        }
    }

    @Override
    public List<Integer> queryUserIds(String resource) {
        return resourceAuthorizedUsersCache.get(resource);
    }
}
