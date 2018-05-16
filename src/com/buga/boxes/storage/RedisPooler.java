package com.buga.boxes.storage;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPooler implements AutoCloseable{
	private JedisPool pool;
	private final String LOCALHOST="redis://localhost:6379";
	
	public RedisPooler() {
		Logger.getAnonymousLogger().info("Initializing redis pool...");
		createClient();
	}
	
	protected Jedis getClient() {
		if(null==pool || pool.isClosed())
			createClient();
		return pool.getResource();
	}
	
	private void createClient() {
		URI host=null;
		try {
			host=new URI(LOCALHOST);
		} catch (URISyntaxException e) {
			Logger.getAnonymousLogger().warning("Error while creating redis client");
		}
		createClient(host);
	}
	
	private void createClient(URI url) {
		pool=new JedisPool(new JedisPoolConfig(),url);
	}

	@Override
	public void close() throws Exception {
		Logger.getAnonymousLogger().info("Closing redis pool...");
		pool.close();
		pool.destroy();
		Logger.getAnonymousLogger().info("Destroyed redis pool successfully!");
	}
}
