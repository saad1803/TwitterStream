package com.flyht.test.domain;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import com.flyht.test.util.TwitterProperties;

import lombok.extern.slf4j.Slf4j;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


@Slf4j
public class TwitterDomainService implements Callable<String>{
	
	private String topicName;

	
	public TwitterDomainService(String topicName) {
		this.topicName = topicName;
	
	}
	
	
	public void readFromTwitterStream() throws Exception {
		TwitterStream twitterStream = getTwitterStreamInstance();
		Path path = Paths.get("src/main/resources/files/"+this.topicName.trim()+".txt");
		twitterStream.addListener(new StatusListener() {

			@Override
			public void onException(Exception arg0) {
				
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				
			}

			@Override
			public void onStatus(Status status) {
				log.info("Inside status listener for" + topicName);
				
				writeToFile(status.getText(), path);
				
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				
			}
			
		});
		FilterQuery fq = new FilterQuery();
		fq.track(this.topicName);
		twitterStream.filter(fq);
		
	}
	
	public void writeToFile(String tweet,Path path) {
		log.info("Inside write File for"+ this.topicName);
		
		try(FileWriter writer = new FileWriter(path.toFile(),true)) {
		
		writer.write(tweet);
		writer.write(System.lineSeparator());
		writer.flush();
				
		}catch(Exception e) {
			log.error(e.getMessage());
		}
	}

	private TwitterStream getTwitterStreamInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(TwitterProperties.CONSUMERKEY)
		.setOAuthConsumerSecret(TwitterProperties.CONSUMERSECRET)
		.setOAuthAccessToken(TwitterProperties.ACCESSKEY)
		.setOAuthAccessTokenSecret(TwitterProperties.ACCESSSECRET);
		
		TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
		return tf.getInstance();
	}
	
	@Override
	public String call() throws Exception {
		try{
//			readFromTwitter();
			readFromTwitterStream();
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		
		return "Success";
	}

}
