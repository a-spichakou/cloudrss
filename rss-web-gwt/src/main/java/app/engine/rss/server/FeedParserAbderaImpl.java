package app.engine.rss.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.abdera.Abdera;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.apache.abdera.model.Person;
import org.apache.abdera.parser.Parser;
import org.apache.abdera.parser.stax.FOMFeed;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;
import app.engine.rss.shared.exception.ServiceException;

public class FeedParserAbderaImpl implements IFeedParser {

	public FeedEntity populateFeedEntityAtom(URL url) throws JAXBException, IOException, ServiceException {		
		final Abdera abdera = new Abdera();
		final Parser parser = abdera.getParser();
		
		final Document<Element> doc = parser.parse(url.openStream(),url.toString());
		final FOMFeed root = (FOMFeed)doc.getRoot();
		
		final FeedEntity feedEntity = new FeedEntity();
		feedEntity.setTitle(root.getTitle());
		feedEntity.setDescription(root.getSubtitle());
		try {
			final IRI icon = root.getIcon();
			if(icon!=null)
			{
				feedEntity.setImageUrl(icon.toURI().toString());
			}
		} catch (URISyntaxException e) {
			throw new ServiceException("Icon URI is not vallid",e);
		}
		
		feedEntity.setLink(url.toString());
		return feedEntity;
	}

	public List<ItemEntity> populateItemEntitiesAtom(URL url) throws JAXBException, IOException {
		final Abdera abdera = new Abdera();
		final Parser parser = abdera.getParser();
		
		final Document<Element> doc = parser.parse(url.openStream(),url.toString());
		final FOMFeed root = (FOMFeed)doc.getRoot();
		final List<Entry> entries = root.getEntries();
		final List<ItemEntity> items = new ArrayList<ItemEntity>(); 
		for(Entry entry: entries)
		{
			final ItemEntity e = new ItemEntity();
			final Person author = entry.getAuthor();
			if(author!=null)
			{
				e.setAuthor(author.getName());
			}
			e.setDescription(entry.getContent());
			e.setGuid(entry.getId().toString());
			final List<Link> links = entry.getLinks();
			if(links!=null && links.size()>0 && links.get(0)!=null)
			{
				e.setLink(links.get(0).getText());
			}
			final Date published = entry.getPublished();
			if(published!=null)
			{
				e.setPubDate(published.toString());
			}
			e.setTitle(entry.getTitle());
			items.add(e);	
		}
		return null;
	}

}
