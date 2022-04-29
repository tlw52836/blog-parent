package com.tlw.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.tlw.blog.dao.pojo.SysUser;
import com.tlw.blog.service.TokenService;
import com.tlw.blog.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isEmpty(userJson)) {
            return null;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);

        return sysUser;
    }
}
