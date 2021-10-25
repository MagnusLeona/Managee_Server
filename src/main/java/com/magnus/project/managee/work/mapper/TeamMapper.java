package com.magnus.project.managee.work.mapper;

import com.magnus.project.managee.work.entity.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@CacheNamespace
public interface TeamMapper {

    @Select("select * from managee_team order by id asc limit #{start},#{offset}")
    @Results({
            @Result(column = "id", property = "teamId"),
            @Result(column = "name", property = "teamName"),
            @Result(column = "description", property = "teamDescription")
    })
    public List<Team> selectTeams(int start, int offset);

    @Select("select * from managee_team where id=#{id}")
    @Results({
            @Result(column = "id", property = "teamId"),
            @Result(column = "name", property = "teamName"),
            @Result(column = "description", property = "teamDescription")
    })
    public List<Team> selectTeamsById(int id);

    @Insert("insert into managee_team(name, description) values(#{teamName},#{teamDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "teamId")
    public void insertTeam(Map map);

    @Insert("insert into managee_team_user(user_id, team_id, user_role) value(#{userId},#{teamId},#{userRole})")
    public void insertTeamUser(Map map);
}
