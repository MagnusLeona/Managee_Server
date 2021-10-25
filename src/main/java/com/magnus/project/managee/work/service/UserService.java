package com.magnus.project.managee.work.service;

import com.magnus.project.managee.work.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> selectUsers(int start, int size);

    public int selectUsersCount();

    public List<User> selectUserById(int id);

    public List<User> selectUserByName(String name);

    public void insertUser(Map user);
}
