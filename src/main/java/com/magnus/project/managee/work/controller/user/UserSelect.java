package com.magnus.project.managee.work.controller.user;

import com.magnus.project.managee.support.constants.Constants;
import com.magnus.project.managee.support.dicts.CommonDict;
import com.magnus.project.managee.support.dicts.ResultDict;
import com.magnus.project.managee.work.entity.User;
import com.magnus.project.managee.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserSelect {
    @Autowired
    UserService userService;

    /**
     * 全量查询客户，需要分页。分页规则是： 如果传递了page和pageSize，则按照这个去获取页码的数据
     * 如果至传递了page，则默认pageSize为10，如果都没有传，则默认page为0； page默认从1开始
     * mysql 获取表limit是从0开始的
     *
     * @return
     */
    @RequestMapping({"/query/users"})
    public Map<String, Object> selectUsers(@RequestBody Map queryMap) {
        int page = queryMap.containsKey(CommonDict.PAGE.getName()) ? (int) queryMap.get(CommonDict.PAGE.getName()) : Constants.DEFAULT_PAGE;
        int pageSize = queryMap.containsKey(CommonDict.PAGE_SIZE.getName()) ? (int) queryMap.get(CommonDict.PAGE_SIZE.getName()) : Constants.DEFAULT_PAGE_SIZE;
        int start = (page - 1) * pageSize;
        List<User> users = userService.selectUsers(start, pageSize);
        int total = userService.selectUsersCount();
        int totalPages = total % pageSize == 0 ? total / pageSize : ((int) total / pageSize) + 1;
        Map result = new HashMap<>();
        result.put(ResultDict.USER_LIST.getName(), users);
        result.put(ResultDict.TOTAL_PAGES.getName(), totalPages);
        return result;
    }

    @RequestMapping("/query/user/id/{id}")
    public User selectUserById(@PathVariable int id) {
        List<User> maps = userService.selectUserById(id);
        if (maps.isEmpty() || maps == null) {
            return null;
        }
        User user = maps.get(0);
        return user;
    }

    @RequestMapping("/query/user/name/{name}")
    public List<User> selectUserByName(@PathVariable String name) {
        List<User> maps = userService.selectUserByName(name);
        return maps;
    }
}
