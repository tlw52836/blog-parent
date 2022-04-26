package com.tlw.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.tlw.blog.mapper.pojo.SysUser;
import com.tlw.blog.service.LoginService;
import com.tlw.blog.service.SysUserService;
import com.tlw.blog.utils.JWTUtils;
import com.tlw.blog.vo.ErrorCode;
import com.tlw.blog.vo.Result;
import com.tlw.blog.vo.params.LoginParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //加密盐，是的md5不容易被破解
    private static final String slat = "tlw!@#";

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user表中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在，使用jwt 生成token 返回给前端
         * 5. token放入redis当中 redis存放格式：token：user信息 设置过期时间
         */

        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account, password);

        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
}
