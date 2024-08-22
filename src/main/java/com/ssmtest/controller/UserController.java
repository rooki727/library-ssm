package com.ssmtest.controller;

import com.ssmtest.entity.*;
import com.ssmtest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URL;
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
            userService.updateAdminToken(adminFromDb);
            return new ResponseEntity<>(adminFromDb, headers, HttpStatus.OK);
        } else {
            // 登录失败
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }

    // 生成token

    public static String generateToken(Admin admin) {
        String name= admin.getName();
        String timestamp = java.lang.String.valueOf(System.currentTimeMillis());
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
    public static String generateTokenUser(User user) {
        String name= user.getName();
        String timestamp = java.lang.String.valueOf(System.currentTimeMillis());
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
        System.out.println(token);
        System.out.println(oldAdmin.getToken());
        System.out.println(id);
        boolean isToken=  userService.isAdminToken(token,oldAdmin.getToken());
        System.out.println(isToken);

      if(isToken){
          System.out.println("token校验成功！getLoginById");
          //    处理业务
          Admin newAdmin=userService.getLoginById(id);
          String newToken = generateToken(newAdmin);
          // 将token放入响应头中
          HttpHeaders headers = new HttpHeaders();
          headers.add("Authorization", "Bearer " + newToken);
          newAdmin.setToken(newToken);
          userService.updateAdminToken(newAdmin);
          return new ResponseEntity<>(newAdmin, headers, HttpStatus.OK);
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
        response.setCode("1");
        response.setMsg("操作成功");
        response.setResult("重置token成功！");
        return response;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}


    private static final String UPLOAD_DIR = "E:\\library-ssm\\uploads";
    @PostMapping("/uploadAvatar")
    @ResponseBody
    public ApiResponse<String> uploadAvatar(@RequestParam("image") MultipartFile file) {
        ApiResponse<String> response = new ApiResponse<>();
        if (file.isEmpty()) {
            response.setResult("uploadFile null");
            return response;
        }
        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();

            // 确定存储路径
            String filePath = UPLOAD_DIR + File.separator + originalFilename;
            File dest = new File(filePath);

            // 检查目标文件夹是否存在，不存在则创建
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            // 存储文件
            file.transferTo(dest);

            // 构造完整的URL路径
            String avatarUrl = "http://localhost:8080/uploads/" + originalFilename;

            // 返回成功信息和完整的URL路径
            response.setCode("1");
            response.setMsg("uploadFile Success");
            response.setResult(avatarUrl);
            return response;
        } catch (Exception e) {
            response.setResult("uploadFile error");
            return response;
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
        
            return null;


    }
    @PostMapping("/loginUser")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
        // 根据用户名从数据库中查询用户信息
        User user1 = userService.selectUserCommonByNameAndPassword(user);
        String password = user.getPassword();
        if (user1 != null && user1.getPassword().equals(password)) {
            // 登录成功，生成token
            String token = generateTokenUser(user1);
            // 将token放入响应头中
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            user1.setToken(token);
            userService.updateCommonToken(user1);
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
//                String newToken = generateTokenUser(newUser);
//                // 将token放入响应头中
//                HttpHeaders headers = new HttpHeaders();
//                headers.add("Authorization", "Bearer " + newToken);
//                newUser.setToken(newToken);
//                userService.updateCommonToken(newUser);
//                return new ResponseEntity<>(newUser, headers, HttpStatus.OK);
                return new ResponseEntity<>(newUser, HttpStatus.OK);
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


}

