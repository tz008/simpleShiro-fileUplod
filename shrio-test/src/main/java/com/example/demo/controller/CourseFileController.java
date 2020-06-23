package com.example.demo.controller;


import com.example.demo.entity.Course;
import com.example.demo.entity.CourseFile;
import com.example.demo.service.CourseFileService;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

//调用接口
@CrossOrigin
@RestController
@RequestMapping("/CourseFileController")
public class CourseFileController {

    @Autowired
    CourseFileService courseFileService;

    //    查询全课程信息
    @GetMapping("/getAllCourseFile")
    public List<CourseFile> selectAllCourseFile(){
        return courseFileService.selectAllCourseFile();
    }


//    根据课程号查找该课程的资料
    @PostMapping("/selectCourseFileByCourseId")
    public List<CourseFile> selectCourseFileByCourseId(){
        return courseFileService.selectCourseFileByCourseId(2345);
    }

}
