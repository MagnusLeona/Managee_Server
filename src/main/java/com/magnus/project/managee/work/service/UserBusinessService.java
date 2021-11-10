package com.magnus.project.managee.work.service;

import com.magnus.project.managee.work.entity.Business;

import java.util.List;
import java.util.Map;

public interface UserBusinessService {
    public List<Business> selectBusinessByUserId(int userId);

    // 更新需求-负责人表
    public void insertBusinessManager(int userId, int businessId);

    // 更新需求-评估人员 表
    public void insertBusinessEvaluator(int userId, int businessId);

    public void setRoleFromManagerToTracer(int userId, int businessId);

    public void setBusinessManager(int userId, int businessId, int managerUserId);

    public void createEvaluateProblem(int businessId, int userId, String content);

    public void commitEvaluateSuccess(int businessId, int userId, String content);

    public void initBusinessEvaluate(int businessId);

    public void commitBusinessEvaluate(Map map);

    public void assignBusinessProjectEvaluate(int projectId, List<Integer> userList);

    public void finishBusinessProjectDevEvaluate(int projectId, int userId);

    public void finishBusinessProjectManagerEvaluate(int projectId, int userId);
}
