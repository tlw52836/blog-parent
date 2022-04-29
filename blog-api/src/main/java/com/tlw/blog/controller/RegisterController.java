package com.tlw.blog.controller;

import com.tlw.blog.service.LoginService;
import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    /**
     * 注册
     * @param loginParams
     * @return
     */
    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        //sso 单点登录 后期把登录注册功能提出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParams);
    }
}
