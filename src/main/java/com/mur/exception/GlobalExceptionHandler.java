package com.mur.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 异常处理
 * @author MuR
 * @email lshanyuye@qq.com
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionInfo> handleBusinessException(HttpServletRequest request, Exception e){
        return handleException(e, request, e.getMessage());
    }
    
    @ExceptionHandler(UnknownAccountException.class)
    public ResponseEntity<ExceptionInfo> handleUnknownAccountException(HttpServletRequest request, Exception e){
        return handleException(e, request, e.getMessage());
    }
    
    @ExceptionHandler(LockedAccountException.class)
    public ResponseEntity<ExceptionInfo> handleLockedAccountException(HttpServletRequest request, Exception e){
        return handleException(e, request, e.getMessage());
    }
    
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<ExceptionInfo> handleIncorrectCredentialsException(HttpServletRequest request, Exception e){
        return handleException(e, request, "密码错误");
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInfo> handlesException(HttpServletRequest request, Exception e){
        return handleException(e, request, "系统错误，请联系管理员");
    }
    
    
    
    private ResponseEntity<ExceptionInfo> handleException(Exception e, HttpServletRequest request, String msg){
        ExceptionInfo exceptionInfo = constructExceptionInfo(request.getRequestURI(), e, msg);
        return new ResponseEntity<ExceptionInfo>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }
    
    private ExceptionInfo constructExceptionInfo(String uri, Exception e, String msg) {
        e.printStackTrace();
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setException(e.getClass().getName());
        exceptionInfo.setMsg(msg);
        exceptionInfo.setPath(uri);
        return exceptionInfo;
    }
}
