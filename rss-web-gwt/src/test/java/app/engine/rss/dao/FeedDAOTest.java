package app.engine.rss.dao;

import static app.engine.rss.dao.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class FeedDAOTest extends TestCase{

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private FeedDAO feedDao = new FeedDAOImpl();
	
	@Before
    public void setUp() {
        helper.setUp();        
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
    
    @Test
    public void testAddNewFeedItems()
    {
    	final FeedEntity feed = createFeed();
    	feedDao.addNewFeed(feed);
    	
    	final List<ItemEntity> items = createListOfItems(3);
    	feedDao.addNewFeedItems(feed.getId(), items);
    	
    	for(ItemEntity item:items)
    	{
    		final ItemEntity saved = ofy().load().now(ItemEntity.key(item.getId()));
    		
        	assertEquals(item.getId(), saved.getId());
        	assertEquals(item.getDescription(), saved.getDescription());
        	assertEquals(item.getAuthor(), saved.getAuthor());
        	assertEquals(item.getLink(), saved.getLink());
        	assertEquals(item.getTitle(), saved.getTitle());
        	assertEquals(item.getGuid(), saved.getGuid());
        	assertEquals(item.getPubDate(), saved.getPubDate());
    	}
    }
    
    @Test
    public void testGetFeed()
    {
    	final FeedEntity entity = createFeed();
    	feedDao.addNewFeed(entity);
    	
    	final FeedEntity saved = feedDao.getFeed(entity.getId());
    	
    	assertEquals(entity.getId(), saved.getId());
    	assertEquals(entity.getDescription(), saved.getDescription());
    	assertEquals(entity.getImageUrl(), saved.getImageUrl());
    	assertEquals(entity.getLink(), saved.getLink());
    	assertEquals(entity.getTitle(), saved.getTitle());
    }
    
    @Test
    public void testAddNewFeed()
    {
    	final FeedEntity entity = createFeed();
    	feedDao.addNewFeed(entity);
    	
    	final FeedEntity saved = ofy().load().now(FeedEntity.key(entity.getId()));
    	
    	assertEquals(entity.getId(), saved.getId());
    	assertEquals(entity.getDescription(), saved.getDescription());
    	assertEquals(entity.getImageUrl(), saved.getImageUrl());
    	assertEquals(entity.getLink(), saved.getLink());
    	assertEquals(entity.getTitle(), saved.getTitle());
    }
    
    private static FeedEntity createFeed()
    {
    	final FeedEntity entity = new FeedEntity();
    	entity.setDescription(RandomStringUtils.random(10));    	 
    	entity.setImageUrl(RandomStringUtils.random(10));
    	entity.setLink(RandomStringUtils.random(10));
    	entity.setTitle(RandomStringUtils.random(10));
    	return entity;
    }
    
    private static ItemEntity createItem()
    {
    	final ItemEntity entity = new ItemEntity();
    	entity.setDescription(RandomStringUtils.random(10));    	 
    	entity.setAuthor(RandomStringUtils.random(10));
    	entity.setGuid(RandomStringUtils.random(10));
    	entity.setPubDate(RandomStringUtils.random(10));
    	entity.setLink(RandomStringUtils.random(10));
    	entity.setTitle(RandomStringUtils.random(10));
    	return entity;
    }
    
    private static List<ItemEntity> createListOfItems(int count)
    {
    	final List<ItemEntity> items = new ArrayList<ItemEntity>();
    	
    	for(int i=0;i<count;i++)
    	{
    		items.add(createItem());
    	}
    	return items;
    }
}
