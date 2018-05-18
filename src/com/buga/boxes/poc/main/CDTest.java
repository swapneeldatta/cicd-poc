package com.buga.boxes.poc.main;

import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.buga.boxes.poc.common.Constants;
import com.buga.boxes.storage.StorageInterface;

import redis.clients.jedis.exceptions.JedisConnectionException;

public class CDTest {
	
	static {
		Logger.getAnonymousLogger().info("This is a test for CI-CD auto deployment.\nVersion : "+Constants.version);
	}
	
	public static void main(String[] args) throws Exception{
		ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(1);
		StorageInterface storageInterface = new StorageInterface();
		storageInterface.ping();	// Will close application if redis ain't running.
		
		executor.scheduleAtFixedRate(() -> {
			try {
				storageInterface.pushData();
				Logger.getAnonymousLogger().info("Ping!");
			} catch (JedisConnectionException e) {
				Logger.getAnonymousLogger().log(Level.WARNING, "Problem connecting to Jedis.", e.getSuppressed());
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage(), e);
			}
		} ,0, 1,TimeUnit.SECONDS);
		
		executor.scheduleAtFixedRate(()-> {
			try {
				Logger.getAnonymousLogger().info(Arrays.toString(storageInterface.pullData(6).toArray()));
			} catch (JedisConnectionException e) {
				Logger.getAnonymousLogger().log(Level.WARNING, "Problem connecting to Jedis.", e.getSuppressed());
			} catch (Exception e) {
				Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage(), e);
			}
		}, 0, 5, TimeUnit.SECONDS);
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				Logger.getAnonymousLogger().info("Shutting down!");
				try {
					storageInterface.close();
				} catch (Exception e) {
					Logger.getAnonymousLogger().log(Level.WARNING, e.getMessage(), e);
				}
			}
		}));
	}
}
