package app.engine.rss.dao;

import static app.engine.rss.dao.OfyService.ofy;

import java.util.List;

import org.springframework.stereotype.Service;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

import com.googlecode.objectify.cmd.LoadType;

@Service
public class FeedDAOImpl implements FeedDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * app.engine.rss.server.FeedDAO#addNewFeed(app.engine.rss.entity.FeedEntity
	 * )
	 */
	public void addNewFeed(FeedEntity feedEntity) {
		ofy().save().entity(feedEntity).now();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.engine.rss.server.FeedDAO#addNewFeedItems(java.lang.Long,
	 * java.util.List)
	 */
	public void addNewFeedItems(Long feedId, List<ItemEntity> entities) {
		ofy().save().entities(entities).now();
	}
	
	public void getFeedItems(Long feedId,String quiery, Object value) {
		ofy().load().type(ItemEntity.class).filter(quiery, value);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.engine.rss.server.FeedDAO#getFeeds()
	 */
	public List<FeedEntity> getFeeds() {
		final LoadType<FeedEntity> type = ofy().load().type(FeedEntity.class);
		return type.list();
	}

	public FeedEntity getFeed(Long id) {
		assert(id!=null);
		return ofy().load().now(FeedEntity.key(id));
	}
	
	
}
