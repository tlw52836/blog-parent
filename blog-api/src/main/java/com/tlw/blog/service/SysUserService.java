package com.tlw.blog.service;

import com.tlw.blog.dao.pojo.SysUser;
import com.tlw.blog.vo.Result;

public interface SysUserService {
    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    SysUser findUserById(Long id);

    /**
     * 根据账户与密码查询用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
