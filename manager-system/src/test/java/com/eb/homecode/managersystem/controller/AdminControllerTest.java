package com.eb.homecode.managersystem.controller;

import com.alibaba.fastjson.JSON;
import com.eb.homecode.managersystem.interceptor.UserInfoValidateInterceptor;
import com.eb.homecode.managersystem.model.UserInfo;
import com.eb.homecode.managersystem.service.UserService;
import com.eb.homecode.managersystem.vo.ResultMessage;
import com.eb.homecode.managersystem.vo.UserParam;
import org.junit.Test;
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
import java.util.Arrays;

import static com.eb.homecode.managersystem.config.Constants.USER_REQUEST_HEADER;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * AdminController 单元测试
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserInfoValidateInterceptor userInfoValidateInterceptor;

    @Test
    public void addUser() throws Exception {
        given(userInfoValidateInterceptor.preHandle(any(HttpServletRequest.class),
                any(HttpServletResponse.class), any())).willReturn(true);
        doNothing().when(userService).save(any(UserParam.class));

        UserParam userParam = new UserParam();
        userParam.setUserId(123456);
        userParam.setEndpoint(Arrays.asList("resourceA"));

        MvcResult result = mvc.perform(post("/admin/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .requestAttr(USER_REQUEST_HEADER, new UserInfo(123456, "123456", "admin"))
                        .content(JSON.toJSONString(userParam)))
                //.andDo(print())
                .andExpect(status().isOk()).andReturn();

        ResultMessage resultMessage =
                JSON.parseObject(result.getResponse().getContentAsString(Charset.defaultCharset()), ResultMessage.class);

        assertTrue(resultMessage.isSuccess());
        assertEquals(resultMessage.getMessage(), "添加用户成功");

    }
}