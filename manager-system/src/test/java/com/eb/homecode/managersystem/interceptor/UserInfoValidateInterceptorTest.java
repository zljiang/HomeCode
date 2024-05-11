package com.eb.homecode.managersystem.interceptor;

import com.eb.homecode.managersystem.config.Role;
import com.eb.homecode.managersystem.model.UserInfo;
import com.eb.homecode.managersystem.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Field;

import static com.eb.homecode.managersystem.config.Constants.USER_REQUEST_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * UserInfoValidateInterceptor 测试类
 */
@RunWith(SpringRunner.class)
public class UserInfoValidateInterceptorTest {

    private UserInfoValidateInterceptor userInfoValidateInterceptor = new UserInfoValidateInterceptor();

    @MockBean
    private HttpServletRequest request;

    @MockBean
    private HttpServletResponse response;

    @MockBean
    private PrintWriter printWriter;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        // 利用反射注入属性 userService
        Class<? extends UserInfoValidateInterceptor> testClass
                = userInfoValidateInterceptor.getClass();
        Field userServiceField = testClass.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(userInfoValidateInterceptor, userService);

        given(userService.queryUserById(any()))
                .willReturn(new UserInfo(123456, "zljiang", Role.admin.name()));
        given(response.getWriter()).willReturn(printWriter);
    }

    /**
     * 测试通过校验
     */
    @Test
    public void preHandle_return_true() {
        given(request.getHeader(USER_REQUEST_HEADER)).
                willReturn("eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiJ6bGppYW5nIiwicm9sZSI6ImFkbWluIn0=");

        boolean result = userInfoValidateInterceptor.preHandle(request, response, null);
        Assert.assertTrue(result);
    }

    /**
     * 测试 Header 无 user-info
     */
    @Test
    public void preHandle_return_false_1() {
        boolean result = userInfoValidateInterceptor.preHandle(request, response, null);
        Assert.assertFalse(result);
    }

    /**
     * 测试 Header 中 user-info 的 usserId 为 null
     */
    @Test
    public void preHandle_return_false_2() {
        // {"userId":null,"accountName":"zljiang","role":"admin"}
        given(request.getHeader(USER_REQUEST_HEADER)).
                willReturn("eyJ1c2VySWQiOm51bGwsImFjY291bnROYW1lIjoiemxqaWFuZyIsInJvbGUiOiJhZG1pbiJ9");

        boolean result = userInfoValidateInterceptor.preHandle(request, response, null);
        Assert.assertFalse(result);
    }

    /**
     * 测试 Header 中 user-info 的 accountName 为 null
     */
    @Test
    public void preHandle_return_false_3() {
        // {"userId":123456,"accountName":"","role":"admin"}
        given(request.getHeader(USER_REQUEST_HEADER)).
                willReturn("eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiIiLCJyb2xlIjoiYWRtaW4ifQ==");

        boolean result = userInfoValidateInterceptor.preHandle(request, response, null);
        Assert.assertFalse(result);
    }

    /**
     * 测试 Header 中 user-info 的 role 为 null
     */
    @Test
    public void preHandle_return_false_4() {
        // {"userId":123456,"accountName":"zljiang","role":""}
        given(request.getHeader(USER_REQUEST_HEADER)).
                willReturn("eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiJ6bGppYW5nIiwicm9sZSI6IiJ9");

        boolean result = userInfoValidateInterceptor.preHandle(request, response, null);
        Assert.assertFalse(result);
    }

    /**
     * 测试 Header 中 user-info 的 role 值非法
     */
    @Test
    public void preHandle_return_false_5() {
        // {"userId":123456,"accountName":"zljiang","role":"role"}
        given(request.getHeader(USER_REQUEST_HEADER)).
                willReturn("eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiJ6bGppYW5nIiwicm9sZSI6InJvbGUifQ==");

        boolean result = userInfoValidateInterceptor.preHandle(request, response, null);
        Assert.assertFalse(result);
    }

    /**
     * 测试 Header 中 user-info 在存储中不存在
     */
    @Test
    public void preHandle_return_false_6() {
        given(request.getHeader(USER_REQUEST_HEADER)).
                willReturn("eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiJ6bGppYW5nIiwicm9sZSI6ImFkbWluIn0=");
        given(userService.queryUserById(any()))
                .willReturn(null);

        boolean result = userInfoValidateInterceptor.preHandle(request, response, null);
        Assert.assertFalse(result);
    }

    /**
     * 测试 Header 中 user-info 与存储用户信息不一致
     */
    @Test
    public void preHandle_return_false_7() {
        given(request.getHeader(USER_REQUEST_HEADER)).
                willReturn("eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiJ6bGppYW5nIiwicm9sZSI6InVzZXIifQ==");

        boolean result = userInfoValidateInterceptor.preHandle(request, response, null);
        Assert.assertFalse(result);
    }
}