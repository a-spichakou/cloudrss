package app.engine.rss.server;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBException;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

public interface IFeedParser {

	public abstract FeedEntity populateFeedEntityAtom(URL url) throws JAXBException, IOException;
	
	public List<ItemEntity> populateItemEntitiesAtom(URL url) throws JAXBException, IOException;

}