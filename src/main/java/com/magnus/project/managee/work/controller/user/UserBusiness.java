package com.magnus.project.managee.work.controller.user;

import com.magnus.project.managee.work.entity.Business;
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
    public List<Business> selectUserBusiness(Map map) {
        // 获取userid,根据user_id去查询客户的需求列表
        // todo: 调用这个交易的客户号作为查询条件
        List<Business> businesses = userBusinessService.selectBusinessByUserId(1);
        return businesses;
    };
}
