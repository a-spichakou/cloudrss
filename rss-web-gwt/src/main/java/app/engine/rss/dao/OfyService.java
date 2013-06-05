package app.engine.rss.dao;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.entity.ItemEntity;

public class OfyService {
	static {
		factory().register(FeedEntity.class);
		factory().register(ItemEntity.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

}
