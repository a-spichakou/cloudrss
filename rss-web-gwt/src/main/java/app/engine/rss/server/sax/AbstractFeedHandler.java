package app.engine.rss.server.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

public abstract class AbstractFeedHandler extends DefaultHandler {
	protected FeedEntity feedEntity;
	protected List<ItemEntity> items;
	
	protected ItemEntity currentItem;
	
	protected String thisElement;
	
	protected StringBuilder content = new StringBuilder();
	
	@Override
	public void startDocument() throws SAXException {
		feedEntity = new FeedEntity();
		items = new ArrayList<ItemEntity>();		
	}	
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		thisElement = qName;
		content.setLength(0);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		thisElement = "";
	}

	public FeedEntity getFeedEntity() {
		return feedEntity;
	}
	public List<ItemEntity> getItems() {
		return items;
	}
	

}
