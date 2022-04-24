package com.tlw.blog.controller;

import com.alibaba.fastjson.JSON;
import com.tlw.blog.mapper.pojo.SysUser;
import com.tlw.blog.service.SysUserService;
import com.tlw.blog.vo.ErrorCode;
import com.tlw.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }
}
