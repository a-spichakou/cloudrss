package app.engine.rss.client;


import app.engine.rss.shared.dto.FeedDTO;
import app.engine.rss.shared.exception.ServiceException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("feed")
public interface FeedService extends RemoteService {

	public Long addFeed(String url) throws ServiceException;
	
	public void removeFeed(Long id) throws IllegalArgumentException;
	
	public FeedDTO getFeed(Long id) throws ServiceException;
	
	public FeedDTO[] getFeeds() throws ServiceException;

}