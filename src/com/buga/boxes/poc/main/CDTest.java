package com.buga.boxes.poc.main;

import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.buga.boxes.poc.common.Constants;
import com.buga.boxes.storage.StorageInterface;


public class CDTest {
	
	static {
		Logger.getAnonymousLogger().info("Version : "+Constants.version);
	}
	
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(1);
		executor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try (StorageInterface storageInterface=new StorageInterface()){
					storageInterface.pushData();
					Logger.getAnonymousLogger().info("Ping!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 0, 1, TimeUnit.SECONDS);
		
		executor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try (StorageInterface storageInterface=new StorageInterface()){
					Logger.getAnonymousLogger().info(Arrays.toString(storageInterface.pullData().toArray()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}, 0, 5, TimeUnit.SECONDS);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				Logger.getAnonymousLogger().info("Shutting down!");
			}
		}));
	}
}
