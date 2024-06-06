package com.ssmtest.controller;

import com.ssmtest.entity.Admin;
import com.ssmtest.entity.CaptchaChar;
import com.ssmtest.entity.User;
import com.ssmtest.entity.UserState;
import com.ssmtest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    //登录

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Admin admin, HttpSession session) {
        // 根据用户名从数据库中查询用户信息
        Admin adminFromDb = userService.selectUserByNameAndPassword(admin);
        String password = admin.getPassword();
        if (adminFromDb != null && adminFromDb.getPassword().equals(password)) {
            // 登录成功，生成token
            String token = generateToken(adminFromDb);
            // 将token放入响应头中
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            adminFromDb.setToken(token);
            return new ResponseEntity<>(adminFromDb, headers, HttpStatus.OK);
        } else {
            // 登录失败
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }

    // 生成token

    public static String generateToken(Admin admin) {
        String name= admin.getName();
        String timestamp = String.valueOf(System.currentTimeMillis());
        String data = name + timestamp;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


@PostMapping("/getLoginById")
@ResponseBody
public Admin getLoginById(@RequestBody Map<String, Object> requestBody){
        System.out.println("requestBody");
    int id = (int) requestBody.get("id");
  Admin admin=userService.getLoginById(id);
  return  admin;
}

    // http://127.0.0.1:8080/library_ssm/user/findAllAdmin
    @GetMapping("/findAllAdmin")
    @ResponseBody
    public List<Admin> findAllAdmin(){
        System.out.println("表现层--查询所有用户");
        //调用service的方法
        List<Admin> adminList = userService.findAllAdmin();
        return adminList;
    }

    //增
    @PostMapping("/addAdmin")
    @ResponseBody
    public String addAdmin(@RequestBody Admin admin) {

        userService.addAdmin(admin);
        return "成功添加";
    }

    //删
    @PostMapping("/deleteAdmin")
    @ResponseBody
    public String deleteAdmin(@RequestBody Map<String, Object> requestBody) {
        // 从请求体中获取 userId
        int id = (int) requestBody.get("id");

        userService.deleteAdmin(id);
        return "成功删除";
    }

    @PostMapping("/updateAdmin")
    @ResponseBody
    public String updateAdmin(@RequestBody Admin admin) {

        userService.updateAdmin(admin);
        return "成功更新";
    }
    @PostMapping("/uploadAvatar")
    @ResponseBody
    public String uploadAvatar(@RequestBody Admin admin) {
        userService.uploadAvatar(admin);
        return "成功更新";
    }
    @PostMapping("/updatePassword")
    @ResponseBody
    public String updatePassword(@RequestBody Admin admin) {
        userService.updatePassword(admin);
        return "成功更新";
    }

//   普通用户的api
    @GetMapping("/findAllUser")
    @ResponseBody
    public List<User> findAllUser(){
        System.out.println("表现层--查询所有用户");
        //调用service的方法
        List<User> userList = userService.findAllUser();
        return userList;
    }

    //增
    @PostMapping("/addUser")
    @ResponseBody
    public String adduserList(@RequestBody User user) {
        userService.addUser(user);
        return "成功添加";
    }

    //删
    @PostMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestBody Map<String, Object> requestBody) {
        // 从请求体中获取 userId
        int id = (int) requestBody.get("user_id");
        userService.deleteUser(id);
        return "成功删除";
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public String updateUser(@RequestBody User user) {

        userService.updateUser(user);
        return "成功更新";
    }


    @GetMapping("/getUserState")
    @ResponseBody
    public UserState getUserState(){

        int todayAdd=userService.selectUserToday();
        int monthAdd=userService.selectUserMonth();
        int userBuy=userService.selectUserBuy();
        return new UserState(todayAdd,monthAdd,userBuy);
    }
@GetMapping("/generateCaptcha")
@ResponseBody
    public  List<CaptchaChar> generateCaptcha() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        List<CaptchaChar> captcha = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            char value = chars.charAt(random.nextInt(chars.length()));
            int position = (int) (Math.random() * 10 + 5);
            int rotation = (int) (Math.random() * 40 - 20);

            captcha.add(new CaptchaChar(value, position, rotation));
        }

        return captcha;
    }

    @PostMapping("/selectByAccount")
    @ResponseBody
    public String selectByAccount(@RequestBody Map<String, Object> requestBody){
        int account = (int) requestBody.get("account");
       Admin admin= userService.selectByAccount(account);
       if(admin!=null){
           return "account存在";
       }
       else {
           return "account不存在";
       }
    }

    @PostMapping("/selectByAccountUser")
    @ResponseBody
    public String selectByAccountUser(@RequestBody Map<String, Object> requestBody){
        int account = (int) requestBody.get("account");
        User user= userService.selectByAccountUser(account);
        if(user!=null){
            return "account存在";
        }
        else {
            return "account不存在";
        }
    }
}

