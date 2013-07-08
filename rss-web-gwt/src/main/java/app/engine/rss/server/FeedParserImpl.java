package app.engine.rss.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import com.sun.org.apache.xerces.internal.dom.TextImpl;
import org.springframework.stereotype.Service;
import org.w3._2005.atom.FeedType;
import org.w3._2005.atom.IconType;
import org.w3._2005.atom.TextType;
import org.w3c.dom.Node;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

/**
 * Feeds parser
 * 
 * @author aspichakou
 * 
 */
@Service
public class FeedParserImpl implements IFeedParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * app.engine.rss.server.FeedParser#populateFeedEntityAtom(java.net.URL)
	 */
	public FeedEntity populateFeedEntityAtom(URL url) throws JAXBException, IOException {		
		final JAXBContext jc = JAXBContext.newInstance("org.w3._2005.atom");

		final Unmarshaller unmarshaller = jc.createUnmarshaller();
		final InputStream xml = url.openStream();
		final JAXBElement<FeedType> feed = unmarshaller.unmarshal(new StreamSource(xml), FeedType.class);
		xml.close();

		final FeedEntity entity = new FeedEntity();		
		entity.setLink(url.toString());
		final List<Object> authorOrCategoryOrContributor = feed.getValue().getAuthorOrCategoryOrContributor();
		
		for (Object obj : authorOrCategoryOrContributor) {
			if(!(obj instanceof javax.xml.bind.JAXBElement))
			{
				continue;
			}
			final javax.xml.bind.JAXBElement element = (javax.xml.bind.JAXBElement)obj;
			final String localPart = element.getName().getLocalPart();
			final Object value = element.getValue();
						
			if ("title".equals(localPart)) {
				final TextType value2 = (TextType)value;
				final List<Object> content = value2.getContent();
				final String title = (String)(content.get(0));
				entity.setTitle(title);
			}
			if ("subtitle".equals(localPart)) {
				final TextType value2 = (TextType)value;
				final List<Object> content = value2.getContent();
				final String description = (String)(content.get(0));
				entity.setDescription(description);
			}
			/*if ("link".equals(localPart)) {
				entity.setLink(((LinkType)value).getContent().toString());
			}*/
			if ("icon".equals(localPart)) {
				final IconType value2 = (IconType)value;
				final String imageUrl = value2.getValue();
				entity.setImageUrl(imageUrl);
			}
		}
		// entity.setDescription(otherAttributes);

		/*
		 * final Marshaller marshaller = jc.createMarshaller();
		 * marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 * marshaller.marshal(feed, System.out);
		 */

		return entity;
	}
	
	public List<ItemEntity> populateItemEntitiesAtom(URL url) throws JAXBException, IOException
	{
		final JAXBContext jc = JAXBContext.newInstance("org.w3._2005.atom");

		final Unmarshaller unmarshaller = jc.createUnmarshaller();
		final InputStream xml = url.openStream();
		final JAXBElement<FeedType> feed = unmarshaller.unmarshal(new StreamSource(xml), FeedType.class);
		xml.close();
		
		final List<Object> authorOrCategoryOrContributor = feed.getValue().getAuthorOrCategoryOrContributor();
		final List<ItemEntity> entities = new ArrayList<ItemEntity>();
		
		for (Object obj : authorOrCategoryOrContributor) {
			if(!(obj instanceof ElementNSImpl))
			{
				continue;
			}
			final ElementNSImpl element = (ElementNSImpl)obj;
			final ItemEntity item = new ItemEntity();
			popilateItemEntityAtom(item, (ElementNSImpl)element.getFirstChild());
			entities.add(item);
		}
		return entities;
	}
	
	private void popilateItemEntityAtom(ItemEntity entity, ElementNSImpl firstChild)
	{
		if(firstChild==null)
		{
			return;
		}
		final String localName = firstChild.getLocalName();
		
		if("title".equals(localName))
		{
			final TextImpl text = (TextImpl)firstChild.getFirstChild();
			entity.setTitle(text.getTextContent());
		}
		if("content".equals(localName))
		{
			entity.setDescription(firstChild.getTextContent());
		}
		if("id".equals(localName))
		{
			final TextImpl text = (TextImpl)firstChild.getFirstChild();
			entity.setGuid(text.getTextContent());
		}
		if("link".equals(localName))
		{
			final String href = firstChild.getAttribute("href");
			entity.setLink(href);
		}
		if("published".equals(localName))
		{
			final TextImpl text = (TextImpl)firstChild.getFirstChild();
			entity.setPubDate(text.getTextContent());
		}
		if("author".equals(localName))
		{
			final TextImpl text = (TextImpl)firstChild.getFirstChild();
			entity.setAuthor(text.getTextContent());
		}
		
		final Node nextSibling = firstChild.getNextSibling();
		if(nextSibling instanceof ElementNSImpl)
		{
			popilateItemEntityAtom(entity, (ElementNSImpl)nextSibling);
		}
	}

}
