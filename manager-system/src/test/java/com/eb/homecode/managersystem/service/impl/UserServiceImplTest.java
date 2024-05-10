package com.eb.homecode.managersystem.service.impl;

import com.eb.homecode.managersystem.dao.UserDao;
import com.eb.homecode.managersystem.service.ResourceService;
import com.eb.homecode.managersystem.service.UserService;
import com.eb.homecode.managersystem.vo.UserParam;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

/**
 * UserServiceImpl 测试类
 */
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @MockBean
    private UserDao userDao;

    @MockBean
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        doNothing().when(resourceService).updateResources(any(), any());
        doNothing().when(userDao).save(any());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void save() {
        UserParam userParam = new UserParam();
        userParam.setUserId(123456);
        userParam.setEndpoint(Arrays.asList("resourceA"));
        userService.save(userParam);
    }

    @Test
    public void queryUserById() {
        userService.queryUserById(123456);
    }
}