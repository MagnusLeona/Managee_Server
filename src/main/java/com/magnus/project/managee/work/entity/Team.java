package com.magnus.project.managee.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team implements Serializable {
    public int teamId;
    public String teamName;
    public String teamDescription;
    public List<User> teamLeaders;
    public List<User> teamMembers;
}
