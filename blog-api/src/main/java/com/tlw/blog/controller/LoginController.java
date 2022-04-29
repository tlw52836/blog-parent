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
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }
}
