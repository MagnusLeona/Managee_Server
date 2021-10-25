package com.magnus.project.managee.work.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magnus.project.managee.work.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserInsert {

    Logger logger = LogManager.getLogger(UserInsert.class);

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    // 使用HandlerMethodArgumentResolver将输入的用户信息转成User对象？
    @RequestMapping("/insert/user")
    public void register(@RequestBody Map map) {
        // 插入用户数据
        userService.insertUser(map);
    }

    @RequestMapping("/insert/users")
    public void insertUser() {

    }
}
