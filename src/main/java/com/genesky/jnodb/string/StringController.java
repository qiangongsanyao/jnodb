package com.genesky.jnodb.string;

import java.util.List;

public interface StringController {

	/**
	 * Get the value of key
	 * 
	 * @param key
	 * @return the value of key, or null when key does not exist.
	 */
	String get(String key);

	/**
	 * Get values of given keys
	 * 
	 * @param key
	 * @return the values of given keys
	 */
	List<String> mget(List<String> keys);

	
	/**
	 * Set the value of key!<br/>
	 * 设置指定 key 的值
	 * 
	 * @param key
	 * @param value
	 * @param px    Set the specified expire time, in milliseconds. -1 if notset it.
	 * @param nx    Only set the key if it does not already exist.
	 * @param xx    Only set the key if it already exist.
	 * @return true is success
	 */
	boolean set(String key, String value, Integer ex, Long px, boolean nx, boolean xx);

	/**
	 * Atomically sets key to value and returns the old value stored at key.<br/>
	 * Returns an error when key exists but does not hold a string value.
	 * 
	 * @param key
	 * @param value
	 * @return the old value stored at key, or null when key did not exist.
	 */
	String getset(String key, String value);

	/**
	 * Get a substring of a string stored at a key!<br/>
	 * 返回 key 中字符串值的子字符
	 * 
	 * @return Returns the substring of the string value stored at key, determined
	 *         by the offsets start and end (both are inclusive). Negative offsets
	 *         can be used in order to provide an offset starting from the end of
	 *         the string. So -1 means the last character, -2 the penultimate and so
	 *         forth.
	 */
	String getrange(String key, int start, int end);

}
