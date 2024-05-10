package com.eb.homecode.managersystem.dao;

import java.util.List;
import java.util.Map;

public interface ResourceDao {
    Map<String, List<Integer>> queryAll();

    // Map: <Resource, List<UserId>>
    void update(Map<String, List<Integer>> map);
}
