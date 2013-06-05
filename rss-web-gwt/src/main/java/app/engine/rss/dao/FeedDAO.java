package app.engine.rss.dao;

import java.util.List;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

public interface FeedDAO {

	public abstract void addNewFeed(FeedEntity feedEntity);

	public abstract void addNewFeedItems(Long feedId, List<ItemEntity> entities);

	public abstract List<FeedEntity> getFeeds();

	public FeedEntity getFeed(Long id);

}