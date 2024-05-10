package com.eb.homecode.managersystem.dao.impl;

import com.eb.homecode.managersystem.dao.BaseDao;
import com.eb.homecode.managersystem.dao.UserDao;
import com.eb.homecode.managersystem.util.Utils;
import com.eb.homecode.managersystem.model.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserFileDaoImpl extends BaseDao implements UserDao {

    private static final String USER_FILE = "./src/main/resources/storage/user-info.txt";

    @Override
    public void save(Map<Integer, UserInfo> userInfoCache) {
        Utils.loadFile(USER_FILE);

        try {
            FileWriter fw = new FileWriter(USER_FILE);
            StringBuilder sb = new StringBuilder();
            userInfoCache.forEach((userId, userInfo) -> sb.append(userInfo.toString()));
            fw.append(sb.toString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<Integer, UserInfo> queryAllUser() {
        Map<Integer, UserInfo> map = new HashMap<>();
        parseFile(Utils.loadFile(USER_FILE), map);

        return map;
    }

    @Override
    protected void parseLine(String line, Map map) {
        if (StringUtils.hasLength(line)) {
            String[] arr = line.split("\\|");
            int userId = Integer.parseInt(arr[0]);
            UserInfo userInfo = new UserInfo(userId, arr[1], arr[2]);
            map.put(userId, userInfo);
        }
    }
}
