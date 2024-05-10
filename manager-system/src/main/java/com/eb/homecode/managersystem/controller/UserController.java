package com.eb.homecode.managersystem.controller;

import com.eb.homecode.managersystem.config.Role;
import com.eb.homecode.managersystem.model.UserInfo;
import com.eb.homecode.managersystem.service.ResourceService;
import com.eb.homecode.managersystem.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.eb.homecode.managersystem.config.Constants.USER_REQUEST_HEADER;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/{resource}")
    public ResultMessage resource(@PathVariable String resource, HttpServletRequest request) {
        //System.out.println(this.getClass().getName() + " " + request.getHeader("role-info"));

        LOGGER.info("resource：" + resource);

        String message = "访问资源 " + resource + " 成功";
        UserInfo loginUser = (UserInfo) request.getAttribute(USER_REQUEST_HEADER);
        boolean success = Role.admin.name().equals(loginUser.getRole());
        if (!success) {
            List<Integer> authorizedUsers = resourceService.queryUserIds(resource);
            success = authorizedUsers != null && authorizedUsers.contains(loginUser.getUserId());
            if (!success) {
                message = "该用户无权限访问资源 "+ resource;
            }
        }

        return new ResultMessage(success, message);
    }
}
