package com.tlw.blog.service.impl;

import com.tlw.blog.mapper.SysUserMapper;
import com.tlw.blog.mapper.entity.SysUser;
import com.tlw.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("Mr Tao");
        }

        return sysUserMapper.selectById(id);
    }
}
