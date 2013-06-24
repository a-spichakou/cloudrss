package app.engine.rss.dao;

import java.util.List;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

public interface FeedDAO {

	public void addFeed(FeedEntity feedEntity);
	
	public void removeFeed(Long feedId);

	public void addFeedItems(Long feedId, List<ItemEntity> entities);

	public List<FeedEntity> getFeeds();

	public FeedEntity getFeed(Long feedId);

}