package com.magnus.project.managee.work.mapper;

import com.magnus.project.managee.work.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@CacheNamespace
public interface UserMapper {

    @Select("select * from managee_user order by id asc limit #{start},#{size}")
    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "name", property = "userName"),
            @Result(column = "level", property = "userLevel"),
            @Result(column = "role", property = "userRole"),
            @Result(column = "password", property = "password"),
            @Result(column = "description", property = "userDescription")
    })
    public List<User> selectUsers(int start, int size);

    @Select("select count(1) from managee_user")
    public int selectUsersCount();

    @Select("select * from managee_user where id=#{id}")
    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "name", property = "userName"),
            @Result(column = "level", property = "userLevel"),
            @Result(column = "role", property = "userRole"),
            @Result(column = "password", property = "password"),
            @Result(column = "description", property = "userDescription")
    })
    public List<User> selectUserById(int id);

    @Select("select * from managee_user where name like concat('%',#{name},'%') order by id asc")
    @Results({
            @Result(column = "id", property = "userId"),
            @Result(column = "name", property = "userName"),
            @Result(column = "level", property = "userLevel"),
            @Result(column = "role", property = "userRole"),
            @Result(column = "password", property = "password"),
            @Result(column = "description", property = "userDescription")
    })
    public List<User> selectUserByName(String name);

    @Insert("insert into managee_user(name,level,role,password,description) values(#{userName}, #{userLevel}, #{userRole}, #{password}, #{userDescription})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insertUser(Map user);
}
