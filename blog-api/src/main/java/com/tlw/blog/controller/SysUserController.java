package com.tlw.blog.controller;

import com.tlw.blog.service.SysUserService;
import com.tlw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }
}
