package com.tlw.blog.service;

import com.tlw.blog.mapper.pojo.SysUser;

public interface TokenService {
    SysUser checkToken(String token);
}
