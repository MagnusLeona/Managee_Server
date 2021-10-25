package com.magnus.project.managee.work.service;

import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.entity.User;

import java.util.List;

public interface TeamUserService {

    public List<User> selectManagersInTeam(int teamId);

    public Team selectTeamByUserId(int userId);
}
