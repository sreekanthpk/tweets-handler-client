package org.sree.restclient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetFeederServiceImpl implements TweetFeederService {
    private String oAuthConsumerKey = "L4sa1OEhKm6izDPcaGTWV9t6G";
    private String oAuthConsumerSecret = "gcrIqObJgSTesHTrTW44vnLIBrbkGv4Ft8pu4iA5JsFJxrK73J"; 

    private String oAuthAccessToken = "58159267-mCM2Cre4rnq5j5yCJFpBS4Zsc9CbWWMPey36Tdxp2";
    private String oAuthAccessTokenSecret = "qw6kESpkqIQvyGKyW1XW3irin2Y1tzuiRY139DQ5Sg3Wf"; 
    
    private Twitter twitter;
    
    public TweetFeederServiceImpl(){
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	        .setOAuthConsumerKey(oAuthConsumerKey)
	        .setOAuthConsumerSecret(oAuthConsumerSecret)
	        .setOAuthAccessToken(oAuthAccessToken)
	        .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    twitter = tf.getInstance();
    }
	
	
	@Override
	public List<String> getTweetFrom(String userId) {		  
	    
	    List<String> tweets= new ArrayList<String>();	  
	    
	    Date from = new Date();
	    from.setTime(1404144000000L);
		   
	    List<Status> statuses=null;
		try {
			statuses = twitter.getUserTimeline(userId,new Paging(1,200));
			for (Status status : statuses) {
			    	tweets.add(status.getText());
			    	if(status.getCreatedAt().before(from))break;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
	    return tweets;
	}
}
