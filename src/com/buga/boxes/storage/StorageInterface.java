package com.buga.boxes.storage;

import java.util.Set;

public class StorageInterface extends RedisPooler{
	
	private final String test="test";
	
	public void pushData() {
		long time=System.currentTimeMillis();
		getClient().zadd(test, time, time+"");
	}
	
	public Set<String> pullData() {
		return getClient().zrange(test, 0, 0);
	}
}
