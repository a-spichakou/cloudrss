package app.engine.rss.server;

import java.net.URL;

import junit.framework.TestCase;

import org.junit.Test;

import app.engine.rss.entity.FeedEntity;

public class FeedParserImplTest extends TestCase {

	private FeedParserImpl feedParser = new FeedParserImpl();

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

}
