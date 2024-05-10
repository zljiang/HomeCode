package com.eb.homecode.managersystem.service.impl;

import com.eb.homecode.managersystem.dao.ResourceDao;
import com.eb.homecode.managersystem.service.ResourceService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

/**
 * ResourceServiceImpl 测试类
 */
@RunWith(SpringRunner.class)
public class ResourceServiceImplTest {

    @TestConfiguration
    static class ResourceServiceImplTestContextConfiguration {
        @Bean
        public ResourceService resourceService() {
            return new ResourceServiceImpl();
        }
    }

    @MockBean
    private ResourceDao resourceDao;

    @Autowired
    private ResourceService resourceService;

    @Before
    public void setUp() throws Exception {
        doNothing().when(resourceDao).update(any());
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>(){{
            put("resourceA", Arrays.asList(123456));
        }};
        given(resourceDao.queryAll()).willReturn(map);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void updateResources() {
        resourceService.updateResources(Arrays.asList("resourceA"), 123456);
    }

    @Test
    public void queryUserIds() {
        resourceService.queryUserIds("resourceA");
    }
}