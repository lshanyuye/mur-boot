package com.mur.exception;

/**
 * ҵ���쳣��
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
