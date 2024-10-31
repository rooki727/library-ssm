package com.ssmtest.controller;

import com.ssmtest.entity.*;
import com.ssmtest.jwt.JwtConfig;
import com.ssmtest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    JwtConfig jwtConfig=new JwtConfig();

    //登录

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Admin admin, HttpSession session) {
        // 根据用户名从数据库中查询用户信息
        Admin adminFromDb = userService.selectUserByNameAndPassword(admin);
        String password = admin.getPassword();
        if (adminFromDb != null && adminFromDb.getPassword().equals(password)) {
            // 登录成功，生成token和refreshToken
            String token =jwtConfig.createToken(adminFromDb.getId().toString());
            String refreshToken=jwtConfig.createRefreshToken(adminFromDb.getId().toString());
            // 将token放入响应头中
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            adminFromDb.setToken(token);
            adminFromDb.setRefreshToken(refreshToken);
            userService.updateAdminRefreshToken(adminFromDb);
            userService.updateAdminToken(adminFromDb);
            return new ResponseEntity<>(adminFromDb, headers, HttpStatus.OK);
        } else {
            // 登录失败
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }

@PostMapping("/getLoginById")
@ResponseBody
public ResponseEntity<?> getLoginById(@RequestBody Map<String, Object> requestBody, HttpServletRequest request){
//        获取id
    int id = (int) requestBody.get("id");
//处理token
    // 获取请求头中的Token
    String tokenAuthorization = request.getHeader("Authorization");
    // 判断是否是Bearer Token格式
    if (tokenAuthorization.startsWith("Bearer ")) {
        // 获取实际的Token值
        String token = tokenAuthorization.substring(7);
        Admin oldAdmin=userService.getLoginById(id);// 7是 "Bearer " 的长度
        boolean isToken=  userService.isAdminToken(token,oldAdmin.getToken());
      if(isToken){
          System.out.println("token校验成功！getLoginById");
          //    处理业务
          Admin newAdmin=userService.getLoginById(id);
          return new ResponseEntity<>(newAdmin, HttpStatus.OK);
      }
    }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");

}

    @GetMapping("/findAllAdmin")
    @ResponseBody
    public ApiResponse<List<Admin>> findAllAdmin(HttpServletRequest request){
        ApiResponse<List<Admin>> response = new ApiResponse<>();
        System.out.println("表现层--查询所有用户");
        try {
            //调用service的方法
            List<Admin> adminList = userService.findAllAdmin();
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(adminList);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PostMapping("/updateAdmin")
    @ResponseBody
    public ApiResponse<Boolean> updateAdmin(@RequestBody Admin admin ,HttpServletRequest request ) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        // 获取请求头中的Token
        String tokenAuthorization = request.getHeader("Authorization");
        // 判断是否是Bearer Token格式
        if (tokenAuthorization.startsWith("Bearer ")) {
            // 获取实际的Token值
            String token = tokenAuthorization.substring(7);
            Admin oldAdmin = userService.getLoginById(admin.getId());// 7是 "Bearer " 的长度
            boolean isToken = userService.isAdminToken(token, oldAdmin.getToken());
            if (isToken) {
                boolean isValid = jwtConfig.validateToken(token, admin.getId().toString());
                if(!isValid){
//                    token失效标准code-403
                    response.setCode("-403");
                    response.setMsg("token失效");
                    response.setResult(false);
                    return response;
                }
                // 继续处理有效token的逻辑
                else {
                    System.out.println("token校验成功！updateAdmin");
                    //    处理业务
                    try {
                        userService.updateAdmin(admin);
                        response.setCode("1");
                        response.setMsg("操作成功");
                        response.setResult(true);
                        return response;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
//            处理token变化情况
            else{
                //                    token失效标准code-403
                response.setCode("-403");
                response.setMsg("token失效");
                response.setResult(false);
                return response;
            }
        }
        return response;
    }
    //    封装一个判断权限的函数
    public Boolean checkVerify(int login_id,String adminVerify){
        Admin adminLoginer=userService.getLoginById(login_id);
        if("first".equals(adminLoginer.getVerify()) && "second".equals(adminVerify)){
//
            System.out.println("权限校验通过！");
            return true;
        }
        else {
            return false;
        }
    }
    //增
    @PostMapping("/addAdmin")
    @ResponseBody
    public ApiResponse<Boolean> addAdmin(@RequestBody AdminRequest adminRequest) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        Admin adminLoginer=userService.getLoginById(adminRequest.getLogin_id());

        if("first".equals(adminLoginer.getVerify())){
            try {
                userService.addAdmin(adminRequest.getAdmin());
                response.setCode("1");
                response.setMsg("操作成功");
                response.setResult(true);
                return response;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            response.setCode("-2");
            response.setMsg("权限不够");
            response.setResult(false);
            return response;
        }


    }

    //删
    @PostMapping("/deleteAdmin")
    @ResponseBody
    public ApiResponse<Boolean> deleteAdmin(@RequestBody AdminRequest adminRequest) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        if(checkVerify(adminRequest.getLogin_id(),adminRequest.getAdmin().getVerify())){
            try {
                userService.deleteAdmin(adminRequest.getAdmin().getId());
                response.setCode("1");
                response.setMsg("操作成功");
                response.setResult(true);
                return response;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        else {
            response.setCode("-2");
            response.setMsg("权限不够");
            response.setResult(false);
            return response;
        }

    }

//修改admin列表的admin信息
    @PostMapping("/updateAdminById")
    @ResponseBody
    public ApiResponse<Boolean> updateAdminById(@RequestBody AdminRequest adminRequest) {
        ApiResponse<Boolean> response = new ApiResponse<>();

        if(checkVerify(adminRequest.getLogin_id(),adminRequest.getAdmin().getVerify())){
            try {
                userService.updateAdmin(adminRequest.getAdmin());
                response.setCode("1");
                response.setMsg("操作成功");
                response.setResult(true);
                return response;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

       else {
            response.setCode("-2");
            response.setMsg("权限不够");
            response.setResult(false);
            return response;
        }
    }
//    重置token
@PostMapping("/resetAdminToken")
@ResponseBody
public ApiResponse<String>  resetAdminToken(@RequestBody Admin admin){
    ApiResponse<String> response = new ApiResponse<>();
    try {
        userService.updateAdminToken(admin);
        userService.updateAdminRefreshToken(admin);
        response.setCode("1");
        response.setMsg("操作成功");
        response.setResult("重置token成功！");
        return response;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


    @PostMapping("/updateAvatar")
    @ResponseBody
    public ApiResponse<Boolean>  updateAvatar(@RequestBody Admin admin,HttpServletRequest request) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        // 获取请求头中的Token
        String tokenAuthorization = request.getHeader("Authorization");
        // 判断是否是Bearer Token格式
        if (tokenAuthorization.startsWith("Bearer ")) {
            // 获取实际的Token值
            String token = tokenAuthorization.substring(7);
            Admin oldAdmin = userService.getLoginById(admin.getId());// 7是 "Bearer " 的长度
            boolean isToken = userService.isAdminToken(token, oldAdmin.getToken());
            if (isToken) {
                boolean isValid = jwtConfig.validateToken(token, admin.getId().toString());
                if(!isValid){
                    response.setCode("-403");
                    response.setMsg("token失效");
                    response.setResult(false);
                    return response;
                }
                else{
                    System.out.println("token校验成功！updatePassword");
                    //    处理业务
                    try {
                        userService.uploadAvatar(admin);
                        response.setCode("1");
                        response.setMsg("操作成功");
                        response.setResult(true);
                        return response;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            //            处理token变化情况
            else{
                //                    token失效标准code-403
                response.setCode("-403");
                response.setMsg("token失效");
                response.setResult(false);
                return response;
            }
        }

        return response;

    }
    @PostMapping("/updatePassword")
    @ResponseBody
    public ApiResponse<Boolean>  updatePassword(@RequestBody Admin admin,HttpServletRequest request) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        // 获取请求头中的Token
        String tokenAuthorization = request.getHeader("Authorization");
        // 判断是否是Bearer Token格式
        if (tokenAuthorization.startsWith("Bearer ")) {
            // 获取实际的Token值
            String token = tokenAuthorization.substring(7);
            Admin oldAdmin = userService.getLoginById(admin.getId());// 7是 "Bearer " 的长度
            boolean isToken = userService.isAdminToken(token, oldAdmin.getToken());
            if (isToken) {
                boolean isValid = jwtConfig.validateToken(token, admin.getId().toString());
                if(!isValid){
                    response.setCode("-403");
                    response.setMsg("token失效");
                    response.setResult(false);
                    return response;
                }
                else{
                    System.out.println("token校验成功！updatePassword");
                    //    处理业务
                    try {
                        userService.updatePassword(admin);
                        response.setCode("1");
                        response.setMsg("操作成功");
                        response.setResult(true);
                        return response;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            //            处理token变化情况
            else{
                //                    token失效标准code-403
                response.setCode("-403");
                response.setMsg("token失效");
                response.setResult(false);
                return response;
            }
        }

        return response;

    }




//   普通用户的api
    @GetMapping("/findAllUser")
    @ResponseBody
    public ApiResponse<List<User>> findAllUser(){
        ApiResponse<List<User>> response = new ApiResponse<>();
        System.out.println("表现层--查询所有用户");
        try {
            //调用service的方法
            List<User> userList = userService.findAllUser();
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(userList);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //增
    @PostMapping("/addUser")
    @ResponseBody
    public ApiResponse<Boolean> adduserList(@RequestBody User user,HttpServletRequest request) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            userService.addUser(user);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //删
    @PostMapping("/deleteUser")
    @ResponseBody
    public  ApiResponse<Boolean> deleteUser(@RequestBody Map<String, Object> requestBody,HttpServletRequest request) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        // 从请求体中获取 userId
        int id = (int) requestBody.get("user_id");
        try {
            userService.deleteUser(id);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/updateUser")
    @ResponseBody
    public  ApiResponse<Boolean> updateUser(@RequestBody User user,HttpServletRequest request) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        // 获取请求头中的Token
        String tokenAuthorization = request.getHeader("Authorization");
        if (tokenAuthorization.startsWith("Bearer ")) {
            // 获取实际的Token值
            String token = tokenAuthorization.substring(7);
            User oldUser=userService.getLoginCommonById(user.getUser_id());// 7是 "Bearer " 的长度
            boolean isToken=  userService.isCommonToken(token,oldUser.getToken());
            if (isToken) {
                boolean isValid = jwtConfig.validateToken(token, user.getUser_id().toString());
                if(!isValid){
                    response.setCode("-403");
                    response.setMsg("token失效");
                    response.setResult(false);
                    return response;
                }

                else{
                    System.out.println("token校验成功！updateUser");

                    //    处理业务
                    try {
                        userService.updateUser(user);
                        response.setCode("1");
                        response.setMsg("操作成功");
                        response.setResult(true);
                        return response;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
//                    token失效标准code-403
            else{
                response.setCode("-403");
                response.setMsg("token失效");
                response.setResult(false);
                return response;
            }
        }
            return response;

    }
    @PostMapping("/loginUser")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        // 根据用户名从数据库中查询用户信息
        User user1 = userService.selectUserCommonByNameAndPassword(user);
        String password = user.getPassword();
        if (user1 != null && user1.getPassword().equals(password)) {
            // 登录成功，生成token
            String token = jwtConfig.createToken(user1.getUser_id().toString());
            String refreshToken= jwtConfig.createToken(user1.getUser_id().toString());
            // 将token放入响应头中
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            user1.setToken(token);
            user1.setRefreshToken(refreshToken);
            userService.updateCommonToken(user1);
            userService.updateCommonRefreshToken(user1);
            User newUser=userService.selectUserCommonByNameAndPassword(user);
            return new ResponseEntity<>(newUser, headers, HttpStatus.OK);
        } else {
            // 登录失败
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }
    @PostMapping("/resetCommonToken")
    @ResponseBody
    public ApiResponse<String>  resetCommonToken(@RequestBody User user){
        ApiResponse<String> response = new ApiResponse<>();
        try {
            userService.updateCommonToken(user);
            userService.updateCommonRefreshToken(user);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult("重置token成功！");
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/getLoginCommonById")
    @ResponseBody
    public ResponseEntity<?> getLoginCommonById(@RequestBody Map<String, Object> requestBody, HttpServletRequest request){
        //        获取id
        int id = (int) requestBody.get("user_id");
         //处理token
        // 获取请求头中的Token
        String tokenAuthorization = request.getHeader("Authorization");
        // 判断是否是Bearer Token格式
        if (tokenAuthorization.startsWith("Bearer ")) {

            // 获取实际的Token值
            String token = tokenAuthorization.substring(7);
            User oldUser=userService.getLoginCommonById(id);// 7是 "Bearer " 的长度
            boolean isToken=  userService.isCommonToken(token,oldUser.getToken());
            if(isToken){
                System.out.println("token校验成功！getLoginCommonById");
                //    处理业务
                User newUser=userService.getLoginCommonById(id);
                return new ResponseEntity<>(newUser, HttpStatus.OK);
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");

    }

//日期情况

    @GetMapping("/getUserState")
    @ResponseBody
    public UserState getUserState(){
        try {
            int todayAdd=userService.selectUserToday();
            int monthAdd=userService.selectUserMonth();
            int userBuy=userService.selectUserBuy();
            return new UserState(todayAdd,monthAdd,userBuy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
    public ApiResponse<String> selectByAccount(@RequestBody Map<String, Object> requestBody){
        ApiResponse<String> response = new ApiResponse<>();
        int account = (int) requestBody.get("account");
       Admin admin= userService.selectByAccount(account);
       if(admin!=null){
           response.setCode("1");
           response.setMsg("操作成功");
           response.setResult("account存在");
           return response;

       }
       else {
           response.setCode("1");
           response.setMsg("操作成功");
           response.setResult("account不存在");
           return response;

       }
    }

    @PostMapping("/selectByAccountUser")
    @ResponseBody
    public ApiResponse<String> selectByAccountUser(@RequestBody Map<String, Object> requestBody){
        ApiResponse<String> response = new ApiResponse<>();
        int account = (int) requestBody.get("account");
        User user= userService.selectByAccountUser(account);
        if(user!=null){
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult("account存在");
            return response;
        }
        else {
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult("account不存在");
            return response;
        }
    }


//token无感刷新 admin
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody Admin admin) {
        Admin admin1=userService.getLoginById(admin.getId());
        if (admin1.getRefreshToken().equals(admin.getRefreshToken())) {
            String newAccessToken = jwtConfig.createToken(admin.getId().toString());
            admin1.setToken(newAccessToken);
            userService.updateAdminToken(admin1);
            return ResponseEntity.ok(admin1);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh Token 已失效");
        }
    }

    //token无感刷新 user
    @PostMapping("/refreshTokenUser")
    public ResponseEntity<?> refreshTokenUser(@RequestBody User user) {
        User user1=userService.getLoginCommonById(user.getUser_id());
        if (user1.getRefreshToken().equals(user.getRefreshToken())) {
            String newAccessToken = jwtConfig.createToken(user.getUser_id().toString());
            user1.setToken(newAccessToken);
            userService.updateCommonToken(user1);
            return ResponseEntity.ok(user1);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh Token 已失效");
        }
    }
}

