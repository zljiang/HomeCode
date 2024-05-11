package com.eb.homecode.managersystem.interceptor;

import com.alibaba.fastjson.JSON;
import com.eb.homecode.managersystem.config.Role;
import com.eb.homecode.managersystem.model.UserInfo;
import com.eb.homecode.managersystem.service.UserService;
import com.eb.homecode.managersystem.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.eb.homecode.managersystem.config.Constants.USER_REQUEST_HEADER;

@Component
public class UserInfoValidateInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoValidateInterceptor.class);

    @Autowired
    private UserService userService;

    private BASE64Decoder decoder = new BASE64Decoder();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String encodedUserInfo = request.getHeader(USER_REQUEST_HEADER);

        LOGGER.info("用户信息：" + encodedUserInfo);

        if (!StringUtils.hasLength(encodedUserInfo)) {
            Utils.overrideResponse(response, false, "用户信息缺失");
            return false;
        }

        UserInfo userInfo;
        try {
            String srcUserInfo = new String(decoder.decodeBuffer(new String(encodedUserInfo.getBytes(StandardCharsets.UTF_8))));
            userInfo = JSON.parseObject(srcUserInfo, UserInfo.class);
        } catch (IOException e) {
            LOGGER.error("解码用户信息异常", e);
            Utils.overrideResponse(response, false, "用户信息无效");
            return false;
        }

        LOGGER.info("用户信息：" + userInfo.toString());
        if (userInfo.getUserId() == null
                || !StringUtils.hasLength(userInfo.getAccountName())
                || !StringUtils.hasLength(userInfo.getRole())) {
            Utils.overrideResponse(response, false, "用户信息不全");
            return false;
        }

        if (!Role.validate(userInfo.getRole())) {
            Utils.overrideResponse(response, false, "用户角色无效");
            return false;
        }

        UserInfo existedUser = userService.queryUserById(userInfo.getUserId());
        if (null == existedUser) {
            Utils.overrideResponse(response, false, "登录用户不存在");
            return false;
        }

        if (!existedUser.equals(userInfo)) {
            Utils.overrideResponse(response, false, "用户信息不一致");
            return false;
        }

        request.setAttribute(USER_REQUEST_HEADER, userInfo);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
