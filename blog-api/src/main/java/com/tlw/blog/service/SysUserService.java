package com.tlw.blog.service;

import com.tlw.blog.mapper.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);
}
