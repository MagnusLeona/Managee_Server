package com.magnus.project.managee.work.controller.business;

import com.magnus.project.managee.support.dicts.BusinessDict;
import com.magnus.project.managee.support.dicts.MissionDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.work.service.MissionService;
import com.magnus.project.managee.work.service.TeamBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BusinessTest {

    @Autowired
    MissionService missionService;

    @Autowired
    TeamBusinessService teamBusinessService;

    @RequestMapping("/business/id/{businessId}/test/evaluate")
    public void evaluateBusinessTest(@PathVariable int businessId) {
        // 检查评估完成所有的mission排期，即：完成对需求的测试评估反馈
        // 修改需求状态为测试评估完成
        // 修改需求-测试表中的状态
        // 获取到testId之后，修改状态，并生成mission-test单号
        teamBusinessService.finishTestEvaluate(businessId);
        // todo:给总牵头人和所有涉及的人员发送评估完成的通知
    }

    @RequestMapping("/mission/test/evaluate/assign")
    public void assignTestEvaluate(@RequestBody Map map) {
        // 分发测试评估到团队内的测试人员
        // 以任务为维度进行分配
        Integer missionId = (Integer) map.get(MissionDict.MISSION_ID.getValue());
        List<Integer> userIdList = (List<Integer>) map.get(UserDict.USER_ID_LIST.getStr());
        // 设置任务状态为已提交测试评估
        missionService.insertMissionTest(missionId, userIdList);
    }

    @RequestMapping("/mission/test/evaluate/finish")
    public void evaluateMissionTestFinishTime(@RequestBody Map map) {
        // 更新任务对应的计划完成时间
        int missionId = (int) map.get(MissionDict.MISSION_ID.getValue());
        String time = (String) map.get(MissionDict.MISSION_PLAN_TEST_FINISH_TIME.getValue());
        // 修改任务的计划完成测试日期
        missionService.updateMissionPlanTestFinishTime(missionId, time);
    }

    @RequestMapping("/mission/id/{missionId}/test/finish")
    public void commitMissionTestSuccess(@PathVariable int missionId) {
        missionService.finishMissionTest(missionId);
    }

    @RequestMapping("/mission/id/{missionId}/test/commit")
    public void commitMissionTest(@PathVariable int missionId) {
        // 任务提交测试
        // 找到单号对应的实施团队对应的团队负责人
        missionService.commitMissionTest(missionId);
        // todo: 给涉及的相关人员发送通知
    }
}
