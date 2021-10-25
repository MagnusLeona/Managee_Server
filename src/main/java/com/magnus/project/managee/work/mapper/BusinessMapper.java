package com.magnus.project.managee.work.mapper;

import com.magnus.project.managee.work.entity.Business;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@CacheNamespace
public interface BusinessMapper {

    @Select("select * from managee_business order by id asc limit #{start},#{offset}")
    @Results({
            @Result(column = "id", property = "businessId"),
            @Result(column = "name", property = "businessName"),
            @Result(column = "status", property = "businessStatus"),
            @Result(column = "time_create", property = "createTime"),
            @Result(column = "time_accept", property = "acceptTime"),
            @Result(column = "time_start", property = "startTime"),
            @Result(column = "time_submit", property = "submitTime"),
            @Result(column = "time_deploy", property = "deployTime")
    })
    public List<Business> selectBusinesses(int start, int offset);

    @Select("select count(1) from managee_business")
    public int selectBusinessesCount();

    @Select("select * from managee_business where id=#{id}")
    @Results({
            @Result(column = "id", property = "businessId"),
            @Result(column = "name", property = "businessName"),
            @Result(column = "status", property = "businessStatus"),
            @Result(column = "time_create", property = "createTime"),
            @Result(column = "time_accept", property = "acceptTime"),
            @Result(column = "time_start", property = "startTime"),
            @Result(column = "time_submit", property = "submitTime"),
            @Result(column = "time_deploy", property = "deployTime")
    })
    public List<Business> selectBusinessById(Integer id);

    @Select("select * from managee_business where name like concat('%', #{name}, '%') order by id asc")
    @Results({
            @Result(column = "id", property = "businessId"),
            @Result(column = "name", property = "businessName"),
            @Result(column = "status", property = "businessStatus"),
            @Result(column = "time_create", property = "createTime"),
            @Result(column = "time_accept", property = "acceptTime"),
            @Result(column = "time_start", property = "startTime"),
            @Result(column = "time_submit", property = "submitTime"),
            @Result(column = "time_deploy", property = "deployTime")
    })
    public List<Business> selectBusinessByName(String name);

    @Insert("insert into managee_business(name,code,status,time_create,time_accept,time_start,time_submit,time_deploy) values(#{businessName},#{code},#{businessStatus},#{createTime},#{acceptTime},#{startTime},#{submitTime},#{deployTime})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public void insertBusiness(Map map);

    @Delete("delete from managee_business where id=#{id}")
    public void deleteBusinessById(int id);

    @Update("alter table managee_business set status=#{status} where id=#{businessId}")
    public void updateBusinessStatus(int businessId, int status);
}
