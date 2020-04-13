package com.genesky.jnodb.pool;

import java.util.List;

public interface TimingPool {

	/**
	 * 设置key的value
	 * 
	 * @param key
	 * @param value
	 */
	void set(String key, Object value);

	/**
	 * 返回key的value
	 * 
	 * @param key
	 * @return value
	 */
	Object get(String key);

	/**
	 * 检查给定 key 是否存在
	 * 
	 */
	boolean exists(String key_name);

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param seconds 秒数
	 * @return 设置成功，返回 1<br/>
	 *         key 不存在或设置失败，返回 0
	 */
	default boolean expire(String key, int seconds) {
		return pexpire(key, seconds * 1000);
	}

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param milliseconds 毫秒数
	 * @return 设置成功，返回 1<br/>
	 *         key 不存在或设置失败，返回 0
	 */
	boolean pexpire(String key, long milliseconds);

	/**
	 * 以秒为单位返回 key 的剩余过期时间
	 * 
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以秒为单位，返回 key
	 *         的剩余生存时间。
	 */
	default int ttl(String key) {
		long time = pttl(key);
		if (time > 0) {
			return (int) (pttl(key) / 1000);
		} else {
			return (int) time;
		}
	}

	/**
	 * 以毫秒为单位返回 key 的剩余过期时间
	 * 
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以毫秒为单位，返回 key
	 *         的剩余生存时间。
	 */
	long pttl(String key);

	/**
	 * 返回 key 所储存的值的类型
	 * 
	 * @param key_name
	 * @return none (key不存在)<br/>
	 *         string (字符串)<br/>
	 *         list (列表)<br/>
	 *         set (集合)<br/>
	 *         zset (有序集)<br/>
	 *         hash (哈希表)<br/>
	 */
	String type(String key_name);

	/**
	 * 返回key的value
	 * 
	 * @param key
	 * @return value
	 */
	<T> T get(String key, Class<T> class1);

	/**
	 * 删除已存在的键
	 * 
	 * @param key
	 * @return 删除的键
	 */
	Object del(String key);

	/**
	 * 设置指定 key 的值
	 * 
	 * @param key
	 * @param value
	 * @param time  Set the specified expire time, in milliseconds. -1 if notset it.
	 * @param nx    Only set the key if it does not already exist.
	 * @param xx    Only set the key if it already exist.
	 * @return true is success
	 */
	boolean set(String key, Object value, long time, boolean nx, boolean xx);

	/**
	 * Atomically sets key to value and returns the old value stored at key.<br/>
	 * Returns an error when key exists but does not hold a correct type value.
	 * 
	 * @param key
	 * @param newvalue
	 * @return the old value stored at key, or null when key did not exist.
	 */
	<T> T getset(String key, T value);

	/**
	 * Find all keys match the given pattern.<br/>
	 * 符合给定模式的 key 列表 (Array)<br/>
	 * Returns all keys matching pattern.<br/>
	 * <br/>
	 * While the time complexity for this operation is O(N), the constant times are
	 * fairly low. For example, Redis running on an entry level laptop can scan a 1
	 * million key database in 40 milliseconds.<br/>
	 * <br/>
	 * <strong>Warning</strong>: consider KEYS as a command that should only be used
	 * in production environments with extreme care. It may ruin performance when it
	 * is executed against large databases. This command is intended for debugging
	 * and special operations, such as changing your keyspace layout. Don't use KEYS
	 * in your regular application code. If you're looking for a way to find keys in
	 * a subset of your keyspace, consider using SCAN or sets.<br/>
	 * <br/>
	 * Supported glob-style patterns:<br/>
	 * <ul>
	 * <li>h?llo matches hello, hallo and hxllo</li>
	 * <li>h*llo matches hllo and heeeello</li>
	 * <li>h[ae]llo matches hello and hallo, but not hillo</li>
	 * <li>h[^e]llo matches hallo, hbllo, ... but not hello</li>
	 * <li>h[a-b]llo matches hallo and hbllo</li>
	 * </ul>
	 * Use \ to escape special characters if you want to match them verbatim<br/>
	 * 
	 * @param pattern
	 * @return list of keys matching pattern.
	 */
	List<String> keys(String pattern);

}
