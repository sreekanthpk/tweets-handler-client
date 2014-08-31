package org.sree.restclient;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Application {
	
	public static void main(String[] args) throws GeneralSecurityException, Exception{
		
		String[] userIds = new String[]{"nyse",
				"sebastiengaly", 
				"kitjuckes", 
				"cnbc", 
				"benzinga", 
				"Lavorgnanomics" };
		
		final TweetFeederService tweetFeederService = new TweetFeederServiceImpl();
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		final CountDownLatch countDownLatch = new CountDownLatch(userIds.length);
		
		final RestTemplate restTemplate= new RestTemplate();	
		
		List<Future<Boolean>> result = new ArrayList<Future<Boolean>>();
		
		
		for (final String userId : userIds) {			
			result.add(executorService.submit(new Callable<Boolean>(){
				@Override
				public Boolean call() throws Exception {
					ResponseEntity<Boolean> exchange = restTemplate.exchange("http://localhost:8080/feed/store/"+userId, HttpMethod.POST, new HttpEntity<List>(tweetFeederService.getTweetFrom(userId)),boolean.class);
					countDownLatch.countDown();
					return exchange.getBody();					
				}
				
			}));				
		}
		
		countDownLatch.await();
		
		for (Future<Boolean> future : result) {
			if(!future.get()){System.out.println("Feed failed");}
		}
		
		executorService.shutdown();
		
		System.out.println("Complete");			
	}

}
