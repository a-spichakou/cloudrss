package app.engine.rss.server.sax;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;
import app.engine.rss.server.IFeedParser;
import app.engine.rss.shared.exception.ServiceException;

public class FeedParserSAXImpl implements IFeedParser{

	public FeedEntity populateFeedEntityAtom(URL url) throws JAXBException, IOException, ServiceException {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		
		
		return null;
	}

	public List<ItemEntity> populateItemEntitiesAtom(URL url) throws JAXBException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
