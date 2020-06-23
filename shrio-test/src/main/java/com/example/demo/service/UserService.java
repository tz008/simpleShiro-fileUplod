package com.example.demo.service;



import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    //    查询全部用户
    List<User> selectAllUser();

    //    根据用户名查找用户
    User selectUserByUser(String username);
}
