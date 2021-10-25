package com.magnus.project.managee.work.service;

import com.magnus.project.managee.work.entity.Business;

import java.util.List;
import java.util.Map;

public interface BusinessService {

    public List<Business> selectBusinesses(int start, int offset);

    public int selectBusinessCount();

    public List<Business> selectBusinessesById(Integer id);

    public List<Business> selectBusinessesByName(String name);

    public void insertBusiness(Map map);

    public void updateBusiness(Map map);

    public void deleteBusinessByBusinessId(int id);

    public void updateBusinessStatus(int businessId, int status);
}
