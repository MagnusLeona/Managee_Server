package com.magnus.project.managee.work.controller.mission;

import com.magnus.project.managee.work.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MissionInsert {

    @Autowired
    MissionService missionService;

    public void addProjectMission(@RequestBody Map map) {
        // 新增任务，由具体的开发执行
        missionService.insertMissionDevs(map);
    }
}
