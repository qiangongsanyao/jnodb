package com.genesky.jnodb.pool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(-2)
public class PoolConfiguration {

	@Autowired
	ApplicationContext context;

	int database = 0;

	Map<Integer, TimingPool> pools = new ConcurrentHashMap<>();

	public TimingPool pool() {
		System.out.println("request");
		return getDatabase(database);
	}

	public TimingPool getDatabase(int database) {
		TimingPool pool = pools.get(database);
		if (pool == null) {
			synchronized (pools) {
				pool = pools.get(database);
				if (pool == null) {
					pool = newpool();
					pools.put(database, pool);
				}
			}
		}
		return pool;
	}

	private TimingPool newpool() {
		TimingPool pool = new TimingPoolImpl();
		return pool;
	}

}
