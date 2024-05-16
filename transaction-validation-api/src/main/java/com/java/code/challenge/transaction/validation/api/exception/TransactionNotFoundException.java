package com.java.code.challenge.transaction.validation.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 9085762026269683548L;

	public TransactionNotFoundException(String message) {
		super(message);
	}
}
