package com.magnus.project.managee.work.service.impl;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.BusinessDict;
import com.magnus.project.managee.work.entity.Business;
import com.magnus.project.managee.work.mapper.BusinessMapper;
import com.magnus.project.managee.work.mapper.TeamBusinessMapper;
import com.magnus.project.managee.work.mapper.UserBusinessMapper;
import com.magnus.project.managee.work.service.BusinessService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    BusinessMapper businessMapper;

    @Autowired
    UserBusinessMapper userBusinessMapper;

    @Autowired
    TeamBusinessMapper teamBusinessMapper;

    @Autowired
    @Qualifier("batch")
    SqlSession sqlSession;

    @Override
    public List<Business> selectBusinesses(int start, int offset) {
        return businessMapper.selectBusinesses(start, offset);
    }

    @Override
    public int selectBusinessCount() {
        return businessMapper.selectBusinessesCount();
    }

    @Override
    public List<Business> selectBusinessesById(Integer id) {
        return businessMapper.selectBusinessById(id);
    }

    @Override
    public List<Business> selectBusinessesByName(String name) {
        return businessMapper.selectBusinessByName(name);
    }

    @Override
    public void insertBusiness(Map map) {
        // todo: 需要插入需求-业务关系表。
        // 新增需求的时候需要判断，是否有关联表需要插入？
        // 如果在需求插入的时候就指定了 开发人员/负责人，则需要插入关联表
        // 需要返回自增主键的场景，如果不预先设置数据格式，会默认把int类型转成BigInteger。
        map.put(BusinessDict.AUTO_INCREMENT_ID.getStr(), Constants.DEFAULT_INTEGER_ZERO);
        businessMapper.insertBusiness(map);
        Integer id = (Integer) map.get(BusinessDict.AUTO_INCREMENT_ID.getStr());
        List<Business> businesses = businessMapper.selectBusinessById(id);
        // todo: 新增需求的时候，直接返回需求内容即可。后续提供指派功能。
        // 插入表： 业务-需求 关系表
        List<Integer> counterList = (List) map.get(BusinessDict.BUSINESS_COUNTER.getStr());
        if (counterList != null && !counterList.isEmpty()) {
            for (Integer counter : counterList) {
                userBusinessMapper.insertBusinessUser(id, counter, Constants.BUSINESS_USER_ROLE_COUNTER);
            }
        }
    }

    @Override
    public void updateBusiness(Map map) {
        // 更新需求内容
        // 更新需求需要考虑一下几点：
        // 1.更新需求的时候，更新的内容是哪些？
        // 2.更新需求的负责人/开发/业务的时候，规则是什么？先删掉原有的关系表，然后插入新的，还是其他方法？（通过前端限制，点击删除开发/业务/负责人是一个原子性操作，不与需求内容的更新关联？）
    }

    @Override
    public void deleteBusinessByBusinessId(int id) {
        // 删除需求，需要同步删除 用户-需求关系表，需求-团队关系表
        businessMapper.deleteBusinessById(id);
        teamBusinessMapper.deleteTeamBusinessByBusinessId(id);
        userBusinessMapper.deleteBusinessUserByBusinessId(id);
    }

    @Override
    public void updateBusinessStatus(int businessId, int status) {
        // 更新需求状态
        businessMapper.updateBusinessStatus(businessId, status);
    }
}
