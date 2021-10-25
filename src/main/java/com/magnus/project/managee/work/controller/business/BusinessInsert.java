package com.magnus.project.managee.work.controller.business;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.BusinessDict;
import com.magnus.project.managee.work.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BusinessInsert {

    @Autowired
    BusinessService businessService;

    @RequestMapping("/create/business")
    public void createBusiness(@RequestBody Map business) {
        // 插入需求
        // 新增需求的时候，只需要上送需求名称，需求相关材料即可,服务端做补充： 修改业务-需求关系表
        // todo：获取提交需求的用户id -- 假定为3
        List<Integer> arr = new ArrayList<>();
        arr.add(3);
        business.put(BusinessDict.BUSINESS_COUNTER.getStr(), arr);
        // 设置需求的状态为创建
        business.put(BusinessDict.BUSINESS_STATUS.getStr(), Constants.BUSINESS_STATUS_CREATE);
        //  设置需求的创建时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        business.put(BusinessDict.BUSINESS_CREATE_TIME.getStr(), simpleDateFormat.format(System.currentTimeMillis()));
        businessService.insertBusiness(business);
    }
}
