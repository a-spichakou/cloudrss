package app.engine.rss.client;


import app.engine.rss.shared.dto.FeedDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("feed")
public interface FeedService extends RemoteService {

	public Long addFeed(String url) throws IllegalArgumentException;
	
	public void removeFeed(Long id) throws IllegalArgumentException;
	
	public FeedDTO getFeed(Long id) throws IllegalArgumentException;
	
	public FeedDTO[] getFeeds() throws IllegalArgumentException;

}