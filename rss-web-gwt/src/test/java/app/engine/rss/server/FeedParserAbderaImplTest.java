package app.engine.rss.server;

import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

public class FeedParserAbderaImplTest extends TestCase {

	private FeedParserAbderaImpl feedParser = new FeedParserAbderaImpl();

	@Test
	public void testPopulateFeedEntityAtom() {
		FeedEntity populateFeedEntityAtom = null;
		URL url = null;
		final String spec = "http://projects.apache.org/feeds/atom.xml";
		try {			
			url = new URL(spec);
			populateFeedEntityAtom = feedParser.populateFeedEntityAtom(url);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals("All projects managed by the Apache Software Foundation release software. This feed shows the latest releases.", populateFeedEntityAtom.getDescription());
		assertEquals("http://apache.org/favicon.ico", populateFeedEntityAtom.getImageUrl());
		assertEquals(spec, populateFeedEntityAtom.getLink());
		assertEquals("Apache Software Foundation Project Releases", populateFeedEntityAtom.getTitle());
	}

	
	public void testPopulateItemEntitiesAtom()
	{
		/*URL url = null;
		final String spec = "http://projects.apache.org/feeds/atom.xml";
		List<ItemEntity> itemEntities = null;
		try {			
			url = new URL(spec);
			itemEntities = feedParser.populateItemEntitiesAtom(url);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(itemEntities);
		assertTrue(itemEntities.size()>0);
		final ItemEntity itemEntity = itemEntities.get(0);
		assertNotNull(itemEntity.getDescription());
		assertNotNull(itemEntity.getGuid());
		assertNotNull(itemEntity.getLink());
		assertNotNull(itemEntity.getTitle());*/
	}
}
