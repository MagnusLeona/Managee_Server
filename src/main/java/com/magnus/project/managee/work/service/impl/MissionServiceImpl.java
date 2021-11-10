package com.magnus.project.managee.work.service.impl;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.MissionDict;
import com.magnus.project.managee.support.dicts.UserDict;
import com.magnus.project.managee.work.mapper.MissionMapper;
import com.magnus.project.managee.work.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    MissionMapper missionMapper;

    @Autowired
    SimpleDateFormat simpleDateFormat;

    @Override
    public void insertMissionDevs(Map map) {
        map.put(MissionDict.MISSION_STATUS.getValue(), Constants.MISSION_STATUS_START);
        // 先创建任务
        missionMapper.insertMission(map);
        // 再更新任务-开发关系表，获取开发列表
        int  missionId = (int) map.get(MissionDict.MISSION_ID.getValue());
        List<Integer> userIdList = (List<Integer>) map.get(UserDict.USER_ID_LIST.getStr());
        for (Integer integer : userIdList) {
            missionMapper.insertMissionUser(missionId, integer, Constants.MISSION_USER_ROLE_DEV);
        }
    }

    @Override
    public void updateMissionPlanTestFinishTime(int missionId, String time) {
        // 更新任务状态为已完成测试评估
        missionMapper.updateMissionTestFinishTime(missionId, time, Constants.MISSION_STATUS_EVALUATE_FINISHED);
    }

    @Override
    public void commitMissionTest(int missionId) {
        // 更新任务状态为测试中
        missionMapper.updateMissionActualTestCommitTime(missionId, simpleDateFormat.format(System.currentTimeMillis()), Constants.MISSION_STATUS_TEST_COMMITED);
        // 更新任务实际提交测试时间为当前日期
        // 查出来测试团队负责人的id
        List<Integer> managers = missionMapper.selectTeamManagerIdInTestTeamByTableTestOrder(missionId);
        // 查出测试单号
        List<Integer> orders = missionMapper.selectOrderIdByMissionId(missionId);
        if(orders.isEmpty()) {
            throw  new NullPointerException();
        }
        Integer orderId = orders.get(0);
        // 更新managee_business_test_order_user表中的数据
        for (Integer manager : managers) {
            missionMapper.insertMissionTestOrderUser(manager, orderId);
        }
    }

    @Override
    public void insertMissionTest(int missionId, List<Integer> userIdList) {
        // 设置任务状态为已提交测试评估
        missionMapper.updateMissionStatus(missionId, Constants.MISSION_STATUS_EVALUATE_COMMITED);
        // 给任务添加相关测试人员-- 更新任务-测试关系表
        for (Integer integer : userIdList) {
            missionMapper.insertMissionUser(missionId, integer, Constants.MISSION_USER_ROLE_TEST);
        }
    }

    @Override
    public void finishMissionTest(int missionId) {
        // 更新任务状态为已完成测试
        missionMapper.updateMissionStatus(missionId, Constants.MISSION_STATUS_TEST_FINISHED);
    }
}
