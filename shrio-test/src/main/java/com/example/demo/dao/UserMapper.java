package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import javax.jws.soap.SOAPBinding;
import java.util.List;


@Mapper
public interface UserMapper {
//    查询全部用户
    List<User> selectAllUser();

//    根据用户名查找用户
    User selectUserByUser(String username);
}