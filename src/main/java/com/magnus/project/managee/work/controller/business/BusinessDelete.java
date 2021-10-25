package com.magnus.project.managee.work.controller.business;

import com.magnus.project.managee.work.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessDelete {

    @Autowired
    BusinessService businessService;

    @RequestMapping("/delete/business/id/{id}")
    public void deleteBusinessById(@PathVariable int id) {
        // 删除id的需求
        businessService.deleteBusinessByBusinessId(id);
    }
}
