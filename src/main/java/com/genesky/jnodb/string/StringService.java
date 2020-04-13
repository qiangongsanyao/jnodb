package com.genesky.jnodb.string;

public interface StringService {

	/**
	 * Set the value of key!<br/>
	 * 设置指定 key 的值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key, String value);

	/**
	 * Get the value of key.<br/>
	 * 用于获取指定 key 的值
	 * 
	 * @param key
	 * @return 返回 key 的值，如果 key 不存在时，返回 null。 如果 key 不是字符串类型，那么返回一个错误。
	 */
	String get(String key);

	/**
	 * Set the value of key!<br/>
	 * 设置指定 key 的值
	 * 
	 * @param key
	 * @param value
	 * @param ex    Set the specified expire time, in seconds.
	 * @param px    Set the specified expire time, in milliseconds.
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
