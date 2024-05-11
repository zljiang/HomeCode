package com.eb.homecode.managersystem.interceptor;

import com.eb.homecode.managersystem.config.Role;
import com.eb.homecode.managersystem.model.UserInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.eb.homecode.managersystem.config.Constants.USER_REQUEST_HEADER;
import static org.mockito.BDDMockito.given;

/**
 * AdminValidateInterceptor 测试类
 */
@RunWith(SpringRunner.class)
public class AdminValidateInterceptorTest {

    private AdminValidateInterceptor adminValidateInterceptor = new AdminValidateInterceptor();

    @MockBean
    private HttpServletRequest request;

    @MockBean
    private HttpServletResponse response;

    @MockBean
    private PrintWriter printWriter;

    /**
     * 测试校验通过
     */
    @Test
    public void preHandle_return_true() {
        given(request.getAttribute(USER_REQUEST_HEADER))
                .willReturn(new UserInfo(123456, "zljiang", Role.admin.name()));
        boolean result = adminValidateInterceptor.preHandle(request, response, null);
        Assert.assertTrue(result);
    }

    /**
     * 测试用户无权限执行该操作
     *
     * @throws IOException
     */
    @Test
    public void preHandle_return_false() throws IOException {
        given(request.getAttribute(USER_REQUEST_HEADER))
                .willReturn(new UserInfo(123456, "zljiang", Role.user.name()));
        given(response.getWriter()).willReturn(printWriter);
        boolean result = adminValidateInterceptor.preHandle(request, response, null);
        Assert.assertFalse(result);
    }
}