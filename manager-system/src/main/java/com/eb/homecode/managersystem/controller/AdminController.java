package com.eb.homecode.managersystem.controller;

import com.eb.homecode.managersystem.service.UserService;
import com.eb.homecode.managersystem.vo.ResultMessage;
import com.eb.homecode.managersystem.vo.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResultMessage addUser(@RequestBody UserParam userParam) {
        userService.save(userParam);
        return new ResultMessage(true, "添加用户成功");
    }
}
