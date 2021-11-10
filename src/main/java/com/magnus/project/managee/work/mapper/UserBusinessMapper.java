package com.magnus.project.managee.work.mapper;

import com.magnus.project.managee.work.entity.Business;
import com.magnus.project.managee.work.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserBusinessMapper {

    @Select("select mb.* from managee_business mb, managee_business_user mbs where mbs.user_id=#{userId} and mb.id=mbs.business_id")
    @Results({
            @Result(column = "id", property = "businessId", javaType = Integer.class),
            @Result(column = "name", property = "businessName"),
            @Result(column = "status", property = "businessStatus"),
            @Result(column = "time_create", property = "createTime"),
            @Result(column = "time_accept", property = "acceptTime"),
            @Result(column = "time_start", property = "startTime"),
            @Result(column = "time_submit", property = "submitTime"),
            @Result(column = "time_deploy", property = "deployTime"),
            @Result(column = "id", property = "counters", javaType = List.class, many = @Many(select = "selectBusinessCreator"))
    })
    public List<Business> selectUserBusiness(Integer userId);

    @Select("select id,name from managee_user mu, managee_business_user mbu where mu.id=mbu.user_id and mbu.business_id=#{id}")
    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "name", property = "userName")
    })
    public List<User> selectBusinessCreator(int id);

    @Insert("insert into managee_business_user(business_id,user_id, user_role) values(#{businessId},#{userId},#{userRole})")
    public void insertBusinessUser(int businessId, int userId, int userRole);

    @Delete("delete from managee_business_user where business_id=#{businessId} and user_id in (select user_id from managee_team_user where team_id=#{teamId})")
    public void deleteBusinessUser(int businessId, int teamId);

    @Delete("delete from managee_business_user where business_id=#{businessId}")
    public void deleteBusinessUserByBusinessId(int businessId);

    @Update("alter table managee_business_user set role=#{role} where user_id=#{userId} and business_id=#{businessId}")
    public void updateBusinessUserRole(int userId, int businessId, int role);

    @Update("update managee_business_user set user_role=#{businessRoleTracer} where user_id in (" +
            "select user_id managee_team_user where user_role=#{teamRoleManager} and team_id=#{teamId}" +
            ")")
    public void updateTeamManagerRoleToTracerInBusiness(int userId, int teamId, int businessRoleTracer, int teamRoleManager);

    @Insert("insert into managee_business_pre_evaluate_user values(#{businessId}, #{userId}, #{status})")
    public void insertBusinessPreEvaluateUser(int businessId, int userId, int status);

    @Insert("insert into managee_business_pre_evaluate_problem(business_id, user_id, content) value(#{businessId}, #{userId}, #{content})")
    public void insertNewBusinessPreEvaluateProblem(int businessId, int userId, String content);

    @Update("alter table managee_business_pre_evaluate_user set status=#{status} where business_id=#{businessId} and user_id=#{userId}")
    public void updateBusinessPreEvaluateStatus(int businessId, int userId, int status);

    @Insert("insert into managee_business_evaluate_user(business_id, user_id, evaluate_status) value(#{businessId}, #{userId}, #{status})")
    public void insertBusinessEvaluateUser(int businessId, int userId, int status);

    @Select("select user_id from managee_business_pre_evaluate_user where business_id=#{businessId}")
    public List<Integer> selectBusinessPreEvaluateUsers(int businessId);

    @Update("alter table managee_business_project set status=#{status} where business_id=#{businessId} and user_id=#{userId}")
    public void updateBusinessProjectStatus(int businessId, int userId, int status);

    @Insert("insert into managee_business_project_dev_evaluate(user_id, project_id, status) value(#{userId},#{projectId},#{status})")
    public void insertBusinessProjectDevEvaluate(int projectId, int userId, int status);

    @Update("alter table managee_business_project_dev_evaluate set status=#{status} where project_id=#{projectId} and user_id=#{userId}")
    public void updateBusinessProjectDevEvaluate(int projectId, int userId, int status);

    @Delete("delete from managee_business_project_dev_evaluate where project_id=#{projectId}")
    public void deleteBusinessProjectDevEvaluate(int projectId);
}
