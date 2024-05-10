package com.eb.homecode.managersystem.dao.impl;

import com.eb.homecode.managersystem.dao.BaseDao;
import com.eb.homecode.managersystem.dao.ResourceDao;
import com.eb.homecode.managersystem.util.Utils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ResourceFileDaoImpl extends BaseDao implements ResourceDao {
    private static final String RESOURCE_USER_FILE = "./src/main/resources/storage/resource-user-info.txt";

    @Override
    public Map<String, List<Integer>> queryAll() {
        Map<String, List<Integer>> map = new HashMap<>();
        File file = Utils.loadFile(RESOURCE_USER_FILE);
        parseFile(file, map);

        return map;
    }

    // Map: <Resource, List<UserId>>
    @Override
    public void update(Map<String, List<Integer>> map) {
        Utils.loadFile(RESOURCE_USER_FILE);

        FileWriter fw = null;
        try {
            fw = new FileWriter(RESOURCE_USER_FILE);
            for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
                List<Integer> list = entry.getValue();
                String line = list.stream().map(String::valueOf).collect(Collectors.joining("|"));
                line = entry.getKey().concat(":").concat(line).concat(System.lineSeparator());

                fw.append(line);
                fw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void parseLine(String line, Map map) {
        if (StringUtils.hasLength(line)) {
            String[] arr = line.split(":");
            List<Integer> userIds = Arrays.stream(arr[1].split("\\|")).map(Integer::parseInt).collect(Collectors.toList());
            map.put(arr[0], userIds);
        }
    }
}
