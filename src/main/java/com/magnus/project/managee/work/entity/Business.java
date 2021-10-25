package com.magnus.project.managee.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Business implements Serializable {
    public Integer businessId;
    public String businessName;
    public Integer businessStatus;
    public String createTime;
    public String acceptTime;
    public String startTime;
    public String submitTime;
    public String deployTime;
    public List<User> developers;
    public List<User> counters;
    public List<User> managers;
}
