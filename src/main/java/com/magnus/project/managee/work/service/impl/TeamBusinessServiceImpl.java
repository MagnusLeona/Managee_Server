package com.magnus.project.managee.work.service.impl;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.work.entity.User;
import com.magnus.project.managee.work.mapper.TeamBusinessMapper;
import com.magnus.project.managee.work.mapper.TeamUserMapper;
import com.magnus.project.managee.work.mapper.UserBusinessMapper;
import com.magnus.project.managee.work.service.TeamBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamBusinessServiceImpl implements TeamBusinessService {

    @Autowired
    TeamBusinessMapper teamBusinessMapper;

    @Autowired
    UserBusinessMapper userBusinessMapper;

    @Autowired
    TeamUserMapper teamUserMapper;

    @Override
    public void insertTeamBusiness(int teamId, int businessId, int teamRole) {
        teamBusinessMapper.insertBusinessTeam(teamId, businessId, teamRole);
    }

    @Override
    public void insertBusinessTeamAsManager(int teamId, int businessId) {
        // 设置团队为总负责团队
        teamBusinessMapper.insertBusinessTeam(teamId, businessId, Constants.BUSIENSS_TEAM_ROLE_MANAGER);
        // 查询所有的团队负责人
        List<User> users = teamUserMapper.queryUserInTeamByTeamId(teamId, Constants.TEAM_USER_ROLE_LEADER);
        for (User user : users) {
            // 更新团队负责人和需求的关系
            userBusinessMapper.insertBusinessUser(businessId, user.getUserId(), Constants.BUSINESS_USER_ROLE_MANAGER);
        }
    }

    @Override
    public void insertBusinessTeamAsSupport(int teamId, int businessId) {
        // 设置团队为支持团队
        teamBusinessMapper.insertBusinessTeam(teamId, businessId, Constants.BUSINESS_TEAM_ROLE_SUPPORTER);
        List<User> users = teamUserMapper.queryUserInTeamByTeamId(teamId, Constants.TEAM_USER_ROLE_LEADER);
        for (User user : users) {
            // 其余团队负责人为协负责人.同时总负责人也负责自己团队的安排
            userBusinessMapper.insertBusinessUser(businessId, user.getUserId(), Constants.BUSINESS_USER_ROLE_SUPPORT_MANAGER);
        }
    }

    @Override
    @Transactional
    public void deleteTeamBusiness(int teamId, int businessId) {
        teamBusinessMapper.deleteTeamBusiness(teamId, businessId);
        // 删除的时候需要同步删除掉用户-需求关系表
        userBusinessMapper.deleteBusinessUser(businessId, teamId);
    }

    @Override
    public void insertTeamEvaluateMission(int businessId, int teamId) {
        // 更新团队-需求的预评估关系到表中，表示团队暂未反馈预评估结果
        teamBusinessMapper.insertBusinessEvaluateTeam(businessId, teamId, Constants.BUSINESS_PRE_EVALUATE_STATUS_ASSIGNED);
    }

    @Override
    public void declineSupportEvaluate(int businessId, int teamId, String reason) {
        // 拒绝需求
        teamBusinessMapper.updateBusinessEvaluateTeamAsDecline(businessId, teamId, reason);
    }
}
