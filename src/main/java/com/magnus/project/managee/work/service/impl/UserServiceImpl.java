package com.magnus.project.managee.work.service.impl;

import com.magnus.project.managee.work.entity.User;
import com.magnus.project.managee.work.mapper.UserMapper;
import com.magnus.project.managee.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> selectUsers(int start, int size) {
        return userMapper.selectUsers(start, size);
    }

    @Override
    public int selectUsersCount() {
        return userMapper.selectUsersCount();
    }

    @Override
    public User selectUserById(int id) {
        List<User> users = userMapper.selectUserById(id);
        if(users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> selectUserByName(String name) {
        return userMapper.selectUserByName(name);
    }


    @Override
    public void insertUser(Map user) {
        userMapper.insertUser(user);
    }
}
