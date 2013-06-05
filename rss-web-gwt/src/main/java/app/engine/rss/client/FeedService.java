package app.engine.rss.client;

import app.engine.rss.entity.FeedEntity;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("feed")
public interface FeedService extends RemoteService {

	public Long addFeed(String url) throws IllegalArgumentException;
	
	public FeedEntity getFeed(Long id) throws IllegalArgumentException;
	
	public FeedEntity[] getFeeds() throws IllegalArgumentException;

}