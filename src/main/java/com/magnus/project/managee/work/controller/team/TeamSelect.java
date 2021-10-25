package com.magnus.project.managee.work.controller.team;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.CommonDict;
import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TeamSelect {

    @Autowired
    TeamService teamService;

    @RequestMapping("/query/teams")
    public List<Team> selectTeams(@RequestBody Map queryMap) {
        int page = queryMap.containsKey(CommonDict.PAGE.getName()) ? (int) queryMap.get(CommonDict.PAGE.getName()) : Constants.DEFAULT_PAGE;
        int pageSize = queryMap.containsKey(CommonDict.PAGE_SIZE.getName()) ? (int) queryMap.get(CommonDict.PAGE_SIZE.getName()) : Constants.DEFAULT_PAGE_SIZE;
        int start = (page - 1) * pageSize;
        return teamService.selectTeams(start, pageSize);
    }

    @RequestMapping("/query/teams/id/{id}")
    public List<Team> selectTeamById(@PathVariable Integer id) {
        return teamService.selectTeamsById(id);
    }
}
