package com.example.demo.serviceImp;



import com.example.demo.dao.UserMapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//实现类
@Service
public class UserServiceImp implements UserService {

    @Autowired(required = false)
    UserMapper userMapper;


    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public User selectUserByUser(String username) {
        return userMapper.selectUserByUser(username);
    }
}
