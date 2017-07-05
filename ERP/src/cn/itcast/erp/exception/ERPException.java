package cn.itcast.erp.exception;

/**
 * 自定义异常
 * @author Administrator
 *
 */
public class ERPException extends RuntimeException {
	public ERPException(String message){
		super(message);
	}
}
