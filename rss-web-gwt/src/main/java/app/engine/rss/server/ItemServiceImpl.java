package app.engine.rss.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import app.engine.rss.dao.FeedDAOImpl;
import app.engine.rss.entity.ItemEntity;

public class ItemServiceImpl extends SpringGwtServlet {
	private static final long serialVersionUID = 5239074901131504225L;
	private FeedDAOImpl feedDAO;
	private FeedParserImpl feedParser;
	
	public List<ItemEntity> loadItems(long feedId)
	{
		return null;
	}
	
	public void downloadNewItems(long feedId)
	{
		
	}
	
	public void markAsRead(long itemId)
	{
		feedDAO.
	}

	public FeedDAOImpl getFeedDAO() {
		return feedDAO;
	}

	@Autowired
	public void setFeedDAO(FeedDAOImpl feedDAO) {
		this.feedDAO = feedDAO;
	}

	public FeedParserImpl getFeedParser() {
		return feedParser;
	}

	@Autowired
	public void setFeedParser(FeedParserImpl feedParser) {
		this.feedParser = feedParser;
	}

}
