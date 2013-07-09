package app.engine.rss.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.engine.rss.client.FeedService;
import app.engine.rss.dao.FeedDAO;
import app.engine.rss.dao.FeedDAOImpl;
import app.engine.rss.entity.FeedEntity;
import app.engine.rss.shared.dto.FeedDTO;
import app.engine.rss.shared.exception.ServiceException;

@Service
public class FeedServiceImpl extends SpringGwtServlet implements
		FeedService {

	private static final Logger log = Logger.getLogger(FeedServiceImpl.class.getName());
	private static final long serialVersionUID = -7745736807965134047L;
	private FeedDAOImpl feedDAO;
	private FeedParserImpl feedParser;	

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.engine.rss.server.FeedService#addFeed(java.lang.String)
	 */
	public Long addFeed(String url) throws ServiceException {
		URL urlObj;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e) {
			log.log(Level.SEVERE, "Given URL is not valid: " + e.getLocalizedMessage());
			throw new ServiceException("Given URL is not valid",e);
		}
		
		FeedEntity entity = null;
		
		try {
			entity = feedParser.populateFeedEntityAtom(urlObj);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unable to parse feed XML file: " + e.getLocalizedMessage());
			throw new ServiceException("Unable to parse feed XML file",e);
		}
				
		entity.setLink(url);
		feedDAO.addFeed(entity);
		return entity.getId();
	}
	
	public FeedDTO getFeed(Long id) throws ServiceException{
		assert(id!=null);
		final FeedEntity feed = feedDAO.getFeed(id);
		final EntityToDTOMapper<FeedEntity, FeedDTO> mapper = new EntityToDTOMapper<FeedEntity,FeedDTO>();
		FeedDTO dto;
		try {
			dto = mapper.getDTO(FeedDTO.class,feed);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unable to clone entity to DTO: " + e.getLocalizedMessage());
			throw new ServiceException("Unable to clone entity to DTO",e);
		}
		return dto;
	}

	public FeedDTO[] getFeeds() throws ServiceException {
		final List<FeedEntity> feeds = feedDAO.getFeeds();
		final EntityToDTOMapper<FeedEntity, FeedDTO> mapper = new EntityToDTOMapper<FeedEntity,FeedDTO>();
		List<FeedDTO> dto;
		try {
			dto = mapper.getDTO(FeedDTO.class, feeds);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unable to clone entity to DTO: " + e.getLocalizedMessage());
			throw new ServiceException("Unable to clone entity to DTO",e);
		} 
		return dto.toArray(new FeedDTO[]{});
	}

	
	public void removeFeed(Long id)
	{
		assert(id!=null);
		feedDAO.removeFeed(id);
	}

	public FeedDAO getFeedDAO() {
		return feedDAO;
	}

	@Autowired
	public void setFeedDAO(FeedDAOImpl feedDAO) {
		this.feedDAO = feedDAO;
	}
	
	@Autowired
	public void setFeedParser(FeedParserImpl feedParser) {
		this.feedParser = feedParser;
	}
		
}
