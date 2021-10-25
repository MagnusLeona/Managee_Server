package com.magnus.project.managee.work.service;

import com.magnus.project.managee.work.entity.Team;

import java.util.List;
import java.util.Map;

public interface TeamService {

    public List<Team> selectTeams(int start, int offset);

    public List<Team> selectTeamsById(int id);

    public void insertTeam(Map map);
}
