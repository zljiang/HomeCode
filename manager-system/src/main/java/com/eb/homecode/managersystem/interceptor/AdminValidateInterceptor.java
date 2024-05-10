package com.eb.homecode.managersystem.interceptor;

import com.eb.homecode.managersystem.config.Role;
import com.eb.homecode.managersystem.model.UserInfo;
import com.eb.homecode.managersystem.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.eb.homecode.managersystem.config.Constants.USER_REQUEST_HEADER;

public class AdminValidateInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminValidateInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UserInfo userInfo = (UserInfo) request.getAttribute(USER_REQUEST_HEADER);
        LOGGER.info("用户角色：" + userInfo.getRole());

        if (!Role.admin.name().equals(userInfo.getRole())) {
            Utils.overrideResponse(response, false, "用户无权限执行该操作");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
