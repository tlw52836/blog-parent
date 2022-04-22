package com.tlw.blog.handler;

import com.tlw.blog.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//对加了@controller注解的方法进行拦截处理 AOP的实现
@RestControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
