package com.ssmtest.service.impl;

import com.ssmtest.dao.IUserDao;
import com.ssmtest.entity.Admin;
import com.ssmtest.entity.User;
import com.ssmtest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public List<Admin> findAllAdmin() {
        System.out.println("user业务层实现类--findAllAdmin");
        return userDao.findAllAdmin();
    }

    @Override
    public void addAdmin(Admin admin) {
        System.out.println("user业务层实现类--addAdmin");
        userDao.addAdmin(admin);
    }

    @Override
    public void deleteAdmin(int id) {
        System.out.println("user业务层实现类--deleteAdmin");
        userDao.deleteAdmin(id);
    }

    @Override
    public void updateAdmin(Admin admin) {
        System.out.println("user业务层实现类--updateAdmin");
        userDao.updateAdmin(admin);
    }

    @Override
    public void uploadAvatar(Admin admin) {
        System.out.println("user业务层实现类--uploadAvatar");
        userDao.uploadAvatar(admin);
    }

    @Override
    public Admin selectUserByNameAndPassword(Admin admin) {
        System.out.println("user业务层实现类--selectUserByNameAndPassword");
        Admin adminFromDb = userDao.selectUserByAccountAndPassword(admin);
        return adminFromDb;
    }

    @Override
    public Admin getLoginById(int id) {
        Admin adminFromDb = userDao.getLoginById(id);
        return adminFromDb;
    }

    @Override
    public void updatePassword(Admin admin) {
        System.out.println("user业务层实现类--updatePassword");
        userDao.updatePassword(admin);
    }

    @Override
    public void updateAdminToken(Admin admin) {
       userDao.updateAdminToken(admin);
    }

    @Override
    public void updateAdminRefreshToken(Admin admin) {
        userDao.updateAdminRefreshToken(admin);
    }

    @Override
    public boolean isAdminToken(String reqToken, String sqlToken) {
        if(reqToken.equals(sqlToken)){
            return true;
        }
        return false;
    }


    //普通用户部分
    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public void addUser(User user) {
         userDao.addUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void updateUserAvatar(User user) {
        userDao.updateUserAvatar(user);
    }

    @Override
    public User selectUserCommonByNameAndPassword(User user) {
        return userDao.selectUserCommonByNameAndPassword(user);
    }

    @Override
    public void updateCommonToken(User user) {
        userDao.updateCommonToken(user);
    }

    @Override
    public void updateCommonRefreshToken(User user) {
        userDao.updateCommonRefreshToken(user);
    }

    @Override
    public User getLoginCommonById(int id) {
        return userDao.getLoginCommonById(id);
    }

    @Override
    public boolean isCommonToken(String reqToken, String sqlToken) {
        if(reqToken.equals(sqlToken)){
            return true;
        }
        return false;
    }

    //    日月情况
    @Override
    public int selectUserToday() {
        return userDao.selectUserToday();
    }

    @Override
    public int selectUserMonth() {
        return userDao.selectUserMonth();
    }

    @Override
    public int selectUserBuy() {
        return userDao.selectUserBuy();
    }


    @Override
    public Admin selectByAccount(int account) {
        return userDao.selectByAccount(account);
    }

    @Override
    public User selectByAccountUser(int account) {
        return userDao.selectByAccountUser(account);
    }

}
