package app.engine.rss.server;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBException;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;
import app.engine.rss.shared.exception.ServiceException;

public interface IFeedParser {

	public abstract FeedEntity populateFeedEntityAtom(URL url) throws JAXBException, IOException, ServiceException;
	
	public List<ItemEntity> populateItemEntitiesAtom(URL url) throws JAXBException, IOException;

}