package com.genesky.jnodb.key;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.genesky.jnodb.pool.TimingPool;
import com.genesky.jnodb.pool.PoolConfiguration;

@Service
@Order(0)
public class KeyServiceImpl implements KeyService {

	@Autowired
	PoolConfiguration configuration;

	TimingPool pool() {
		return configuration.pool();
	}

	@Override
	public boolean del(String key) {
		return pool().del(key) != null;
	}

	@Override
	public boolean exists(String key) {
		return pool().exists(key);
	}

	@Override
	public boolean expire(String key, int seconds) {
		return pool().expire(key, seconds);
	}

	@Override
	public boolean pexpire(String key, long milliseconds) {
		return pool().pexpire(key, milliseconds);
	}

	@Override
	public int ttl(String key) {
		return pool().ttl(key);
	}

	@Override
	public long pttl(String key) {
		return pool().pttl(key);
	}

	@Override
	public List<String> keys(String pattern) {
		return pool().keys(pattern);
	}

}
