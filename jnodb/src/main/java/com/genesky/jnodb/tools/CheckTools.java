package com.genesky.jnodb.tools;

import com.genesky.jnodb.error.CheckErrorException;

public class CheckTools {

	@SuppressWarnings("unchecked")
	public static <T> T checkType(Object value, Class<T> clazz) {
		if (!clazz.isAssignableFrom(value.getClass())) {
			throw new CheckErrorException("WRONGTYPE Operation against a key holding the wrong kind of value");
		} else {
			return (T) value;
		}
	}

}
