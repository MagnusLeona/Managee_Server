package com.magnus.project.managee.work.service.impl;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.BusinessDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.work.entity.Business;
import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.mapper.*;
import com.magnus.project.managee.work.service.UserBusinessService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBusinessServiceImpl implements UserBusinessService {

    @Autowired
    UserBusinessMapper userBusinessMapper;

    @Autowired
    TeamUserMapper teamUserMapper;

    @Autowired
    TeamBusinessMapper teamBusinessMapper;

    @Autowired
    MissionMapper missionMapper;

    @Autowired
    BusinessMapper businessMapper;

    @Override
    public List<Business> selectBusinessByUserId(int userId) {
        List<Business> businesses = userBusinessMapper.selectUserBusiness(userId);
        return businesses;
    }

    @Override
    public void insertBusinessManager(int userId, int businessId) {
        // 更新需求-团队负责人
        userBusinessMapper.insertBusinessUser(businessId, userId, Constants.BUSINESS_USER_ROLE_MANAGER);
    }

    @Override
    public void insertBusinessEvaluator(int userId, int businessId) {
        // 设置需求评估人员
        userBusinessMapper.insertBusinessUser(businessId, userId, Constants.BUSINESS_USER_ROLE_EVALUATOR);
        // 写入评估表数据,评估人员+需求+评估状态
        userBusinessMapper.insertBusinessPreEvaluateUser(businessId, userId, Constants.BUSINESS_PRE_EVALUATE_STATUS_ASSIGNED);
    }

    @Override
    public void setRoleFromManagerToTracer(int userId, int businessId) {
        userBusinessMapper.updateBusinessUserRole(userId, businessId, Constants.BUSINESS_USER_ROLE_TRACER);
    }

    /**
     * @param userId        发起这个交易userid（当前团队中的某一位负责人）
     * @param businessId
     * @param managerUserId 目标牵头人（设置目标牵头人）
     */
    @Override
    @Transactional
    public void setBusinessManager(int userId, int businessId, int managerUserId) {
        // aim: 设置需求项目经理
        // 指定需求负责人分为两种，一个是总牵头团队的负责人，同时也会作为总的需求负责人。另一个是协助团队的负责人，这时候指定的需求负责人是协负责人，两个角色对应的role不一样。
        // 判断当前团队在需求中的角色
        int teamId = teamUserMapper.selectTeamIdByUserId(userId);
        int teamRole = teamBusinessMapper.selectTeamRoleInBusiness(businessId, teamId);
        // todo: 判断设置的角色是否属于当前团队
        if (teamRole == Constants.BUSINESS_TEAM_ROLE_MANAGER) {
            // 如果当前团队在需求中的角色为总负责团队
            // 则当前设置的需求负责人就是需求的总负责人
            // 设置user-business表
            userBusinessMapper.insertBusinessUser(businessId, managerUserId, Constants.BUSINESS_USER_ROLE_MANAGER);
        } else if (teamRole == Constants.BUSINESS_TEAM_ROLE_SUPPORTER) {
            // 如果当前团队为协助团队，则这个这个团队内的需求负责人就是协负责人
            userBusinessMapper.insertBusinessUser(businessId, managerUserId, Constants.BUSINESS_USER_ROLE_SUPPORT_MANAGER);
        }
        // 将当前团队中项目经理的角色全部改为tracer
        userBusinessMapper.updateTeamManagerRoleToTracerInBusiness(userId, teamId, Constants.BUSINESS_USER_ROLE_TRACER, Constants.TEAM_USER_ROLE_LEADER);
    }

    @Override
    public void createEvaluateProblem(int businessId, int userId, String content) {
        // 创建新的问题,并设置需求反馈状态为已反馈，有问题
        userBusinessMapper.insertNewBusinessPreEvaluateProblem(businessId, userId, content);
        userBusinessMapper.updateBusinessPreEvaluateStatus(businessId, userId, Constants.BUSINESS_PRE_EVALUATE_STATUS_PROBLEM);
    }

    @Override
    public void commitEvaluateSuccess(int businessId, int userId, String content) {
        // 评估完成
        userBusinessMapper.updateBusinessPreEvaluateStatus(businessId, userId, Constants.BUSINESS_PRE_EVALUATE_STATUS_FINISHED);
    }

    @Override
    public void initBusinessEvaluate(int businessId) {
        // 初始化需求正式评估
        // 设置当前需求状态为正式评估
        // 先取出所有的预评估人员
        List<Integer> preEvaluaters = userBusinessMapper.selectBusinessPreEvaluateUsers(businessId);
        for (Integer evaluaterId : preEvaluaters) {
            // 更新到正式评估的表中,设置所有的初始计划时间为空,  状态标记为评估开始
            userBusinessMapper.insertBusinessEvaluateUser(businessId, evaluaterId, Constants.BUSINESS_EVALUATE_START);
            //  给每个评估人员都新建一个实施单元project
            HashMap<Object, Object> project = new HashMap<>();
            project.put(BusinessDict.BUSINESS_ID.getStr(), businessId);
            project.put(UserDict.USER_ID.getStr(), evaluaterId);
            businessMapper.createBusinessProject(project);
            // 更新团队-实施单元表 -- 迁移到点击完成的时候加？
            int teamId = teamUserMapper.selectTeamIdByUserId(evaluaterId);
            businessMapper.createBusinessProjectTeam((Integer) project.get(BusinessDict.BUSINESS_PROJECT_ID.getStr()), teamId);
        }
    }

    @Override
    public void commitBusinessEvaluate(Map map) {
        // 提交需求正式评估---评估者身份
        // 修改正式评估表数据
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        int userId = (int) map.get(UserDict.USER_ID.getStr());
        // 设置需求状态为评估已完成
        businessMapper.updateBusinessStatus(businessId, Constants.BUSINESS_STATUS_TEST_EVALUATE_FINISHED);
    }

    @Override
    public void assignBusinessProjectEvaluate(int projectId, List<Integer> userList) {
        // 设置分配的人员
        for (Integer integer : userList) {
            userBusinessMapper.insertBusinessProjectDevEvaluate(projectId, integer, Constants.BUSINESS_PROJECT_STATUS_EVALUATED_START);
        }
    }

    @Override
    public void finishBusinessProjectDevEvaluate(int projectId, int userId) {
        // 设置Dev评估已完成 --开发评估
        userBusinessMapper.updateBusinessProjectDevEvaluate(projectId, userId, Constants.BUSINESS_PROJECT_STATUS_EVALUATED_FINISHED);
    }

    @Override
    public void finishBusinessProjectManagerEvaluate(int projectId, int userId) {
        // 设置Project评估已完成
        businessMapper.updateBusinessProjectStatus(projectId, Constants.BUSINESS_PROJECT_STATUS_EVALUATED_FINISHED);
        // 评估完成的话需要删除dev评估任务表
        userBusinessMapper.deleteBusinessProjectDevEvaluate(projectId);
    }
}
