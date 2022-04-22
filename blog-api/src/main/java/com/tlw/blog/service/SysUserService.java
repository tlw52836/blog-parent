package com.tlw.blog.service;

import com.tlw.blog.mapper.entity.SysUser;

public interface SysUserService {
    SysUser findUserById(Long id);
}
