package com.magnus.project.managee.work.service.impl;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.BusinessDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.work.entity.Business;
import com.magnus.project.managee.work.mapper.TeamUserMapper;
import com.magnus.project.managee.work.mapper.UserBusinessMapper;
import com.magnus.project.managee.work.service.UserBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBusinessServiceImpl implements UserBusinessService {

    @Autowired
    UserBusinessMapper userBusinessMapper;

    @Autowired
    TeamUserMapper teamUserMapper;

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
     *
     * @param userId  发起这个交易userid（当前团队中的某一位负责人）
     * @param businessId
     * @param managerUserId  目标牵头人（设置目标牵头人）
     */
    @Override
    @Transactional
    public void setBusinessManager(int userId, int businessId, int managerUserId) {
        // 设置需求项目经理
        // 1。将当前团队中项目经理的角色全部改为tracer
        // 修改当前团队中的负责人的需求牵头人身份为tracer
        userBusinessMapper.updateTeamManagerRoleToTracerInBusiness(userId, Constants.BUSINESS_USER_ROLE_TRACER, Constants.BUSINESS_USER_ROLE_MANAGER);
        // 设置新的需求牵头人
        userBusinessMapper.insertBusinessUser(businessId, managerUserId, Constants.BUSINESS_USER_ROLE_MANAGER);
    }

    @Override
    public void createEvaluateProblem(int businessId, int userId, String content) {
        // 创建新的问题,并设置需求反馈状态为已反馈，有问题
        userBusinessMapper.insertNewBusinessPreEvaluateProblem(businessId, userId, content);
        userBusinessMapper.updateBusinessEvaluateStatus(businessId, userId, Constants.BUSINESS_PRE_EVALUATE_STATUS_PROBLEM);
    }

    @Override
    public void commitEvaluateSuccess(int businessId, int userId, String content) {
        // 评估完成
        userBusinessMapper.updateBusinessEvaluateStatus(businessId, userId, Constants.BUSINESS_PRE_EVALUATE_STATUS_FINISHED);
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
        }
    }

    @Override
    public void commitBusinessEvaluate(Map map) {
        // 提交需求正式评估
        // 修改正式评估表数据
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        int userId = (int) map.get(UserDict.USER_ID.getStr());
        // 这里需要判空，任何数据均不能为空, 设置需求评估状态为已完成
        userBusinessMapper.modifyBusinessEvaluateTime(businessId, userId, Constants.BUSINESS_EVALUATE_FINISED);
    }
}
