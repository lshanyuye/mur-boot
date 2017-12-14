package com.mur.exception;

/**
 * 业务异常类
 * @author MuR
 * @email lshanyuye@qq.com
 */
public class BusinessException extends RuntimeException{
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
