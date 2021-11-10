package com.magnus.project.managee.work.mapper;

import com.magnus.project.managee.work.entity.Team;
import com.magnus.project.managee.work.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

@CacheNamespace
public interface TeamMapper {

    @Select("select * from managee_team order by id asc limit #{start},#{offset}")
    @Results({
            @Result(column = "id", property = "teamId"),
            @Result(column = "name", property = "teamName"),
            @Result(column = "description", property = "teamDescription"),
            @Result(column = "id", property = "teamLeaders", many = @Many(select = "com.magnus.project.managee.work.mapper.TeamMapper.selectTeamLeadersByTeamId"))
    })
    public List<Team> selectTeams(int start, int offset);

    @Select("select * from managee_team where id=#{id}")
    @Results({
            @Result(column = "id", property = "teamId"),
            @Result(column = "name", property = "teamName"),
            @Result(column = "description", property = "teamDescription"),
            @Result(column = "id", property = "teamLeaders", many = @Many(select = "com.magnus.project.managee.work.mapper.TeamMapper.selectTeamLeadersByTeamId"))
    })
    public List<Team> selectTeamsById(int id);

    @Select("select mu.* from managee_user mu, managee_team_user mtu where mtu.team_id=#{id} and mtu.user_role=0 and mtu.user_id=mu.id")
    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "name", property = "userName"),
            @Result(column = "level", property = "userLevel"),
            @Result(column = "role", property = "userRole"),
            @Result(column = "password", property = "password"),
            @Result(column = "description", property = "userDescription")
    })
    public List<User> selectTeamLeadersByTeamId(int id);

    @Insert("insert into managee_team(name, description) values(#{teamName},#{teamDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "teamId")
    public void insertTeam(Map map);

    @Insert("insert into managee_team_user(user_id, team_id, user_role) value(#{userId},#{teamId},#{userRole})")
    public void insertTeamUser(Map map);
}
