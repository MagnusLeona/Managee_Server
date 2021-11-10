package com.magnus.project.managee.work.service;

import java.util.List;
import java.util.Map;

public interface MissionService {

    public void insertMissionDevs(Map map);

    public void updateMissionPlanTestFinishTime(int missionId, String time);

    public void commitMissionTest(int missionId);

    public void insertMissionTest(int missionId, List<Integer> userIdList);

    public void finishMissionTest(int missionId);
}
