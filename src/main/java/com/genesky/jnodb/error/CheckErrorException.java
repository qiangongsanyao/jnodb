package com.genesky.jnodb.error;

public class CheckErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8703583410534803214L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CheckErrorException() {
		super();
	}

	public CheckErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CheckErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public CheckErrorException(String message) {
		super(message);
	}

	public CheckErrorException(Throwable cause) {
		super(cause);
	}

}
