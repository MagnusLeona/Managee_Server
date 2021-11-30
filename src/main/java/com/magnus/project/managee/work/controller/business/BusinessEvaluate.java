package com.magnus.project.managee.work.controller.business;

import com.magnus.project.managee.support.aop.aspects.annotations.LoginRequired;
import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.BusinessDict;
import com.magnus.project.managee.support.dicts.TeamDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.entity.User;
import com.magnus.project.managee.work.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BusinessEvaluate {

    @Autowired
    TeamBusinessService teamBusinessService;

    @Autowired
    TeamUserService teamUserService;

    @Autowired
    UserBusinessService userBusinessService;

    @Autowired
    BusinessService businessService;

    @Autowired
    MissionService missionService;

    @RequestMapping("/business/accept/id/{businessId}")
    @LoginRequired
    public void acceptBusiness(@PathVariable int businessId, User user) {
        int userId = user.getUserId();
        // 团队负责人接受需求委托
        // 更新需求状态为 待安排预评估-3
        businessService.updateBusinessStatus(businessId, Constants.BUSINESS_STATUS_WAIT_FOR_PRE_EVALUATE);
        // 获取当前用户所处的团队id
        Team team = teamUserService.selectTeamByUserId(userId);
        // 接受需求的时候，需求状态已经改为预评估，则需要维护团队-预评估状态表
        teamBusinessService.insertTeamEvaluateMission(businessId, team.getTeamId());
        // todo：通知上游用户需求状态变更-给业务发消息
    }

    // 需求分发 -- 需求分发给其余团队并行预评估，设置其余团队为Support角色
    @RequestMapping("/preevaluate/business/issue/team")
    @Transactional
    public void issueBusinessToSupportTeams(@RequestBody Map map) {
        // 更新团队-需求关系表
        List<Integer> teamIdList = (List) map.get(TeamDict.TEAM_ID_LIST.getName());
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID);
        for (int teamId : teamIdList) {
            // 修改需求关系表,设置团队的角色为支持（配合）
            teamBusinessService.insertBusinessTeamAsSupport(teamId, businessId);
            // 维护相关团队的一个是否完成反馈的表
            teamBusinessService.insertTeamEvaluateMission(businessId, teamId);
        }
        // todo: 给相关团队负责人发送通知： 收到需要配合的需求
    }

    // 设置需求在自己团队中的负责人
    @RequestMapping("/business/preevaluate/arrange/manager")
    @LoginRequired
    public void arrangeBusinessManager(@RequestBody Map map, User user) {
        int userId = user.getUserId();
        int managerUserId = (int) map.get(UserDict.USER_ID.getStr());
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        userBusinessService.setBusinessManager(userId, businessId, managerUserId);
    }

    // 需求牵头人设定评估人员
    @RequestMapping("/business/preevaluate/arrange/evaluater")
    public void arrangeBusinessEvaluater(@RequestBody Map map) {
        List<Integer> userIdList = (List<Integer>) map.get(UserDict.USER_ID_LIST.getStr());
        Integer businessId = (Integer) map.get(BusinessDict.BUSINESS_ID.getStr());
        for (Integer integer : userIdList) {
            userBusinessService.insertBusinessEvaluator(integer, businessId);
            // todo: 发送预评估通知给分配的人员
        }
    }

    @RequestMapping("/business/preevaluate/decline}")
    public void declineBusinessSupport(@RequestBody Map map) {
        // todo：获取这个客户的id/所在团队
        int id = 1;
        // 拒绝配合业务需求
        // 预评估状态表中，将反馈状态改为拒绝
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        String feedback = (String) map.get(BusinessDict.BUSINESS_EVALUATE_FEEDBACK.getStr());
        Team team = teamUserService.selectTeamByUserId(id);
        // 需求拒绝
        teamBusinessService.declineSupportEvaluate(businessId, team.getTeamId(), feedback);
        // todo: 给总牵头团队发消息
    }

    // 需求预评估遇到暂时无法解决的问题，反馈给业务确认
    @RequestMapping("/preevaluate/business/update/problem")
    public void commitEvaluateProblem(@RequestBody Map map) {
        // todo: 获取当前交易的操作userid
        int userId = 1;
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        String content = (String) map.get(BusinessDict.BUSINESS_EVALUATE_PROBLEM.getStr());
        // 反馈评估问题
        // 创建评估问题
        userBusinessService.createEvaluateProblem(businessId, userId, content);
        // todo: 给需求负责人发送通知
        // todo: 是否每次有提交评估结果的交易就触发一次整体需求评估完成的检查？
    }

    // 需求预评估可行，暂无其余问题
    @RequestMapping("/preevaluate/business/update/success")
    public void commitPreEvaluateSuccess(@RequestBody Map map) {
        // todo: 获取执行此交易的用户id
        int userId = 1;
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        String content = (String) map.get(BusinessDict.BUSINESS_EVALUATE_PROBLEM.getStr());
        userBusinessService.commitEvaluateSuccess(businessId, userId, content);
        // todo: 发送通知给相关负责人
        // todo：是否每次检查此团队是否完成评估？
    }

    @RequestMapping("/preevaluate/business/update/finish")
    public void finishBusinessPreEvaluate(@RequestBody Map map) {
        // 完成需求预评估流程，开始正式评估流程---修改需求状态为正式评估
        // 修改需求状态为正式评估
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        // 正式评估完成的时候，需要把需求预评估相关的数据删除(需求归档的时候再删除把)。
        // todo: 需求归档的时候删除所有记录
        businessService.updateBusinessStatus(businessId, Constants.BUSINESS_STATUS_EVALUATE);
        // 将所有涉及的预评估人员身份转移到正式评估表中
        userBusinessService.initBusinessEvaluate(businessId);
        // todo: 发送通知给所有的预评估人员，开始正式评估反馈排期
        // 更新需求-实施单元表
        // todo: 更新待办事项到用户-待办事项表中
    }

    @RequestMapping("/evaluate/business/create/project")
    public void assignBusinessEvalauteToDevs(@RequestBody Map map) {
        // 新增实施单元-挂载到需求上的项目,并分发给开发进行评估
        // todo：获取操作的userId
        int userId = 3;
        // 将需求分配给相关开发人员进行任务添加
        // 获取分发的人员列表
        List<Integer> userIdList = (List<Integer>) map.get(UserDict.USER_ID_LIST.getStr());
        // 每个人员需要分配对应的project_id
        int projectId = (int) map.get(BusinessDict.BUSINESS_PROJECT_ID.getStr());
        userBusinessService.assignBusinessProjectEvaluate(projectId, userIdList);
        // todo:消息通知各个开发人员进行评估
    }

    @RequestMapping("/evaluate/business/create/mission")
    public void createProjectMission(@RequestBody Map map) {
        // 新增各种需求
        // 获取开发人列表
        /*
        {
            userIdList: [],  -- 开发人员列表
            projectId: 1 -- projectId
        }
         */
        missionService.insertMissionDevs(map);
    }

    @RequestMapping("/evaluate/business/commit/dev/evaluate")
    public void commitDevBusinessProjectEvaluate(@RequestBody Map map) {
        // 开发确认评估完成
        // todo:获取操作此交易的客户号
        int userId = 3;
        int projectId = (int) map.get(BusinessDict.BUSINESS_PROJECT_ID.getStr());
        userBusinessService.finishBusinessProjectDevEvaluate(projectId, userId);
        // todo: 给负责人发送通知
        // todo: 分析是否已经完成了全部的评估
    }

    public void commitManagerBusinessProjectEvaluate(@RequestBody Map map) {
        // 完成此需求实施单元的整体评估
        int userId = 3;
        int projectId = (int) map.get(BusinessDict.BUSINESS_PROJECT_ID.getStr());
        // 修改project的状态，并删除businessProjectEvalaute表中的相关评估状态
        userBusinessService.finishBusinessProjectManagerEvaluate(projectId, userId);
    }

    @RequestMapping("/evaluate/business/finish")
    public void commitEvaluate(@RequestBody Map map) {
        // 提交正式评估的结果，每个评估者的角度
        // todo: 获取到操作的userId
        int userId = 1;
        // 更新需求评估日期
        map.put(UserDict.USER_ID.getStr(), userId);
        userBusinessService.commitBusinessEvaluate(map);
    }

    @RequestMapping("/evaluate/business/apply/test")
    public void deliverEvaluateToTestTeam(@RequestBody Map map) {
        // 将需求评估内容提交到测试团队进行评估 --- 由需求总牵头人负责
        // 只需要告诉测试团队需求编号，测试团队直接获取表中的数据
        // todo: 维护一个测试团队的表？
        // 先更新测试表
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        int teamId = (int) map.get(TeamDict.TEAM_ID.getName());
        teamBusinessService.createTestOrder(businessId, teamId);
        // todo: 发送通知给测试团队
        // 设置需求状态为测试评估中
        businessService.updateBusinessStatus(businessId, Constants.BUSINESS_STATUS_TEST_EVALUATE);
    }
}
