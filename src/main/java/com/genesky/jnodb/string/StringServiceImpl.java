package com.genesky.jnodb.string;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genesky.jnodb.pool.TimingPool;

@Service
public class StringServiceImpl implements StringService {

	@Autowired
	TimingPool pool;

	@Override
	public boolean set(String key, String value) {
		pool.set(key, value);
		return true;
	}

	@Override
	public String get(String key) {
		String result = pool.get(key, String.class);
		return result;
	}

	@Override
	public boolean set(String key, String value, Integer ex, Long px, boolean nx, boolean xx) {
		long time = -1;
		if (ex != null) {
			time = ex * 1000;
		}
		if (px != null) {
			time = px;
		}
		return pool.set(key, value, time, nx, xx);
	}

	@Override
	public String getset(String key, String value) {
		return pool.getset(key, value);
	}

	@Override
	public String getrange(String key, int start, int end) {
		String value = get(key);
		if (value != null) {
			if (start < 0) {
				start += value.length();
			}
			if (end < 0) {
				end += value.length();
			}
			if (end >= value.length()) {
				end = value.length() - 1;
			}
			if (start > end) {
				return "";
			}
			return value.substring(start, end + 1);
		}
		return "";
	}

}