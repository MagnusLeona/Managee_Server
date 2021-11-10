package com.magnus.project.managee.work.mapper;

import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.entity.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TeamUserMapper {

    @Select("select id, name from managee_team_user mtu left join managee_user mu on mtu.user_id=mu.id where mtu.team_id=#{teamId} and mtu.user_role=#{userRole}")
    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "name", property = "userName")
    })
    public List<User> queryUserInTeamByTeamId(int teamId, int userRole);

    @Select("select team_id from managee_team_user where user_id=#{userId}")
    @Results({
            @Result(column = "team_id", property = "teamId")
    })
    public List<Team> selectTeamByUserId(int userId);

    @Select("select team_id from managee_team_user where user_id=#{userId}")
    public int selectTeamIdByUserId(int userId);
}
