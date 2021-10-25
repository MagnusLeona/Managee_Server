package com.magnus.project.managee.work.service.impl;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.TeamDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.entity.User;
import com.magnus.project.managee.work.mapper.TeamUserMapper;
import com.magnus.project.managee.work.service.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamUserServiceImpl implements TeamUserService {

    @Autowired
    TeamUserMapper teamUserMapper;

    @Override
    public List<User> selectManagersInTeam(int teamId) {
        // 查询团队中的团队负责人
        return teamUserMapper.queryUserInTeamByTeamId(teamId, Constants.TEAM_USER_ROLE_LEADER);
    }

    @Override
    public Team selectTeamByUserId(int userId) {
        List<Team> teams = teamUserMapper.selectTeamByUserId(userId);
        if(teams != null && !teams.isEmpty()) {
            return teams.get(0);
        }
        return null;
    }
}
