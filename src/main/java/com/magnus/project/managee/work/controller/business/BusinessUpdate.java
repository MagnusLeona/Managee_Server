package com.magnus.project.managee.work.controller.business;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.BusinessDict;
import com.magnus.project.managee.support.dicts.TeamDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.service.BusinessService;
import com.magnus.project.managee.work.service.TeamBusinessService;
import com.magnus.project.managee.work.service.TeamUserService;
import com.magnus.project.managee.work.service.UserBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BusinessUpdate {

    @Autowired
    TeamBusinessService teamBusinessService;

    @Autowired
    TeamUserService teamUserService;

    @Autowired
    UserBusinessService userBusinessService;

    @Autowired
    BusinessService businessService;

    // 需求流转（需求递交给其他人） --- 自己不再订阅消息（不再关注需求进度）
    // 将需求流转给其余团队
    @RequestMapping("/update/business/deliver")
    public void deliverBusinessToOtherTeam(@RequestBody Map map) {
        // todo: 获取操作的客户号，流转需求到其余团队仅有团队负责人才可以操作
        int id = 1; // -- 发起流转的客户号，模拟客户号为1 的用户把需求1流转给客户号为2的用户
        // 给其余团队递交需求
        Integer targetTeamId = (Integer) map.get(TeamDict.TEAM_ID.getName());  //目标团队id
        Integer businessId = (Integer) map.get(BusinessDict.BUSINESS_ID.getStr());
        // 递交需求表示自己的团队不再关注这个需求，所以需要删掉现有的关系
        // 获取当前用户的id和所在团队
        Team currTeam = teamUserService.selectTeamByUserId(id);
        int currTeamId = currTeam.getTeamId();
        // 删除当前团队-需求的关系表，同时删除当前团队中所有和这个需求有关系的用户
        teamBusinessService.deleteTeamBusiness(currTeamId, businessId);
        // 新增目标团队和需求的关系, 设置目标团队的角色为负责团队
        teamBusinessService.insertBusinessTeamAsManager(targetTeamId, businessId);
    }

    // 需求下发（需求安排给项目实施牵头人员--此人员暂时为评估者，后续变为项目牵头人） --- 自己的角色变为tracer（跟踪）
    @RequestMapping("/update/business/issue/user")
    @Transactional
    public void issueBusinessToUsers(@RequestBody Map map) {
        // todo: 获取当前操作的id
        // 需求下发给团队内其他人员进行牵头，需要进行反馈 --- 下发的时候是否需要收到反馈？怎么样去存储这样一种状态？
        // 当前团队牵头人--每个团队每个需求仅能有一个牵头人
        List<Integer> userList = (List<Integer>) map.get(UserDict.USER_ID_LIST.getStr());
        int businessId = (int) map.get(BusinessDict.BUSINESS_ID.getStr());
        for (Integer integer : userList) {
            // 设置每个相关人员角色为评估人员
            userBusinessService.insertBusinessEvaluator(integer, businessId);
        }
        // todo：维护一个需要评估的需求，维护是否完成评估的状态
        // todo：给每个涉及的人员发送通知
    }

    // 需求指派（指派给某一个团队） --- 权限暂时定位只能业务去操作
    @RequestMapping("/update/business/assign")
    public void assignBusiness(@RequestBody Map map) {
        // todo: 权限校验
        // 指派给团队的时候，会自动创建负责人信息为团队负责人
        // 步骤： 获取businessId,获取团队Id
        // teamRole： team所表演的角色
        Integer teamId = (Integer) map.get(TeamDict.TEAM_ID.getName());
        Integer businessId = (Integer) map.get(BusinessDict.BUSINESS_ID.getStr());
        // 指派需求的时候，被指派的团队的角色默认选择为----总负责，同时更新团队负责人和需求的关系
        teamBusinessService.insertBusinessTeamAsManager(teamId, businessId);
        // 指派需求的时候，设置需求状态为流转中  2
        businessService.updateBusinessStatus(businessId, Constants.BUSINESS_STATUS_ASSIGN);
        // todo: 发消息给订阅的人 --- 更新 通知-用户关系表, 发消息通知
    }
}
