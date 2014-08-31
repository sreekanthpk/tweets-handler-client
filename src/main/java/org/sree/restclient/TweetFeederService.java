package org.sree.restclient;

import java.util.List;

public interface TweetFeederService {
	List<String> getTweetFrom(String userId);
}
