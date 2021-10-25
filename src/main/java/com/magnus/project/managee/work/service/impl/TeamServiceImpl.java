package com.magnus.project.managee.work.service.impl;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.TeamDict;
import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.mapper.TeamMapper;
import com.magnus.project.managee.work.service.TeamService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    @Qualifier("batch")
    SqlSession sqlSession;

    @Override
    public List<Team> selectTeams(int start, int offset) {
        return teamMapper.selectTeams(start, offset);
    }

    @Override
    public List<Team> selectTeamsById(int id) {
        return teamMapper.selectTeamsById(id);
    }

    @Override
    public void insertTeam(Map map) {
        map.put(TeamDict.AUTO_INCREMENT_ID.getName(), Constants.DEFAULT_INTEGER_ZERO);
        teamMapper.insertTeam(map);
        Integer id = (Integer) map.get(TeamDict.AUTO_INCREMENT_ID.getName());
        // 如果设置了团队负责人，则需要插入用户-团队关系表
        List<Integer> leaderList = (List<Integer>) map.get(TeamDict.TEAM_LEADERS.getName());
        List<Integer> memberList = (List<Integer>) map.get(TeamDict.TEAM_MEMBERS.getName());

        TeamMapper mapper = sqlSession.getMapper(TeamMapper.class);
        if(leaderList != null && !leaderList.isEmpty()) {
            Map leader = new HashMap();
            leader.put(TeamDict.TEAM_ID.getName(), id);
            leader.put(TeamDict.TEAM_USER_ROLE.getName(), Constants.TEAM_USER_ROLE_LEADER);
            // 批量插入团队-负责人关系表
            for (Integer integer : leaderList) {
                leader.put(TeamDict.TEAM_USER_ID.getName(), integer);
                mapper.insertTeamUser(leader);
            }
        }

        if(memberList != null && !memberList.isEmpty()) {
            // 批量插入团队-成员关系表
            Map member = new HashMap();
            member.put(TeamDict.TEAM_ID.getName(), id);
            member.put(TeamDict.TEAM_USER_ROLE.getName(), Constants.TEAM_USER_ROLE_MEMBER);
            // 批量插入团队-成员关系表
            for (Integer integer : memberList) {
                member.put(TeamDict.TEAM_USER_ID.getName(), integer);
                mapper.insertTeamUser(member);
            }
        }
    }
}
