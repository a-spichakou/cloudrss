package app.engine.rss.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.engine.rss.client.FeedService;
import app.engine.rss.dao.FeedDAO;
import app.engine.rss.dao.FeedDAOImpl;
import app.engine.rss.entity.FeedEntity;

@Service
public class FeedServiceImpl extends SpringGwtServlet implements
		FeedService {

	private static final long serialVersionUID = -7745736807965134047L;
	private FeedDAOImpl feedDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.engine.rss.server.FeedService#addFeed(java.lang.String)
	 */
	public Long addFeed(String url) throws IllegalArgumentException {
		final FeedEntity entity = new FeedEntity();
		entity.setLink(url);
		feedDAO.addNewFeed(entity);
		return entity.getId();
	}

	public FeedDAO getFeedDAO() {
		return feedDAO;
	}

	@Autowired
	public void setFeedDAO(FeedDAOImpl feedDAO) {
		this.feedDAO = feedDAO;
	}

	public String getFeed(Long id) throws IllegalArgumentException {
		return "String";
	}

}
