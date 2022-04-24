package com.tlw.blog.service;

import com.tlw.blog.mapper.pojo.SysUser;
import com.tlw.blog.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);
}
