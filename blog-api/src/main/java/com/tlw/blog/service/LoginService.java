package com.tlw.blog.service;

import com.tlw.blog.mapper.pojo.SysUser;
import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);
}