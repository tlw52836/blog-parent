package com.tlw.blog.service;

import com.tlw.blog.mapper.pojo.SysUser;

public interface TokenService {
    /**
     * 校验token，并返回SysUser
     * @param token
     * @return
     */
    SysUser checkToken(String token);
}
