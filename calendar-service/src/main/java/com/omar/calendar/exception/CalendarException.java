package com.fdc.ucom.address.exception;

import com.fdc.ucom.address.enums.UComExceptionCode;

public class UComException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private UComExceptionCode code;
	
	private String message;
	
	public UComException(UComExceptionCode code) {
		super(code.getMessage());
		this.code = code;
		this.message = code.getMessage();
	}
	
	public UComException(UComExceptionCode code, String message) {
		super(message);
		this.message = message;
		this.code = code;
	}

	public UComException(UComExceptionCode code, Exception ex) {
		super(ex);
		this.message = code.getMessage();
	}
	

	public UComExceptionCode getCode() {
		return code;
	}

	public void setCode(UComExceptionCode code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
