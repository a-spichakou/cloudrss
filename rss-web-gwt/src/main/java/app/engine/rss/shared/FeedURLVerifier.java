package app.engine.rss.shared;

import java.net.MalformedURLException;
import java.net.URL;

public class FeedURLVerifier {

	public static boolean isValidURL(String url) {
		if (url == null || url.isEmpty()) {
			return false;
		}
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		return true;
	}

}
