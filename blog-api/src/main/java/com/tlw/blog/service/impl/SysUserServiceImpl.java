package com.tlw.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tlw.blog.mapper.SysUserMapper;
import com.tlw.blog.mapper.pojo.SysUser;
import com.tlw.blog.service.SysUserService;
import com.tlw.blog.service.TokenService;
import com.tlw.blog.vo.ErrorCode;
import com.tlw.blog.vo.LoginUserVo;
import com.tlw.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TokenService tokenService;


    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("Mr Tao");
        }

        return sysUserMapper.selectById(id);
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1. token合法性校验：是否为空，解析是否成功，redis是否存在
         * 2. 如果校验失败：返回错误
         * 3. 如果校验成功：返回对应结果 LoginUserVo
         */
        SysUser sysUser = tokenService.checkToken(token);
        if (sysUser == null) {
            Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }

        System.out.println("=======>" + sysUser);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());

        return Result.success(loginUserVo);
    }
}
