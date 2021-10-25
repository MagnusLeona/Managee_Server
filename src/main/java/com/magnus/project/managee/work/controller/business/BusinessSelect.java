package com.magnus.project.managee.work.controller.business;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.CommonDict;
import com.magnus.project.managee.support.dicts.ResultDict;
import com.magnus.project.managee.work.entity.Business;
import com.magnus.project.managee.work.service.BusinessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BusinessSelect {

    public Logger logger = LogManager.getLogger(BusinessSelect.class);

    @Autowired
    BusinessService businessService;

    @RequestMapping({"/query/businesses"})
    public Map selectBusinesses(@RequestBody Map queryMap) {
        int page = queryMap.containsKey(CommonDict.PAGE.getName()) ? (int) queryMap.get(CommonDict.PAGE.getName()) : Constants.DEFAULT_PAGE;
        int pageSize = queryMap.containsKey(CommonDict.PAGE_SIZE.getName()) ? (int) queryMap.get(CommonDict.PAGE_SIZE.getName()) : Constants.DEFAULT_PAGE_SIZE;
        // 根据页码和页数去查询数据
        int start = (page - 1) * 10;
        List<Business> businesses = businessService.selectBusinesses(start, pageSize);
        int total = businessService.selectBusinessCount();
        int totalPages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        Map resultMap = new HashMap();
        resultMap.put(ResultDict.BUSINESS_LIST.getName(), businesses);
        resultMap.put(ResultDict.TOTAL_PAGES, totalPages);
        return resultMap;
    }

    @RequestMapping({"/query/businesses/name/{name}"})
    public Business selectBusinessesByName(@PathVariable String name) {
        // 根据给出的名称进行模糊查找；

        return null;
    }

    @RequestMapping({"/query/businesses/id/{id}"})
    public Business selectBusinessesById(@PathVariable Integer id) {
        List<Business> businesses = businessService.selectBusinessesById(id);
        if (businesses.isEmpty()) {
            return null;
        }
        return businesses.get(0);
    }
}
