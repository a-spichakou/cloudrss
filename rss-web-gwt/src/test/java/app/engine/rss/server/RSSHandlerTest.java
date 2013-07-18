package app.engine.rss.server;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import app.engine.rss.server.sax.RSSHandler;

import junit.framework.TestCase;

public class RSSHandlerTest extends TestCase{


	public void testParsing()
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RSSHandler saxp = new RSSHandler();

		try {
			parser.parse("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml", saxp);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail();
	}
	
}
