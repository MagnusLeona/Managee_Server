package com.magnus.project.managee.work.mapper;

import com.magnus.project.managee.work.entity.Mission;
import com.magnus.project.managee.work.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface MissionMapper {

    @Insert("insert into managee_business_project_mission(code, name,status, project_id, work_load, plan_start_time, plan_test_commit_time, plan_test_finish_time, plan_product_time, actual_start_time, actual_test_commit_time, actual_test_finish_time, actual_product_time) value(" +
            "#{code},#{name},#{status},#{projectId},#{workLoad},#{planStartTime},#{planTestCommitTime},#{planTestFinishTime},#{planProductTime},#{actualStartTime},#{actualTestCommitTime},#{actualTestFinishTime},#{actualProductTime}" +
            ")")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "missionId")
    public void insertMission(Map map);

    @Update("alter table managee_business_project_mission set actual_test_commit_time=#{time}, status=#{status} where id=#{missionId}")
    public void updateMissionActualTestCommitTime(int missionId, String time, int status);

    @Insert("insert into managee_business_project_mission_user value(#{missionId}, #{userId}, #{role})")
    public void insertMissionUser(int missionId, int userId, int role);

    @Update("alter table managee_business_project_mission set plan_test_finish_time=#{finishTime}, status=#{status} where id=#{missionId}")
    public void updateMissionTestFinishTime(int missionId, String finishTime, int status);

    @Select("select mbpm.* from managee_business_project_mission mbpm, managee_business_project mbp where mbp.business_id=#{businessId} and mbpm.project_id=mbp.id")
    public List<Mission> selectMissionsByBusinessId(int businessId);

    @Insert("insert into managee_business_test_order(mission_id, status) value(#{missionId},#{status})")
    public void insertMissionTestOrder(int businessId, int missionId, int status);

    @Select("select mission_id from managee_business_test_order where mission_id=#{missionId}")
    public List<Integer> selectOrderIdByMissionId(int missionId);

    @Insert("insert into managee_business_test_order_user value(#{orderId}, #{userId})")
    public void insertMissionTestOrderUser(int orderId, int userId);

    // 从missionid去查对应单号的测试团队负责人
    @Select("select mu.id from managee_user mu ,managee_team_user mtu where mtu.team_id in (select team_id from managee_business_test_order mbto, managee_team_business mtb where mbto.mission_id=#{missionId} and mbto.business_id=mtb.business_id) and mtu.user_role=0")
    public List<Integer> selectTeamManagerIdInTestTeamByTableTestOrder(int missionId);

    @Update("alter table managee_business_project_mission set status=#{status} where mission_id=#{missionId}")
    public void updateMissionStatus(int missionId, int status);
}