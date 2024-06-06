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

//    普通用户部分
List<User> findAllUser();

    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    //    查询用户的日期增长情况
    int  selectUserToday();
    int selectUserMonth();
    int selectUserBuy();
}

