package com.genesky.jnodb.pool;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.genesky.jnodb.error.CheckErrorException;
import com.genesky.jnodb.tools.CheckTools;

@Component
public class TimingPoolImpl implements TimingPool {

	/**
	 * 所有对不存在值的修改请加锁<br/>
	 * 所有删除请加锁
	 */
	Map<String, Value> pool = new ConcurrentHashMap<String, Value>();

	/**
	 * 定时删除任务
	 */
	public void autoDelete() {

	}

	@Override
	public void set(String key, Object value) {
		Value mvalue = new Value();
		mvalue.obj = value;
		safeput(key, mvalue);
	}

	@Override
	public Object get(String key) {
		Value value = getValidKey(key);
		if (value != null) {
			return value.obj;
		}
		return null;
	}

	private Value getValidKey(String key) {
		Value value = pool.get(key);
		if (value != null && valid(key, value)) {
			return value;
		}
		return null;
	}

	private boolean valid(String key, Value value) {
		if (value.expire_time == null || value.expire_time > System.currentTimeMillis()) {
			return true;
		}
		safedel(key, value);
		return false;
	}

	/**
	 * 安全删除 key 需要确保此时的value = oldvalue
	 * 
	 * @param key
	 * @param value
	 */
	private void safedel(String key, Value value) {
		synchronized (pool) {
			Value tvalue = pool.get(key);
			if (tvalue != null && tvalue == value) {
				pool.remove(key);
			}
		}
	}

	@Override
	public boolean exists(String key_name) {
		Value value = pool.get(key_name);
		if (value != null && valid(key_name, value)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean pexpire(String key, long milliseconds) {
		Value value = getValidKey(key);
		if (value != null) {
			value.expire_time = System.currentTimeMillis() + milliseconds;
			return true;
		}
		return false;
	}

	@Override
	public long pttl(String key) {
		Value value = getValidKey(key);
		if (value != null) {
			if (value.expire_time != null) {
				return value.expire_time - System.currentTimeMillis();
			} else {
				return -1;
			}
		}
		return -2;
	}

	@Override
	public String type(String key_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		Object value = get(key);
		if (value != null) {
			return CheckTools.checkType(value, clazz);
		}
		return null;
	}

	@Override
	public Object del(String key) {
		Value value = pool.remove(key);
		if (value != null && value.expire_time > System.currentTimeMillis()) {
			return value.obj;
		}
		return null;
	}

	@Override
	public boolean set(String key, Object value, long time, boolean nx, boolean xx) {
		Value mvalue = new Value();
		mvalue.obj = value;
		if (time >= 0) {
			mvalue.expire_time = System.currentTimeMillis() + time;
		}
		if (nx) {
			Value nvalue = getValidKey(key);
			if (nvalue == null) {
				mvalue = pool.putIfAbsent(key, mvalue);
				if (mvalue == null) {
					return true;
				}
			}
			return false;
		} else if (xx) {
			Value nvalue = getValidKey(key);
			if (nvalue != null) {
				synchronized (nvalue) {
					nvalue.obj = mvalue.obj;
					nvalue.expire_time = mvalue.expire_time;
					return true;
				}
			}
			return false;
		} else {
			safeput(key, mvalue);
			return true;
		}
	}

	private void safeput(String key, Value mvalue) {
		mvalue = pool.putIfAbsent(key, mvalue);
		/**
		 * 如果该key已存在，加锁后操作
		 */
		if (mvalue != null) {
			synchronized (pool) {
				pool.put(key, mvalue);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getset(String key, T value) {
		Value ovalue = getValidKey(key);
		if (ovalue != null) {
			if (ovalue.obj.getClass() != value.getClass()) {
				throw new CheckErrorException("WRONGTYPE Operation against a key holding the wrong kind of value");
			} else {
				T oldvalue = (T) ovalue.obj;
				ovalue.obj = value;
				return oldvalue;
			}
		} else {
			Value mvalue = new Value();
			mvalue.obj = value;
			safeput(key, mvalue);
			return null;
		}
	}

	@Override
	public List<String> keys(String pattern) {
		StringBuffer newpattern = new StringBuffer();
		for (int i = 0; i < pattern.length(); i++) {
			char tchar = pattern.charAt(i);
			if (tchar == '?' || tchar == '*') {
				if (i > 0) {
					if (pattern.charAt(i - 1) != '/') {
						newpattern.append('.');
					}
				} else {
					newpattern.append('.');
				}
			}
			newpattern.append(tchar);
		}
		Pattern p = Pattern.compile(newpattern.toString());
		return pool.keySet().stream().filter(key -> p.matcher(key).matches()).collect(Collectors.toList());
	}

}
