package com.flyht.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.flyht.test.domain.TwitterDomainService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TwitterService implements ITwitterService{

	/**
	 * This method is the starter method that triggers everything
	 */
	public void initConsole() {
		try {
			Scanner scan = new Scanner(System.in);
			List<String> topics =null;
			int i=5;
			System.out.println("Please enter "+ i + 
					" topics you want  to retrieve tweets seperated by comma (,)  or enter exit to exit: \n");
			while(true) {
				
				
				String input = scan.nextLine();
				while(input.isEmpty()) {
					System.out.println("Kindly provide atleast one criteria to retrieve Tweets: \n");
					input = scan.nextLine();
				}
				
				//This will make sure that whenever user write's exit we will exit the application.
				if(input.equalsIgnoreCase("exit")) {
					scan.close();
					System.exit(0);
					break;
				}
				
				
				String[] tokens = null;
				
				if(input.trim().length() > 0) {
					tokens = input.split(",");
				}
				
				topics = new ArrayList<String>(Arrays.asList(tokens));
				
				executeReaderWriter(topics);
				
				
			}
			
			
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * This Method uses ExecutorService to create the number 
	 * of threads based on what ever user 
	 * provides and then process those in parallel
	 * 
	 * @param topics
	 * @throws Exception
	 */
	public void executeReaderWriter(List<String> topics) throws Exception {
	    ExecutorService executorService =  Executors.newFixedThreadPool(topics.size());	
	    
	    topics.forEach((String s) -> {
	    	
	    	Future<String> task = executorService
	    			.submit(new TwitterDomainService(s));
	    });
		
	}
}
