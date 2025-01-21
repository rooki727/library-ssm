package com.ssmtest.service;
import com.ssmtest.entity.Admin;
import com.ssmtest.entity.User;

import java.util.List;

public interface IUserService {

    //查询所有
    public List<Admin> findAllAdmin();

    //保存用户
    public void addAdmin(Admin admin);
    //删
    void deleteAdmin(int id);
    //改
    void updateAdmin(Admin admin);
    void uploadAvatar(Admin admin);
    //查
    //根据用户名和密码查询
    Admin selectUserByNameAndPassword(Admin admin);

    Admin getLoginById(int id);

    void updatePassword(Admin admin);

    void updateAdminToken(Admin admin);
    void updateAdminRefreshToken(Admin admin);
    boolean isAdminToken(String reqToken, String sqlToken);
    Admin selectByAccount(int account);
    //    普通用户部分
    List<User> findAllUser();
    List<Integer> getAllUserId();
    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);
    void updateUserAvatar(User user);

    User selectByAccountUser(int account);

    User selectUserCommonByNameAndPassword(User user);
    //    查询用户的日期增长情况
    int  selectUserToday();
    int selectUserMonth();
    int selectUserBuy();
    void updateCommonToken(User user);
    void updateCommonRefreshToken(User user);
    User getLoginCommonById(int id);
    boolean isCommonToken(String reqToken, String sqlToken);

}

