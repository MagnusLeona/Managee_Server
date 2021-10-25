package com.magnus.project.managee.work.controller.team;

import com.magnus.project.managee.support.dicts.TeamDict;
import com.magnus.project.managee.work.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TeamInsert {

    @Autowired
    TeamService teamService;

    @RequestMapping("/create/team")
    public void insertTeam(@RequestBody Map map) {
        // 新增团队，需要设置新增团队的关系表
        teamService.insertTeam(map);
    }
}
