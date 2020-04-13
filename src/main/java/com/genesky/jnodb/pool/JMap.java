package com.genesky.jnodb.pool;

import java.util.concurrent.ConcurrentHashMap;

public class JMap extends ConcurrentHashMap<String, Value> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3410601199844723936L;

	public void safeput(String key, Value mvalue) {
		mvalue = putIfAbsent(key, mvalue);
		/**
		 * 如果该key已存在，加锁后操作
		 */
		if (mvalue != null) {
			synchronized (this) {
				super.put(key, mvalue);
			}
		}
	}

	@Override
	public Value put(String key, Value value) {
		throw new RuntimeException("Forbidden!");
	}

	public void safedel(String key, Value value) {
		synchronized (this) {
			Value tvalue = get(key);
			if (tvalue != null && tvalue == value) {
				remove(key);
			}
		}
	}

}
