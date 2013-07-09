package app.engine.rss.dao;

import static app.engine.rss.dao.OfyService.ofy;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import app.engine.rss.entity.ItemEntity;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import junit.framework.TestCase;

public class ItemDAOTest extends TestCase{

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private ItemDAO itemDao = new ItemDAOImpl();
	
	@Before
    public void setUp() {
        helper.setUp();        
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
    
    @Test
    public void testAddItem()
    {
    	final ItemEntity entity = createItem();
    	itemDao.addItem(entity);
    	
    	final ItemEntity saved = ofy().load().now(ItemEntity.key(entity.getId()));
    	assertEquals(entity.getDescription(), saved.getDescription());
    	assertEquals(entity.getAuthor(), saved.getAuthor());
    	assertEquals(entity.getGuid(), saved.getGuid());
    	assertEquals(entity.getPubDate(), saved.getPubDate());
    	assertEquals(entity.getLink(), saved.getLink());
    	assertEquals(entity.getId(), saved.getId());
    	assertEquals(entity.getTitle(), saved.getTitle());
    	assertEquals(entity.getRead(), saved.getRead());
    }
    
    @Test
    public void testGetItem()
    {
    	final ItemEntity entity = createItem();
    	itemDao.addItem(entity);
    	
    	final ItemEntity saved = itemDao.getItem(entity.getId());
    	assertNotNull(saved);
    	
    	assertEquals(entity.getDescription(), saved.getDescription());
    	assertEquals(entity.getAuthor(), saved.getAuthor());
    	assertEquals(entity.getGuid(), saved.getGuid());
    	assertEquals(entity.getPubDate(), saved.getPubDate());
    	assertEquals(entity.getLink(), saved.getLink());
    	assertEquals(entity.getId(), saved.getId());
    	assertEquals(entity.getTitle(), saved.getTitle());
    	assertEquals(entity.getRead(), saved.getRead());
    }
    
    @Test
    public void testRemoveItem()
    {
    	final ItemEntity entity = createItem();
    	itemDao.addItem(entity);
    	final ItemEntity saved = ofy().load().now(ItemEntity.key(entity.getId()));
    	assertEquals(entity.getId(), saved.getId());
    	itemDao.removeItem(entity.getId());
    	final ItemEntity removed = ofy().load().now(ItemEntity.key(entity.getId()));    	
    	assertNull(removed);
    }
    
    @Test
    public void testSaveItem()
    {
    	final ItemEntity entity = createItem();
    	itemDao.addItem(entity);
    	
    	entity.setDescription("New description");
    	itemDao.saveItem(entity);
    	
    	final ItemEntity saved = ofy().load().now(ItemEntity.key(entity.getId()));
    	
    	assertEquals(entity.getDescription(), saved.getDescription());
    }
    
    @Test
    public void testAddItemAndSkipExisting()
    {
    	final ItemEntity entity = createItem();
    	itemDao.addItem(entity);
    	
    	final ItemEntity entity1 = createItem();
    	
    	final boolean addItemAndSkipExisting = itemDao.addItemAndSkipExisting(entity);
    	assertFalse(addItemAndSkipExisting);
    	
    	final boolean addItemAndSkipExisting1 = itemDao.addItemAndSkipExisting(entity1);
    	assertTrue(addItemAndSkipExisting1);    	
    }
    
    @Test
    public void testGetItems()
    {
    	final ItemEntity entity = createItem();
    	entity.setFeedId(1l);    	
    	itemDao.addItem(entity);
    	
    	final ItemEntity entity1 = createItem();
    	entity1.setFeedId(1l);
    	itemDao.addItem(entity1);
    	
    	final ItemEntity entity2 = createItem();
    	entity2.setFeedId(2l);
    	itemDao.addItem(entity2);    	
    	
    	final List<ItemEntity> items = itemDao.getItems(1l);
    	assertEquals(2, items.size());
    	
    }
    
    
    private static ItemEntity createItem()
    {
    	final ItemEntity entity = new ItemEntity();
    	entity.setDescription(RandomStringUtils.random(10, "abcdefg1234567890"));    	 
    	entity.setAuthor(RandomStringUtils.random(10, "abcdefg1234567890"));
    	entity.setGuid(RandomStringUtils.random(10, "abcdefg1234567890"));
    	entity.setPubDate(RandomStringUtils.random(10, "abcdefg1234567890"));
    	entity.setLink(RandomStringUtils.random(10, "abcdefg1234567890"));
    	entity.setTitle(RandomStringUtils.random(10, "abcdefg1234567890"));
    	//entity.setFeedId("")
    	entity.setRead(Boolean.TRUE);
    	return entity;
    }
}
