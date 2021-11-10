package com.magnus.project.managee.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    public int id;
    public String code;
    public String name;
    public int projectId;
    public float workLoad;
    public String planStartTime;
    public String planTestCommitTime;
    public String planTestFinishTime;
    public String planProductTime;
    public String actualStartTime;
    public String actualTestCommitTime;
    public String actualTestFinishTime;
    public String actualProductTime;
}
