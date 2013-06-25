package app.engine.rss.server;

import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import app.engine.rss.entity.FeedEntity;

public interface IFeedParser {

	public abstract FeedEntity populateFeedEntityAtom(URL url) throws JAXBException, IOException;

}