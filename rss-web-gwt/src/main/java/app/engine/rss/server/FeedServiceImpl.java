package app.engine.rss.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.engine.rss.client.FeedService;
import app.engine.rss.dao.FeedDAO;
import app.engine.rss.dao.FeedDAOImpl;
import app.engine.rss.entity.FeedEntity;
import app.engine.rss.shared.dto.FeedDTO;

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

	public FeedDTO getFeed(Long id) throws IllegalArgumentException {
		assert(id!=null);
		final FeedEntity feed = feedDAO.getFeed(id);
		final FeedDTO dto = EntityToDTOMapper.getDTO(feed);
		return dto;
	}

	public FeedDTO[] getFeeds() throws IllegalArgumentException {
		final List<FeedEntity> feeds = feedDAO.getFeeds();
		final List<FeedDTO> dto = EntityToDTOMapper.getDTO(feeds);
		return dto.toArray(new FeedDTO[]{});
	}

}
