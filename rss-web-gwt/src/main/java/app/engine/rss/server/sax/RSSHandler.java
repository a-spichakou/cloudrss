package app.engine.rss.server.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import app.engine.rss.entity.ItemEntity;


public class RSSHandler extends AbstractFeedHandler {
	private boolean feedInfoPart = false;
	private boolean feedInfoPartIcon = false;

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {		
		content.append(ch, start, length);
		if(feedInfoPart)
		{			
			if("title".equals(thisElement))
			{
				feedEntity.setTitle(content.toString());
			}
			if("description".equalsIgnoreCase(thisElement))
			{
				feedEntity.setDescription(content.toString());
			}
			
			if(feedInfoPartIcon)
			{
				if("url".equalsIgnoreCase(thisElement))
				{
					feedEntity.setImageUrl(content.toString());
				}
			}
		}
		else
		{			
			if("author".equals(thisElement))
			{
				currentItem.setAuthor(content.toString());
			}
			if("description".equals(thisElement))
			{
				currentItem.setDescription(content.toString());
			}
			if("guid".equals(thisElement))
			{
				currentItem.setGuid(content.toString());
			}
			if("link".equals(thisElement))
			{
				currentItem.setLink(content.toString());
			}
			if("pubDate".equals(thisElement))
			{
				currentItem.setPubDate(content.toString());
			}
			if("title".equals(thisElement))
			{
				currentItem.setTitle(content.toString());
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if("item".equalsIgnoreCase(thisElement))
		{
			feedInfoPart = false;
			currentItem = new ItemEntity();
			items.add(currentItem);
		}
		
		if("channel".equalsIgnoreCase(thisElement))
		{
			feedInfoPart = true;
		}
		
		if("image".equalsIgnoreCase(thisElement))
		{
			feedInfoPartIcon = true;
		}
				
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if("image".equalsIgnoreCase(thisElement))
		{
			feedInfoPartIcon = false;
		}
		super.endElement(uri, localName, qName);
	}

}
