package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.CourseFileService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.parser.MediaType;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

//调用接口
@CrossOrigin
@Controller
@RequestMapping("/testController")
public class testController {

    @Autowired
    UserService userService;

    @Autowired
    CourseFileService courseFileService;

//    文件上传
private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/upload")
    public String upLoad(HttpServletRequest request) throws IOException {

        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("files");
        MultipartFile file=files.get(0);
        System.out.println(file.getOriginalFilename());
        String name=params.getParameter("name");
        System.out.println("name:"+name);
        String id=params.getParameter("id");
        System.out.println("id:"+id);

        //获取上传文件的路径
        String realPath = "D://实验室//spingboot//springboot-shiro-vue权限管理//shrio-test//src//main//java//com//example//demo//file";
//        System.out.println("--------"+realPath);
        //获取上传的文件名，
        File file1 = new File(realPath, file.getOriginalFilename());
        System.out.println(file.getOriginalFilename());
        courseFileService.insertCourseFile(Integer.parseInt(id),file.getOriginalFilename());
        //执行文件上传复制
        file.transferTo(file1);
//        //跳到下载页面
        return "user/file";

    }


//文件下载
    @RequestMapping("/download")
    public void Download(String fileName,HttpServletResponse response) throws UnsupportedEncodingException {
//        获取文件路径
        String uploadPath = "D://实验室//spingboot//springboot-shiro-vue权限管理//shrio-test//src//main//java//com//example//demo//file";
        File file = new File(uploadPath,fileName);
        if(file.exists()){
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName,"utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try(FileInputStream fis= new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






    @GetMapping("/getAll")
    @ResponseBody
    public List<User> getAll(){
        return userService.selectAllUser();
    }

    @GetMapping("/add")
    public String add(){
        return "/user/add";
    }

    @GetMapping("/file")
    public String file(){
        return "/user/file";
    }

    @GetMapping("/update")
    public String update(){
        return "/user/update";
    }

    @GetMapping("/login")
    public String login(){
        return "/user/login";
    }

    @GetMapping("/no")
    public String no(){
        return "/user/no";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/goLogin")
    public String goLogin(String username, String password,String types, Model model){

        Subject user = SecurityUtils.getSubject();
        String md5PassWord = DigestUtils.md5DigestAsHex(password.getBytes());
        UsernamePasswordToken token = new UsernamePasswordToken(username, md5PassWord,types);
        try {
            //shiro帮我们匹配密码什么的，我们只需要把东西传给它，它会根据我们在UserRealm里认证方法设置的来验证
            user.login(token);
            return "test";
        } catch (UnknownAccountException e) {
            //账号不存在和下面密码错误一般都合并为一个账号或密码错误，这样可以增加暴力破解难度
            model.addAttribute("message", "账号错误！");
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("message", "密码错误！");
        } catch (Throwable e) {
            model.addAttribute("message", "未知错误！");
        }
        return "/user/login";
//        System.out.println(username);
////        使用shiro编写认证操作
//       Subject subject=SecurityUtils.getSubject();
//
//        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
//
//        try {
//            subject.login(token);
//            return "test";
////            登录成功
//        }
//        catch (UnknownAccountException e){
//            model.addAttribute("msg","用户名不存在");
//            return "login";
//        }
//        catch (IncorrectCredentialsException e){
//            model.addAttribute("msg","密码错误");
//            return "login";
//        }
    }

}
