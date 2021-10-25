package com.magnus.project.managee.work.controller.business;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessArchive {

    @RequestMapping("/archive/business/id/{id}")
    public void businessArchive(@PathVariable int id) {
        // 需求归档
        // todo: 查询出所有的团队-需求预评估的关系
        // todo: 查询出所有的预评估人员-需求 的关系
        // todo: 删除以上所有关系在表中的数据

    }
}
