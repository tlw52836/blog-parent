package com.tlw.blog.controller;

import com.tlw.blog.dao.pojo.SysUser;
import com.tlw.blog.utils.UserThreadLocal;
import com.tlw.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}