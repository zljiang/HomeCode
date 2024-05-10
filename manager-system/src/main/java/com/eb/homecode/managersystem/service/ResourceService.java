package com.eb.homecode.managersystem.service;

import java.util.List;

public interface ResourceService {
    void updateResources(List<String> resources, Integer userId);

    List<Integer> queryUserIds(String resource);
}
