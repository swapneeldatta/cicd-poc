package com.buga.boxes.storage;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class StorageInterface extends RedisPooler{
	
	private final String test="test";
	
	public void pushData() {
		long time=System.currentTimeMillis();
		try(Jedis cJedis=getClient()){
			cJedis.zadd(test, time, time+"");
		}
	}
	
	public Set<String> pullData() {
		try(Jedis cJedis=getClient()){
			return cJedis.zrange(test, 0, 0);
		}
	}
	
	public Set<String> pullData(int n) {
		try(Jedis cJedis=getClient()){
			return cJedis.zrange(test, -n, -1);
		}
	}
	
	public Long deleteData(Set<String> s) {
		try(Jedis cJedis=getClient()){
			return super.getClient().zrem(test, s.stream().toArray(n->new String[n]));
		}
	}
	
	public String ping() {
		try(Jedis cJedis=getClient()){
			return super.getClient().ping();
		}
	}
	
	public StorageInterface() {
		super();
	}
	
	@Override
	public void close() throws Exception {
		super.close();
	}
}
