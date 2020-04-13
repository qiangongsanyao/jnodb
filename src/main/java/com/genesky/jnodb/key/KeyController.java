package com.genesky.jnodb.key;

import java.util.List;

public interface KeyController {

	/**
	 * 删除过期的键
	 * 
	 * @param key
	 * @return 键是否存在
	 */
	boolean del(String key);

	/**
	 * 检查给定 key 是否存在
	 * 
	 * @param key
	 * @return 键是否存在
	 */
	boolean exists(String key);

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param seconds 秒
	 * @return 键是否存在
	 */
	boolean expire(String key, Integer seconds);

	/**
	 * 设置key的过期时间
	 * 
	 * @param key          毫秒
	 * @param milliseconds
	 * @return 键是否存在
	 */
	boolean pexpire(String key, Long milliseconds);

	/**
	 * 以秒为单位返回 key 的剩余过期时间
	 * 
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以秒为单位，返回 key
	 *         的剩余生存时间。
	 */
	int ttl(String key);

	/**
	 * Find all keys match the given pattern.<br/>
	 * 符合给定模式的 key 列表 (Array)<br/>
	 * Returns all keys matching pattern.<br/>
	 * <br/>
	 * While the time complexity for this operation is O(N), the constant times are
	 * fairly low. For example, Redis running on an entry level laptop can scan a 1
	 * million key database in 40 milliseconds.<br/>
	 * <br/>
	 * <strong>Warning</strong>: consider KEYS as a command that should only be used in production
	 * environments with extreme care. It may ruin performance when it is executed
	 * against large databases. This command is intended for debugging and special
	 * operations, such as changing your keyspace layout. Don't use KEYS in your
	 * regular application code. If you're looking for a way to find keys in a
	 * subset of your keyspace, consider using SCAN or sets.<br/>
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

	/**
	 * 以毫秒为单位返回 key 的剩余过期时间
	 * 
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以毫秒为单位，返回 key
	 *         的剩余生存时间。
	 */
	long pttl(String key);

}
