package com.tlw.blog.service;

import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    Result logout(String token);
}
