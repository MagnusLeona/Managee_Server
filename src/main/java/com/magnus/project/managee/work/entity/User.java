package com.magnus.project.managee.work.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @JsonProperty("id")
    public int userId;
    @JsonProperty("name")
    public String userName;
    @JsonProperty("level")
    public int userLevel;
    @JsonProperty("role")
    public int userRole;
    @JsonIgnore
    public String password;
    @JsonProperty("description")
    public String userDescription;
}
