package com.magnus.project.managee.work.controller.user;

import com.magnus.project.managee.support.aop.aspects.annotations.LoginRequired;
import com.magnus.project.managee.work.entity.Business;
import com.magnus.project.managee.work.entity.User;
import com.magnus.project.managee.work.service.UserBusinessService;
import com.magnus.project.managee.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserBusiness {

    @Autowired
    UserBusinessService userBusinessService;

    @RequestMapping("/query/user/business")
    @LoginRequired
    public List<Business> selectUserBusiness(User user) {
        // 获取userid,根据user_id去查询客户的需求列表
        List<Business> businesses = userBusinessService.selectBusinessByUserId(user.getUserId());
        return businesses;
    };
}
