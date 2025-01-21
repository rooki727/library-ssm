package com.ssmtest.dao;

import com.ssmtest.entity.Admin;
import com.ssmtest.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
//管理员部分
    List<Admin> findAllAdmin();

    void addAdmin(Admin admin);

    void deleteAdmin(int id);

    void updateAdmin(Admin admin);
    void uploadAvatar(Admin admin);
    Admin getLoginById(int id);
    Admin selectUserByAccountAndPassword(Admin admin);

    void updatePassword(Admin admin);
    void updateAdminToken(Admin admin);
    void updateAdminRefreshToken(Admin admin);
//    普通用户部分
    List<User> findAllUser();
    List<Integer> getAllUserId();
    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);
    void updateUserAvatar(User user);
    User selectUserCommonByNameAndPassword(User user);
    void updateCommonToken(User user);
    void updateCommonRefreshToken(User user);
    User getLoginCommonById(int id);


//    查询用户的日期增长情况
    int  selectUserToday();
    int selectUserMonth();
    int selectUserBuy();

    Admin selectByAccount(int account);

    User selectByAccountUser(int account);

}
