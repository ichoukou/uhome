package com.ytoxl.module.uhome.uhomebase.common.exception;

import com.ytoxl.module.core.common.exception.YTOXLException;


public class UhomeStoreException extends YTOXLException {
	private final static long serialVersionUID = 6476473452739657628L;
	
	public UhomeStoreException() {
		super();
	}

	public UhomeStoreException(String messageOrCode) {
		super(messageOrCode);
	}

	public UhomeStoreException(String messageOrCode, String[] errorValues) {
		super(messageOrCode,errorValues);		
	}

	public UhomeStoreException(String messageOrCode, String errorValue) {
		super(messageOrCode,errorValue);		
	}
	
	public UhomeStoreException(String messageOrCode, Throwable cause) {
		super(messageOrCode, cause);
	}

	public UhomeStoreException(Throwable cause) {
		super(cause);
	}

	public UhomeStoreException(String messageOrCode, String[] errorValues,
			Throwable cause) {
		super(messageOrCode, errorValues,cause);		
	}
	
	public UhomeStoreException(String messageOrCode, String errorValue,
			Throwable cause) {
		super(messageOrCode, errorValue,cause);		
	}	
}
