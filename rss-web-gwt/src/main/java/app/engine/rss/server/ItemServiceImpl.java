package app.engine.rss.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import app.engine.rss.client.ItemService;
import app.engine.rss.dao.FeedDAO;
import app.engine.rss.dao.ItemDAO;
import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;
import app.engine.rss.shared.dto.ItemDTO;
import app.engine.rss.shared.exception.ServiceException;

public class ItemServiceImpl extends SpringGwtServlet implements ItemService {
	private static final long serialVersionUID = 5239074901131504225L;
	private static final Logger log = Logger.getLogger(ItemServiceImpl.class.getName());

	private ItemDAO itemDAO;
	private FeedDAO feedDAO;
	private FeedParserImpl feedParser;
	
	/* (non-Javadoc)
	 * @see app.engine.rss.server.ItemService#loadItems(java.lang.Long)
	 */
	public ItemDTO[] loadItems(Long feedId) throws ServiceException
	{
		final List<ItemEntity> items = itemDAO.getItems(feedId);
		final EntityToDTOMapper<ItemEntity, ItemDTO> mapper = new EntityToDTOMapper<ItemEntity,ItemDTO>();
		List<ItemDTO> dto;
		try {
			dto = mapper.getDTO(ItemDTO.class, items);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unable to clone entity to DTO: " + e.getLocalizedMessage());
			throw new ServiceException("Unable to clone entity to DTO",e);
		} 
		return dto.toArray(new ItemDTO[]{});
	}
	
	/* (non-Javadoc)
	 * @see app.engine.rss.server.ItemService#downloadNewItems(java.lang.Long)
	 */
	public void downloadNewItems(Long feedId) throws ServiceException
	{
		final FeedEntity feed = feedDAO.getFeed(feedId);
		if(feed==null)
		{
			return;
		}
		URL url = null;
		try {
			url = new URL(feed.getLink());
		} catch (MalformedURLException e) {
			log.log(Level.SEVERE, "Feed url is not valid: " + feed.getLink());
			throw new ServiceException("Feed url is not valid: " + feed.getLink(),e);
		}
		List<ItemEntity> items;
		try {
			items = feedParser.populateItemEntitiesAtom(url);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Unable to parse feed: " + feed.getLink() + " " + e.getLocalizedMessage());
			throw new ServiceException("Unable to parse feed: " + feed.getLink(),e);
		} 
		
		for(ItemEntity item: items)
		{
			item.setFeedId(feed.getId());
			itemDAO.addItemAndSkipExisting(item);
		}
	}
	
	/* (non-Javadoc)
	 * @see app.engine.rss.server.ItemService#markAsRead(java.lang.Long)
	 */
	public void markAsRead(Long itemId)
	{
		final ItemEntity item = itemDAO.getItem(itemId);
		item.setRead(true);
		itemDAO.saveItem(item);
	}

	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	@Autowired
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public FeedParserImpl getFeedParser() {
		return feedParser;
	}

	@Autowired
	public void setFeedParser(FeedParserImpl feedParser) {
		this.feedParser = feedParser;
	}

	public FeedDAO getFeedDAO() {
		return feedDAO;
	}

	@Autowired
	public void setFeedDAO(FeedDAO feedDAO) {
		this.feedDAO = feedDAO;
	}	
}
