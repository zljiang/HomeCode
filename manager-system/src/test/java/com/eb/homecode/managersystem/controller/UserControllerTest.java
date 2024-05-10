package com.eb.homecode.managersystem.controller;

import com.alibaba.fastjson.JSON;
import com.eb.homecode.managersystem.interceptor.UserInfoValidateInterceptor;
import com.eb.homecode.managersystem.model.UserInfo;
import com.eb.homecode.managersystem.service.ResourceService;
import com.eb.homecode.managersystem.vo.ResultMessage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.eb.homecode.managersystem.config.Constants.USER_REQUEST_HEADER;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserController 单元测试
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ResourceService resourceService;

    @MockBean
    private UserInfoValidateInterceptor userInfoValidateInterceptor;

    /**
     * 请求返回 success: true
     *
     * @throws Exception
     */
    @Test
    void resource_success() throws Exception {
        ResultMessage resultMessage = perform(new ArrayList<Integer>(){{add(123456);}});
        assertTrue(resultMessage.isSuccess());
        assertEquals(resultMessage.getMessage(), "访问资源 resourceA 成功");
    }

    /**
     * 请求返回 success: false
     *
     * @throws Exception
     */
    @Test
    void resource_fail() throws Exception {
        ResultMessage resultMessage = perform(new ArrayList<Integer>(){{add(123457);}});
        assertFalse(resultMessage.isSuccess());
        assertEquals(resultMessage.getMessage(), "该用户无权限访问资源 resourceA");
    }

    private ResultMessage perform(List<Integer> userIds) throws Exception {
        given(userInfoValidateInterceptor.preHandle(any(HttpServletRequest.class),
                any(HttpServletResponse.class), any())).willReturn(true);
        given(resourceService.queryUserIds("resourceA")).willReturn(userIds);

        MvcResult result = mvc.perform(get("/user/{resource}", "resourceA")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .requestAttr(USER_REQUEST_HEADER, new UserInfo(123456, "123456", "user")))
                //.andDo(print())
                .andExpect(status().isOk()).andReturn();

        return JSON.parseObject(result.getResponse().getContentAsString(Charset.defaultCharset()), ResultMessage.class);
    }

}