package com.genesky.jnodb.pool;

public class Value {

	volatile Object obj;
	volatile Long expire_time;

	@Override
	public String toString() {
		return "Value [obj=" + obj + ", expire_time=" + expire_time + "]";
	}

}
